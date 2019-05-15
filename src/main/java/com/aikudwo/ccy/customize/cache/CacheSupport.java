package com.aikudwo.ccy.customize.cache;

import com.aikudwo.ccy.customize.configuration.CacheConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CacheSupport {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String defaultCacheName = CustomizedCacheConfigurationManager.getDefaultCacheName();

    private Map<String, CopyOnWriteArraySet<CachedInvocation>> cacheToInvocationsMap;
    private CacheManager cacheManager;


    public void refreshCache(CachedInvocation invocation, String cacheName) {
        boolean invocationSuccess;
        Object computed = null;
        try {
            computed = invoke(invocation);
            invocationSuccess = true;
        } catch (Exception ex) {
            invocationSuccess = false;
        }
        if (invocationSuccess) {
            Cache cache;
            if (cacheToInvocationsMap.get(cacheName) != null && (cache = cacheManager.getCache(cacheName)) != null)
                cache.put(invocation.getKey(), computed);
        }
    }


    public void registerInvocation(Object targetBean, Method targetMethod, Object[] arguments, Set<String> annotatedCacheNames) {
        String key = CacheConfig.createKey(targetMethod, arguments);
        for (String cacheName : annotatedCacheNames) {
            if (cacheToInvocationsMap == null) {
                this.initialize();
            }
            if (!cacheToInvocationsMap.containsKey(cacheName))
                cacheName = defaultCacheName;
            final CachedInvocation invocation = new CachedInvocation(key, targetBean, targetMethod, arguments);
            if (cacheToInvocationsMap.get(cacheName).add(invocation))
                logger.info("registerInvocation[cacheName:{},key:{}]", cacheName, key);
        }
    }

    public void refreshCache(String cacheName) {
        this.refreshCacheByKey(cacheName, null);
    }

    public boolean refreshCacheByKey(String cacheName, String cacheKey) {
        if (!cacheToInvocationsMap.containsKey(cacheName))
            cacheName = defaultCacheName;
        CachedInvocation invocation = getCachedInvocation(cacheName, cacheKey);
        if (invocation != null) {
            refreshCache(invocation, cacheName);
            return true;
        } else {
            return false;
        }
    }

    public Map<String, CopyOnWriteArraySet<CachedInvocation>> getCacheToInvocationsMap() {
        return cacheToInvocationsMap;
    }

    private CachedInvocation getCachedInvocation(String cacheName, String cacheKey) {
        for (final CachedInvocation invocation : cacheToInvocationsMap.get(cacheName)) {
            if (!StringUtils.isBlank(cacheKey) && invocation.getKey().toString().equals(cacheKey))
                return invocation;
        }
        return null;
    }

    private Object invoke(CachedInvocation invocation)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final MethodInvoker invoker = new MethodInvoker();
        invoker.setTargetObject(invocation.getTargetBean());
        invoker.setArguments(invocation.getArguments());
        invoker.setTargetMethod(invocation.getTargetMethod().getName());
        invoker.prepare();
        return invoker.invoke();
    }

    public CacheSupport initialize(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        initialize();
        return this;
    }

    private void initialize() {
        cacheToInvocationsMap = new ConcurrentHashMap<>(cacheManager.getCacheNames().size());
        cacheToInvocationsMap.put(defaultCacheName, new CopyOnWriteArraySet<>());
        for (final String cacheName : cacheManager.getCacheNames()) {
            cacheToInvocationsMap.put(cacheName, new CopyOnWriteArraySet<>());
        }
    }

    public class CachedInvocation {
        private Object key;
        private final Object targetBean;
        private final Method targetMethod;
        private Object[] arguments;

        CachedInvocation(Object key, Object targetBean, Method targetMethod, Object[] arguments) {
            this.key = key;
            this.targetBean = targetBean;
            this.targetMethod = targetMethod;
            if (arguments != null && arguments.length != 0) {
                this.arguments = Arrays.copyOf(arguments, arguments.length);
            }
        }

        Object getKey() {
            return key;
        }

        void setKey(Object key) {
            this.key = key;
        }

        Object getTargetBean() {
            return targetBean;
        }

        Method getTargetMethod() {
            return targetMethod;
        }

        Object[] getArguments() {
            return arguments;
        }

        public void setArguments(Object[] arguments) {
            this.arguments = arguments;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CachedInvocation that = (CachedInvocation) o;
            return Objects.equals(key, that.key) &&
                    Objects.equals(targetBean, that.targetBean) &&
                    Objects.equals(targetMethod, that.targetMethod) &&
                    Arrays.equals(arguments, that.arguments);
        }

        @Override
        public int hashCode() {

            int result = Objects.hash(key, targetBean, targetMethod);
            result = 31 * result + Arrays.hashCode(arguments);
            return result;
        }
    }
}
