package com.chocksaway.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@NotNull
@Getter
@ToString
public class RedisConfiguration {
    @JsonProperty("uri") private String uri;
    @JsonProperty("port") private String port;
    @JsonProperty("idleConnectionTimeout") private int idleConnectionTimeout;
    @JsonProperty("connectTimeout") private int connectTimeout;
    @JsonProperty("timeout") private int timeout;
    @JsonProperty("retryAttempts") private int retryAttempts;
    @JsonProperty("retryInterval") private int retryInterval;
}

