package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class YourRedisServiceTest {

    private static RedisServer redisServer;
    
    @BeforeAll
    public static void setUp() {
        // Запускаем embedded Redis перед запуском всех тестов
        redisServer = new RedisServer(6379);
        redisServer.start();
    }

    @AfterAll
    public static void tearDown() {
        // Останавливаем embedded Redis после выполнения всех тестов
        redisServer.stop();
    }

    @Autowired
    private YourRedisService yourRedisService;

    @Test
    public void testSaveAndGetFromRedis() {
        // Тестируем сохранение и получение данных из Redis
        String key = "testKey";
        String value = "testValue";

        // Сохраняем данные в Redis
        yourRedisService.saveToRedis(key, value);

        // Получаем данные из Redis
        String result = yourRedisService.getFromRedis(key);

        // Проверяем, что данные были сохранены и получены корректно
        assertEquals(value, result);
    }
}
