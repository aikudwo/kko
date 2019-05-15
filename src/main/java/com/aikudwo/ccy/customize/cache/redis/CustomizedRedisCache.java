package com.aikudwo.ccy.customize.cache.redis;

import com.aikudwo.ccy.customize.cache.CacheSupport;
import com.aikudwo.ccy.customize.cache.CustomizedCacheConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class CustomizedRedisCache extends RedisCache {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RedisTemplate<Object, Object> redisTemplate;
    private final CacheSupport cacheSupport;
    private ExecutorService executorService;

    private static final ConcurrentHashMap<String, Boolean> RUNNING_REFRESH_CACHE = new ConcurrentHashMap<>();

    CustomizedRedisCache(String name,
                         RedisCacheWriter cacheWriter,
                         RedisCacheConfiguration cacheConfig,
                         RedisTemplate<Object, Object> redisTemplate,
                         CacheSupport cacheSupport
            , ExecutorService executorService) {
        super(name, cacheWriter, cacheConfig);
        this.redisTemplate = redisTemplate;
        this.cacheSupport = cacheSupport;
        this.executorService = executorService;
    }

    @Override
    @Nullable
    public ValueWrapper get(@NonNull Object key) {
        ValueWrapper valueWrapper = super.get(key);
        String cacheName = key.toString();
        if (cacheName.contains(" "))
            cacheName = cacheName.substring(0, cacheName.indexOf(" "));
        long refreshRemainTimeSecond =
                CustomizedCacheConfigurationManager.getCacheRefreshRemainTimeSecond(cacheName);
        String cacheKey = this.createCacheKey(key);
        Long ttl = this.redisTemplate.getExpire(cacheKey);
        assert ttl != null;
        if (ttl < refreshRemainTimeSecond) {
            if (RUNNING_REFRESH_CACHE.putIfAbsent(cacheKey, Boolean.TRUE) == null)
                executorService.execute(() -> {
                    logger.debug("refresh key:{} ttl:{} refreshRemainTimeSecond:{}", cacheKey, ttl, refreshRemainTimeSecond);
                    if (!CustomizedRedisCache.this.cacheSupport
                            .refreshCacheByKey(CustomizedRedisCache.super.getName(), key.toString()) && ttl != -2) {
                        logger.info("CachedInvocation({}:{}) instance not found", CustomizedRedisCache.super.getName(), cacheKey);
                        evict(key);
                    }
                    RUNNING_REFRESH_CACHE.remove(cacheKey);
                });
        }
        return valueWrapper;
    }

}