package com.yunzhicloud.auth.web.config;

import com.yunzhicloud.auth.cache.RedisCache;
import com.yunzhicloud.core.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author shay
 * @date 2021/2/24
 */
@Configuration
public class CacheConfig {

    @Resource
    private RedisTemplate<String, String> template;

    @Bean
    public Cache<String, String> cacheBean() {
        return new RedisCache<>(template);
    }
}
