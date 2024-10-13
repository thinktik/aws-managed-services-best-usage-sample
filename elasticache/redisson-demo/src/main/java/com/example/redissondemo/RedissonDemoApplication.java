package com.example.redissondemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedissonDemoApplication {
    private static final Logger log = LoggerFactory.getLogger(RedissonDemoApplication.class);

    public static void main(String[] args) {
        // 为本项目调整JVM DNS TTL时间,方便及时接受到AWS ElastiCache Redis集群拓扑关系调整导致的DNS状态更新.
        java.security.Security.setProperty("networkaddress.cache.ttl", "3");
        java.security.Security.setProperty("networkaddress.cache.negative.ttl", "0");

        // debug 专用,查询当前的JVM DNS TTL设置.
        String cacheTtl = java.security.Security.getProperty("networkaddress.cache.ttl");
        String cacheNegativeTtl = java.security.Security.getProperty("networkaddress.cache.negative.ttl");
        System.out.println("the  'networkaddress.cache.ttl' is : " + cacheTtl);
        log.info("the  'networkaddress.cache.ttl' is : {}", cacheTtl);
        System.out.println("the  'networkaddress.cache.negative.ttl' is : " + cacheNegativeTtl);
        log.info("the  'networkaddress.cache.negative.ttl' is : {}", cacheNegativeTtl);

        SpringApplication.run(RedissonDemoApplication.class, args);
    }

}
