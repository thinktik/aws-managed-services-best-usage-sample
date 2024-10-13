package com.example.redissondemo.daos;

public interface RedisOpsDao {
    /**
     * 写入一个K-V到Redis
     *
     * @param key   redis key name
     * @param value redis key's value
     */
    void set(String key, String value);

    /**
     * 从Redis读取一个K-V
     *
     * @param key redis key name
     * @return redis key's value
     */
    String get(String key);
}
