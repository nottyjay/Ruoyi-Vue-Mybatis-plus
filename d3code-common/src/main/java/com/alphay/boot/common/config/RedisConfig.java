package com.alphay.boot.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("common-redis-config")
@Data
public class RedisConfig {

  @Value("${spring.redis.host}")
  private String host;

  @Value("${spring.redis.port}")
  private Integer port;

  @Value("${spring.redis.password}")
  private String password;

  @Value("${spring.redis.timeout}")
  private Integer timeout;

  @Value("${spring.redis.database}")
  private Integer index;

  @Value("${spring.redis.lettuce.pool.min-idle}")
  private Integer minIdle;

  @Value("${spring.redis.lettuce.pool.max-idle}")
  private Integer maxIdle;

  @Value("${spring.redis.lettuce.pool.max-active}")
  private Integer maxTotal;

  @Value("${spring.redis.lettuce.pool.max-wait}")
  private Integer maxWait;
}
