package com.yunzhicloud.auth.web.config;

import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.cache.impl.MemoryCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shay
 * @date 2021/2/24
 */
@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, String> cacheBean() {
        return new MemoryCache<>();
    }
}
