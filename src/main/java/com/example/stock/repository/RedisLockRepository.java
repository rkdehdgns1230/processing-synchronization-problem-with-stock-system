package com.example.stock.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RedisLockRepository {
    // redis 명령어 사용을 위해 추가
    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(Long key){
        return redisTemplate
                .opsForValue()
                .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_000));
    }

    public Boolean unlock(Long key){
        return redisTemplate.delete(generateKey(key));
    }

    private String generateKey(Long key){
        return key.toString();
    }
}
