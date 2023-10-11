package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class YourRedisService {

    private final Jedis jedis;

    @Autowired
    public YourRedisService(Jedis jedis) {
        this.jedis = jedis;
    }

    public void saveToRedis(String key, String value) {
        jedis.set(key, value);
    }

    public String getFromRedis(String key) {
        return jedis.get(key);
    }
}

