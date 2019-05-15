package com.aikudwo.ccy.customize.cache.redis;

import com.aikudwo.ccy.customize.cache.CacheSupport;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.concurrent.ExecutorService;


public class CustomizedRedisCacheManager extends RedisCacheManager {
    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfiguration defaultCacheConfiguration;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final CacheSupport cacheSupport;
    private final ExecutorService executorService;

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration,
                                       RedisTemplate<Object, Object> redisTemplate,
                                       CacheSupport cacheSupport, ExecutorService executorService) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
        this.redisTemplate = redisTemplate;
        this.cacheSupport = cacheSupport.initialize(this);
        this.executorService = executorService;
    }

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration,
                                       RedisTemplate<Object, Object> redisTemplate,
                                       CacheSupport cacheSupport,
                                       ExecutorService executorService, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
        this.redisTemplate = redisTemplate;
        this.cacheSupport = cacheSupport.initialize(this);
        this.executorService = executorService;
    }

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration,
                                       boolean allowInFlightCacheCreation,
                                       RedisTemplate<Object, Object> redisTemplate,
                                       CacheSupport cacheSupport,
                                       ExecutorService executorService, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
        this.redisTemplate = redisTemplate;
        this.cacheSupport = cacheSupport.initialize(this);
        this.executorService = executorService;
    }

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration,
                                       Map<String, RedisCacheConfiguration> initialCacheConfigurations,
                                       RedisTemplate<Object, Object> redisTemplate,
                                       CacheSupport cacheSupport,
                                       ExecutorService executorService) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
        this.redisTemplate = redisTemplate;
        this.cacheSupport = cacheSupport.initialize(this);
        this.executorService = executorService;
    }

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter,
                                       RedisCacheConfiguration defaultCacheConfiguration,
                                       Map<String, RedisCacheConfiguration> initialCacheConfigurations,
                                       boolean allowInFlightCacheCreation,
                                       RedisTemplate<Object, Object> redisTemplate,
                                       CacheSupport cacheSupport,
                                       ExecutorService executorService) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
        this.redisTemplate = redisTemplate;
        this.cacheSupport = cacheSupport.initialize(this);
        this.executorService = executorService;
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        return new CustomizedRedisCache(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfiguration,
                redisTemplate, cacheSupport, executorService);
    }
}
