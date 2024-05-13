package com.my.memberservice.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisUtilService {

    private final RedissonClient redissonClient;


//    private final RedisTemplate<String, Object> template;
//
//    public String getData(String key) {
//        ValueOperations<String, Object> valueOperations = template.opsForValue();
//
//        return (String) valueOperations.get(key);
//    }
//
//    public boolean existData(String key) {
//        return Boolean.TRUE.equals(template.hasKey(key));
//    }
//
//    public void setData(String key, String value, long duration) {
//        ValueOperations<String, Object> valueOperations = template.opsForValue();
//        Duration expireDuration = Duration.ofMillis(duration);
//
//        valueOperations.set(key, value, expireDuration);
//    }
}