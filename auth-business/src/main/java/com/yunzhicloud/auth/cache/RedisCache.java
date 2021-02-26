package com.yunzhicloud.auth.cache;

import com.yunzhicloud.core.cache.Cache;
import com.yunzhicloud.core.lang.Action;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author shay
 * @date 2021/2/25
 */
@AllArgsConstructor
public class RedisCache<K, V> implements Cache<K, V> {
    private final RedisTemplate<K, V> template;

    @Override
    public void put(K k, V v, long l) {
        template.opsForValue().set(k, v, l - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public V get(K k) {
        return k == null ? null : template.opsForValue().get(k);
    }

    @Override
    public V getOrPut(K k, Function<? super K, ? extends V> function, long l) {
        V value = get(k);
        if (value == null && function != null) {
            value = function.apply(k);
            put(k, value, l);
        }
        return value;
    }

    @Override
    public void remove(K k) {
        template.delete(k);
    }

    @Override
    public void clean() {
    }

    @Override
    public void info() {

    }

    @Override
    public void keyExpired(Action<K> action) {

    }
}
