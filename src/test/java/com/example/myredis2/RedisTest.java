package com.example.myredis2;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Test
    public void testRedisLoadBalancer() {
        //集群模式下的测试，负载均衡。
        //本文的负载均衡，是使得key在1-1000，1001-2000，十个连接是否都会启用。
        for (int i = 0; i < 10; i++) {
            RedisConnection connection = redisConnectionFactory.getConnection();
            System.out.println("Connected to Redis Node: " + connection.serverCommands().info("server").get("role"));
            connection.close();
        }
    }
}

