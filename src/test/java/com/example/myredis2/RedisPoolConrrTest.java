package com.example.myredis2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class RedisPoolConrrTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        int threadCount = 10;
        int iterationsPerThread = 100;

        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    for (int j = 0; j < iterationsPerThread; j++) {
                        performRedisOperation();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
    }

    private void performRedisOperation() {
        // 通过 redisTemplate 进行 Redis 连接的并发操作
        redisTemplate.opsForValue().increment("counter", 1);
        System.out.println("Current counter value: " + redisTemplate.opsForValue().get("counter"));
    }

}
