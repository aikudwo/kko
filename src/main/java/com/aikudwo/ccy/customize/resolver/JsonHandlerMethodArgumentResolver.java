package com.aikudwo.ccy.customize.resolver;

import com.aikudwo.ccy.customize.annotation.RequestBodyParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JsonHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private Class<?> jsonClass;
    private static final Class<com.alibaba.fastjson.JSONObject> defaultJsonClass = com.alibaba.fastjson.JSONObject.class;

    private final Map<MethodParameter, NamedValueInfo> namedValueInfoCache = new ConcurrentHashMap<>(256);

    private static final Set<HttpMethod> SUPPORTED_METHODS =
            EnumSet.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH);

    private static final Object NO_VALUE = new Object();

    protected final Log logger = LogFactory.getLog(getClass());

    private final List<HttpMessageConverter<?>> messageConverters;

    private final List<MediaType> allSupportedMediaTypes;

    private final RequestResponseBodyAdviceChain advice;

    @Autowired
    public JsonHandlerMethodArgumentResolver(List<HttpMessageConverter<?>> converters) {
        this(converters, null);
    }

    public JsonHandlerMethodArgumentResolver(List<HttpMessageConverter<?>> converters,
                                             @Nullable List<Object> requestResponseBodyAdvice) {
        this(converters, requestResponseBodyAdvice, null);
    }

    public JsonHandlerMethodArgumentResolver(List<HttpMessageConverter<?>> converters,
                                             @Nullable List<Object> requestResponseBodyAdvice, Class json) {
        Assert.notEmpty(converters, "'messageConverters' must not be empty");
        this.messageConverters = converters;
        this.allSupportedMediaTypes = getAllSupportedMediaTypes(converters);
        this.advice = new RequestResponseBodyAdviceChain(requestResponseBodyAdvice);
        if (jsonClass != null && json.getClass().isAssignableFrom(Map.class)) {
            this.jsonClass = json.getClass();
        } else this.jsonClass = defaultJsonClass;
    }

    private static List<MediaType> getAllSupportedMediaTypes(List<HttpMessageConverter<?>> messageConverters) {
        Set<MediaType> allSupportedMediaTypes = new LinkedHashSet<>();
        for (HttpMessageConverter<?> messageConverter : messageConverters) {
            allSupportedMediaTypes.addAll(messageConverter.getSupportedMediaTypes());
        }
        List<MediaType> result = new ArrayList<>(allSupportedMediaTypes);
        MediaType.sortBySpecificity(result);
        return Collections.unmodifiableList(result);
    }

    private RequestResponseBodyAdviceChain getAdvice() {
        return this.advice;
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBodyParam.class);
    }

    @Override
    @Nullable
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        NamedValueInfo namedValueInfo = getNamedValueInfo(parameter);
        MethodParameter nestedParameter = parameter.nestedIfOptional();

        Object resolvedName = parameter.getParameterName();
        if (resolvedName == null) {
            throw new IllegalArgumentException(
                    "Specified name must not resolve to null: [" + namedValueInfo.name + "]");
        }

        Object arg = resolveName(resolvedName.toString(), nestedParameter, webRequest);
        if (arg == null) {
            if (namedValueInfo.defaultValue != null) {
                arg = namedValueInfo.defaultValue;
            } else if (namedValueInfo.required && !nestedParameter.isOptional()) {
                handleMissingValue(namedValueInfo.name, nestedParameter);
            }
            arg = handleNullValue(namedValueInfo.name, arg, nestedParameter.getNestedParameterType());
        } else if ("".equals(arg) && namedValueInfo.defaultValue != null) {
            arg = namedValueInfo.defaultValue;
        }

        WebDataBinder binder = binderFactory.createBinder(webRequest, null, namedValueInfo.name);
        try {
            arg = binder.convertIfNecessary(arg, parameter.getParameterType(), parameter);
        } catch (ConversionNotSupportedException ex) {
            throw new MethodArgumentConversionNotSupportedException(arg, ex.getRequiredType(),
                    namedValueInfo.name, parameter, ex.getCause());
        } catch (TypeMismatchException ex) {
            throw new MethodArgumentTypeMismatchException(arg, ex.getRequiredType(),
                    namedValueInfo.name, parameter, ex.getCause());
        }

        handleResolvedValue(arg, namedValueInfo.name, webRequest);

        return arg;
    }

    private Object readWithMessageConverters(NativeWebRequest webRequest, MethodParameter parameter,
                                             Type paramType) throws HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {

        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Assert.state(servletRequest != null, "No HttpServletRequest");
        ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(servletRequest);

        Object arg = readWithMessageConverters(inputMessage, parameter, paramType);
        if (arg == null && checkRequired(parameter)) {
            throw new HttpMessageNotReadableException("Required request body is missing: " +
                    parameter.getExecutable().toGenericString());
        }
        return arg;
    }

    private boolean checkRequired(MethodParameter parameter) {
        RequestBodyParam requestBodyParam = parameter.getParameterAnnotation(RequestBodyParam.class);
        return (requestBodyParam != null && requestBodyParam.required()) && !parameter.isOptional();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private <T> Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter,
                                                 Type targetType) throws HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {

        MediaType contentType;
        try {
            contentType = inputMessage.getHeaders().getContentType();
        } catch (InvalidMediaTypeException ex) {
            throw new HttpMediaTypeNotSupportedException(ex.getMessage());
        }

        Class<?> contextClass = parameter.getContainingClass();
        Class<T> targetClass = (targetType instanceof Class ? (Class<T>) targetType : null);
        if (targetClass == null) {
            ResolvableType resolvableType = ResolvableType.forMethodParameter(parameter);
            targetClass = (Class<T>) resolvableType.resolve();
        }

        HttpMethod httpMethod = (inputMessage instanceof HttpRequest ? ((HttpRequest) inputMessage).getMethod() : null);
        Object body = NO_VALUE;

        EmptyBodyCheckingHttpInputMessage message;
        try {
            message = new EmptyBodyCheckingHttpInputMessage(inputMessage);

            for (HttpMessageConverter<?> converter : this.messageConverters) {
                Class<HttpMessageConverter<?>> converterType = (Class<HttpMessageConverter<?>>) converter.getClass();
                GenericHttpMessageConverter<?> genericConverter =
                        (converter instanceof GenericHttpMessageConverter ? (GenericHttpMessageConverter<?>) converter : null);
                if (genericConverter != null ? genericConverter.canRead(targetType, contextClass, contentType) :
                        (targetClass != null && converter.canRead(targetClass, contentType))) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Read [" + targetType + "] as \"" + contentType + "\" with [" + converter + "]");
                    }
                    if (message.hasBody()) {
                        HttpInputMessage msgToUse =
                                getAdvice().beforeBodyRead(message, parameter, targetType, converterType);
                        body = (genericConverter != null ? genericConverter.read(targetType, contextClass, msgToUse) :
                                ((HttpMessageConverter<T>) converter).read(targetClass, msgToUse));
                        body = getAdvice().afterBodyRead(body, msgToUse, parameter, targetType, converterType);
                    } else {
                        body = getAdvice().handleEmptyBody(null, message, parameter, targetType, converterType);
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", ex);
        }

        if (body == NO_VALUE) {
            if (httpMethod == null || !SUPPORTED_METHODS.contains(httpMethod) || !message.hasBody()) {
                return null;
            }
            throw new HttpMediaTypeNotSupportedException(contentType, this.allSupportedMediaTypes);
        }

        return body;
    }

    @Nullable
    private Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);

        if (servletRequest != null) {
            Object mpArg = MultipartResolutionDelegate.resolveMultipartArgument(name, parameter, servletRequest);
            if (mpArg != MultipartResolutionDelegate.UNRESOLVABLE)
                return mpArg;
        }

        Object arg = null;
        MultipartHttpServletRequest multipartRequest = request.getNativeRequest(MultipartHttpServletRequest.class);
        if (multipartRequest != null) {
            List<MultipartFile> files = multipartRequest.getFiles(name);
            if (!files.isEmpty()) {
                arg = (files.size() == 1 ? files.get(0) : files);
            }
        }
        if (arg == null) {
            String[] paramValues = request.getParameterValues(name);
            if (paramValues != null)
                arg = (paramValues.length == 1 ? paramValues[0] : paramValues);
        }

        if (arg == null) {
            Object body = getRequestBody(request, parameter);
            if (body instanceof Map && ((Map) body).containsKey(name))
                arg = ((Map) body).get(name);
        }
        return arg;
    }

    private void handleMissingValue(String name, MethodParameter parameter) throws ServletException {
        throw new ServletRequestBindingException("Missing argument '" + name +
                "' for method parameter of type " + parameter.getNestedParameterType().getSimpleName());
    }


    private Object getRequestBody(NativeWebRequest webRequest, MethodParameter parameter) throws HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Assert.state(servletRequest != null, "No HttpServletRequest");
        Object jsonBody = servletRequest.getAttribute("JSON_REQUEST_BODY");
        if (jsonBody == null) {
            jsonBody = readWithMessageConverters(webRequest, parameter, jsonClass);
            servletRequest.setAttribute("JSON_REQUEST_BODY", jsonBody);
        }
        return jsonBody;
    }

    private NamedValueInfo getNamedValueInfo(MethodParameter parameter) {
        NamedValueInfo namedValueInfo = this.namedValueInfoCache.get(parameter);
        if (namedValueInfo == null) {
            namedValueInfo = createNamedValueInfo(parameter);
            namedValueInfo = updateNamedValueInfo(parameter, namedValueInfo);
            this.namedValueInfoCache.put(parameter, namedValueInfo);
        }
        return namedValueInfo;
    }

    private NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
        RequestBodyParam ann = parameter.getParameterAnnotation(RequestBodyParam.class);
        return (ann != null ? new RequestParamNamedValueInfo(ann) : new RequestParamNamedValueInfo());
    }


    @SuppressWarnings("unchecked")
    private void handleResolvedValue(@Nullable Object arg, String name, NativeWebRequest request) {

        String key = View.PATH_VARIABLES;
        int scope = RequestAttributes.SCOPE_REQUEST;
        Map<String, Object> pathVars = (Map<String, Object>) request.getAttribute(key, scope);
        if (pathVars == null) {
            pathVars = new HashMap<>();
            request.setAttribute(key, pathVars, scope);
        }
        pathVars.put(name, arg);

    }

    private NamedValueInfo updateNamedValueInfo(MethodParameter parameter, NamedValueInfo info) {
        String name = info.name;
        if (info.name.isEmpty()) {
            name = parameter.getParameterName();
        }
        String defaultValue = (ValueConstants.DEFAULT_NONE.equals(info.defaultValue) ? null : info.defaultValue);
        return new NamedValueInfo(name, info.required, defaultValue);
    }

    @Nullable
    private Object handleNullValue(String name, @Nullable Object value, Class<?> paramType) {
        if (value == null) {
            if (Boolean.TYPE.equals(paramType)) {
                return Boolean.FALSE;
            } else if (paramType.isPrimitive()) {
                throw new IllegalStateException("Optional " + paramType.getSimpleName() + " parameter '" + name +
                        "' is present but cannot be translated into a null value due to being declared as a " +
                        "primitive type. Consider declaring it as object wrapper for the corresponding primitive type.");
            }
        }
        return value;
    }

    /**
     * Represents the information about a named value, including name, whether it's required and a default value.
     */
    private static class NamedValueInfo {

        private final String name;

        private final boolean required;

        @Nullable
        private final String defaultValue;

        NamedValueInfo(String name, boolean required, @Nullable String defaultValue) {
            this.name = name;
            this.required = required;
            this.defaultValue = defaultValue;
        }
    }

    private static class RequestParamNamedValueInfo extends NamedValueInfo {

        RequestParamNamedValueInfo() {
            super("", false, ValueConstants.DEFAULT_NONE);
        }

        RequestParamNamedValueInfo(RequestBodyParam annotation) {
            super(annotation.name(), annotation.required(), annotation.defaultValue());
        }
    }

    private static class EmptyBodyCheckingHttpInputMessage implements HttpInputMessage {

        private final HttpHeaders headers;

        @Nullable
        private final InputStream body;

        EmptyBodyCheckingHttpInputMessage(HttpInputMessage inputMessage) throws IOException {
            this.headers = inputMessage.getHeaders();
            InputStream inputStream = inputMessage.getBody();
            if (inputStream.markSupported()) {
                inputStream.mark(1);
                this.body = (inputStream.read() != -1 ? inputStream : null);
                inputStream.reset();
            } else {
                PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
                int b = pushbackInputStream.read();
                if (b == -1) {
                    this.body = null;
                } else {
                    this.body = pushbackInputStream;
                    pushbackInputStream.unread(b);
                }
            }
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.headers;
        }

        @Override
        public InputStream getBody() {
            return (this.body != null ? this.body : StreamUtils.emptyInput());
        }

        public boolean hasBody() {
            return (this.body != null);
        }
    }

    class RequestResponseBodyAdviceChain implements RequestBodyAdvice, ResponseBodyAdvice<Object> {

        private final List<Object> requestBodyAdvice = new ArrayList<>(4);

        private final List<Object> responseBodyAdvice = new ArrayList<>(4);


        /**
         * Create an instance from a list of objects that are either of type
         * {@code ControllerAdviceBean} or {@code RequestBodyAdvice}.
         */
        RequestResponseBodyAdviceChain(@Nullable List<Object> requestResponseBodyAdvice) {
            if (requestResponseBodyAdvice != null) {
                for (Object advice : requestResponseBodyAdvice) {
                    Class<?> beanType = (advice instanceof ControllerAdviceBean ?
                            ((ControllerAdviceBean) advice).getBeanType() : advice.getClass());
                    if (beanType != null) {
                        if (RequestBodyAdvice.class.isAssignableFrom(beanType)) {
                            this.requestBodyAdvice.add(advice);
                        } else if (ResponseBodyAdvice.class.isAssignableFrom(beanType)) {
                            this.responseBodyAdvice.add(advice);
                        }
                    }
                }
            }
        }


        @Override
        public boolean supports(MethodParameter param, Type type, Class<? extends HttpMessageConverter<?>> converterType) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        public HttpInputMessage beforeBodyRead(HttpInputMessage request, MethodParameter parameter,
                                               Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

            for (RequestBodyAdvice advice : getMatchingAdvice(parameter, RequestBodyAdvice.class)) {
                if (advice.supports(parameter, targetType, converterType)) {
                    request = advice.beforeBodyRead(request, parameter, targetType, converterType);
                }
            }
            return request;
        }

        @Override
        public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                    Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

            for (RequestBodyAdvice advice : getMatchingAdvice(parameter, RequestBodyAdvice.class)) {
                if (advice.supports(parameter, targetType, converterType)) {
                    body = advice.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
                }
            }
            return body;
        }

        @Override
        @Nullable
        public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType contentType,
                                      Class<? extends HttpMessageConverter<?>> converterType,
                                      ServerHttpRequest request, ServerHttpResponse response) {

            return processBody(body, returnType, contentType, converterType, request, response);
        }

        @Override
        @Nullable
        public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                      Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

            for (RequestBodyAdvice advice : getMatchingAdvice(parameter, RequestBodyAdvice.class)) {
                if (advice.supports(parameter, targetType, converterType)) {
                    body = advice.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
                }
            }
            return body;
        }


        @SuppressWarnings("unchecked")
        @Nullable
        private <T> Object processBody(@Nullable Object body, MethodParameter returnType, MediaType contentType,
                                       Class<? extends HttpMessageConverter<?>> converterType,
                                       ServerHttpRequest request, ServerHttpResponse response) {

            for (ResponseBodyAdvice<?> advice : getMatchingAdvice(returnType, ResponseBodyAdvice.class)) {
                if (advice.supports(returnType, converterType)) {
                    body = ((ResponseBodyAdvice<T>) advice).beforeBodyWrite((T) body, returnType,
                            contentType, converterType, request, response);
                }
            }
            return body;
        }

        @SuppressWarnings("unchecked")
        private <A> List<A> getMatchingAdvice(MethodParameter parameter, Class<? extends A> adviceType) {
            List<Object> availableAdvice = getAdvice(adviceType);
            if (CollectionUtils.isEmpty(availableAdvice)) {
                return Collections.emptyList();
            }
            List<A> result = new ArrayList<>(availableAdvice.size());
            for (Object advice : availableAdvice) {
                if (advice instanceof ControllerAdviceBean) {
                    ControllerAdviceBean adviceBean = (ControllerAdviceBean) advice;
                    if (!adviceBean.isApplicableToBeanType(parameter.getContainingClass())) {
                        continue;
                    }
                    advice = adviceBean.resolveBean();
                }
                if (adviceType.isAssignableFrom(advice.getClass())) {
                    result.add((A) advice);
                }
            }
            return result;
        }

        private List<Object> getAdvice(Class<?> adviceType) {
            if (RequestBodyAdvice.class == adviceType) {
                return this.requestBodyAdvice;
            } else if (ResponseBodyAdvice.class == adviceType) {
                return this.responseBodyAdvice;
            } else {
                throw new IllegalArgumentException("Unexpected adviceType: " + adviceType);
            }
        }

    }


}