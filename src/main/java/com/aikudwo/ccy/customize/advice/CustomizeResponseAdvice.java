package com.aikudwo.ccy.customize.advice;

import com.aikudwo.ccy.customize.advice.result.WebResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author wls
 * @date 2019-05-15 17:14
 */
@RestControllerAdvice(basePackages = "com.aikudwo.ccy.controller")
public class CustomizeResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.getMethod() != null && !returnType.getMethod().getReturnType().isAssignableFrom(WebResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof WebResult) {
            return body;
        } else if (body instanceof String) {
            return new WebResult<>((String)body).toJSONObject().toJSONString();
        } else {
            return new WebResult<>(body);
        }
    }
}
