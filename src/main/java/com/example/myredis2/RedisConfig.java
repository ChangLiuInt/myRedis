package com.example.myredis2;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import io.lettuce.core.resource.ClientResources;

@Configuration
public class RedisConfig {

    @Autowired
    GenericObjectPoolConfig genericObjectPoolConfig;

    @Bean
    public LettucePoolingClientConfiguration poolingClientConfiguration() {
        return LettucePoolingClientConfiguration.builder()
                .poolConfig(genericObjectPoolConfig)
                .build();
    }
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration
                = new RedisStandaloneConfiguration("localhost", 6379);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, poolingClientConfiguration());
        lettuceConnectionFactory.setShareNativeConnection(false);
        return lettuceConnectionFactory;
    }


    private RedisNode hostAndPort(String host, int port) {
        return new RedisNode(host, port);
    }
}
