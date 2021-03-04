package com.yunzhicloud.auth.web.config;

import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.data.cache.RedisCache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author shay
 * @date 2021/2/24
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Resource
    private RedisTemplate<String, String> template;

    @Resource
    private RedisTemplate<String, Object> objectTemplate;

    @Bean
    public Cache<String, String> cacheBean() {
        return new RedisCache<>(template);
    }

    @Bean
    public Cache<String, Object> cacheObjectBean() {
        return new RedisCache<>(objectTemplate);
    }
}
