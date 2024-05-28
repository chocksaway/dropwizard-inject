package com.chocksaway.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@NotNull
@Getter
public class AppConfiguration extends Configuration {
    @JsonProperty("redis")
    private RedisConfiguration redisConfiguration;
}
