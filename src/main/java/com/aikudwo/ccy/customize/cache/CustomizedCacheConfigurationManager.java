package com.aikudwo.ccy.customize.cache;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class CustomizedCacheConfigurationManager {
    private static final String DEFAULT_CACHE_NAME = "default";

    private static final long DEFAULT_REMAIN_TIME = -2;

    private static final ConcurrentHashMap<String, CacheItemConfig> CACHE_CONFIG_HOLDER;

    static {
        CACHE_CONFIG_HOLDER = new ConcurrentHashMap<>();
        CACHE_CONFIG_HOLDER.put(getDefaultCacheName(), new CacheItemConfig(DEFAULT_REMAIN_TIME));
    }

    public static void setDefaultCacheRefreshRemainTimeSecond(long refreshRemainTimeSecond) {
        CACHE_CONFIG_HOLDER.get(getDefaultCacheName()).setRefreshRemainTimeSecond(refreshRemainTimeSecond);
    }

    public static void setCacheRefreshRemainTimeSecond(String cacheName, long refreshRemainTimeSecond) {
        CACHE_CONFIG_HOLDER.put(cacheName, new CacheItemConfig(cacheName, refreshRemainTimeSecond));
    }

    public static long getCacheRefreshRemainTimeSecond(String cacheName) {
        return getCacheRefreshRemainTimeSecondByCacheName(cacheName);
    }

    public static String getDefaultCacheName() {
        return DEFAULT_CACHE_NAME;
    }

    public static long getDefaultRemainTime() {
        return DEFAULT_REMAIN_TIME;
    }

    private static long getCacheRefreshRemainTimeSecondByCacheName(String cacheName) {
        if (CACHE_CONFIG_HOLDER.containsKey(cacheName))
            return CACHE_CONFIG_HOLDER.get(cacheName).getRefreshRemainTimeSecond();
        return CACHE_CONFIG_HOLDER.get(getDefaultCacheName()).getRefreshRemainTimeSecond();
    }

    public static class CacheItemConfig implements Serializable {
        private String name;
        private long refreshRemainTimeSecond;

        CacheItemConfig() {
            this.name = getDefaultCacheName();
            this.refreshRemainTimeSecond = 10;
        }

        CacheItemConfig(long refreshRemainTimeSecond) {
            this.name = DEFAULT_CACHE_NAME;
            this.refreshRemainTimeSecond = refreshRemainTimeSecond;
        }

        CacheItemConfig(String name, long refreshRemainTimeSecond) {
            this.name = name;
            this.refreshRemainTimeSecond = refreshRemainTimeSecond;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getRefreshRemainTimeSecond() {
            return refreshRemainTimeSecond;
        }

        public void setRefreshRemainTimeSecond(long refreshRemainTimeSecond) {
            this.refreshRemainTimeSecond = refreshRemainTimeSecond;
        }
    }
}
