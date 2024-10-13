package com.example.redissondemo.daos.impl;


import com.example.redissondemo.daos.RedisOpsDao;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisOpsDaoImpl implements RedisOpsDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonConnectionFactory redissonConnectionFactory;

    @Override
    public void set(String key, String value) {

        stringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(5));
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
