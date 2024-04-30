package com.my.apigatewayserver.Token;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RedisUtilService {
    private final RedisTemplate<String, Object> template;
    public boolean existData(String key) {
        return Boolean.TRUE.equals(template.hasKey(key));
    }

}