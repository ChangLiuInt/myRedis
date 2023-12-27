package com.example.myredis2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

@Configuration
@ConfigurationProperties(prefix = "common.redis.pool-config", ignoreInvalidFields = true)
public class ConnectionPoolProperties {

    @Value("${maxIdle:10}")
    private Integer maxIdle;

    @Value("${maxTotal:10}")
    private Integer maxTotal;

    @Value("${minIdle:10}")
    private Integer minIdle;

    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMinIdle(minIdle);
        return poolConfig;
    }
}