package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedisOperations() {
        // Установка значения в Redis
        String key = "example_key";
        String value = "example_value";
        redisTemplate.opsForValue().set(key, value);

        // Получение значения из Redis
        String retrievedValue = redisTemplate.opsForValue().get(key);

        // Проверка соответствия установленного и полученного значения
        assertEquals(value, retrievedValue);
    }
}
//implementation 'org.springframework.boot:spring-boot-starter-data-redis'
//spring:
//  redis:
//    host: localhost
//    port: 6379
