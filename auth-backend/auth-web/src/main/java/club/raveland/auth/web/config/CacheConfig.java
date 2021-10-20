package club.raveland.auth.web.config;

import club.raveland.core.cache.Cache;
import club.raveland.cache.RedisCache;
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

    @Bean
    public Cache<String, String> cacheBean(RedisTemplate<String, String> template) {
        return new RedisCache<>(template);
    }

    @Bean
    @SuppressWarnings("all")
    public <T> Cache<String, T> cacheObjectBean(RedisTemplate<String, T> objectTemplate) {
        return new RedisCache<>(objectTemplate);
    }
}
