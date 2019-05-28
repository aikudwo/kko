package com.aikudwo.ccy.customize.configuration;


import com.aikudwo.ccy.customize.cache.CacheSupport;
import com.aikudwo.ccy.customize.cache.CustomizedCacheConfigurationManager;
import com.aikudwo.ccy.customize.cache.redis.CustomizedRedisCacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {


    private static final Long DEFAULT_REFRESH_SECOND = -2L;
    private static final Long DEFAULT_TTL_SECOND = 30L;

    public static class CacheNames {
        public static final String CACHE_TEST_CACHE_NAME = "cacheTest";
        public static final String ACCESS_TOKEN_CACHE_NAME = "accessToken";

    }

    public enum TTLSecond {
        CACHE_TEST(CacheNames.CACHE_TEST_CACHE_NAME, 3600L),
        ACCESS_TOKEN(CacheNames.ACCESS_TOKEN_CACHE_NAME,3600L) ;
        private String cacheName;
        private Long ttl;
        private Long refreshTime;

        TTLSecond(String cacheName, Long ttl) {
            this.cacheName = cacheName;
            this.ttl = ttl;
            this.refreshTime = DEFAULT_REFRESH_SECOND;
        }

        TTLSecond(String cacheName, Long ttl, Long refreshTime) {
            this.cacheName = cacheName;
            this.ttl = ttl;
            this.refreshTime = refreshTime;
        }
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> createKey(method, objects);
    }

    public static String createKey(Method method, Object... objects) {
        if (objects.length == 0) {
            return method.getName();
        }
        if (objects.length == 1) {
            Object param = objects[0];
            if (param != null && !param.getClass().isArray()) {
                return method.getName() + " " + param;
            }
        }
        return method.getName() + " [" + StringUtils.arrayToCommaDelimitedString(objects) + "]";
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<Object, Object> template = new RedisTemplate<>();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jdkSerializationRedisSerializer);
        template.setValueSerializer(jdkSerializationRedisSerializer);
        return template;
    }

    @Bean
    public CacheSupport cacheSupport() {
        return new CacheSupport();
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                               RedisTemplate<Object, Object> redisTemplate,
                                               CacheSupport cacheSupport,
                                               ExecutorService executorService) {

        return new CustomizedRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                defaultCacheConfiguration(), initialCacheConfigurations(), redisTemplate, cacheSupport, executorService);
    }

    private RedisCacheConfiguration defaultCacheConfiguration() {
        CustomizedCacheConfigurationManager.setDefaultCacheRefreshRemainTimeSecond(DEFAULT_REFRESH_SECOND);
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
                .entryTtl(Duration.ofSeconds(DEFAULT_TTL_SECOND));
    }

    private Map<String, RedisCacheConfiguration> initialCacheConfigurations() {
        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
        TTLSecond[] ttlSeconds = TTLSecond.values();
        for (TTLSecond ttlSecond : ttlSeconds){
            CustomizedCacheConfigurationManager.setCacheRefreshRemainTimeSecond(ttlSecond.cacheName, ttlSecond.refreshTime);
            initialCacheConfigurations.put(ttlSecond.cacheName,
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttlSecond.ttl)));
        }

        return initialCacheConfigurations;
    }

}
