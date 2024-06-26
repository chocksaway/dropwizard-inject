package com.chocksaway.service;

import com.chocksaway.config.RedisConfiguration;
import com.chocksaway.model.Person;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.dropwizard.lifecycle.Managed;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Singleton
public class RedisService implements Managed {
    Logger logger = LoggerFactory.getLogger(RedisService.class);

    private final RedisConfiguration redisConfiguration;
    private RedissonClient client;

    @Inject
    public RedisService(final RedisConfiguration redisConfiguration){
        this.redisConfiguration = redisConfiguration;
        logger.info("Configuration {}", redisConfiguration);
    }

    @Override
    public void start() throws Exception {
        String url = String.format(
                "redis://%s:%s",
                redisConfiguration.getUri(),
                redisConfiguration.getPort()
        );
        Config config = new Config();
        config.useSingleServer()
                .setConnectTimeout(redisConfiguration.getConnectTimeout())
                .setRetryAttempts(redisConfiguration.getRetryAttempts())
                .setRetryInterval(redisConfiguration.getRetryInterval())
                .setIdleConnectionTimeout(redisConfiguration.getIdleConnectionTimeout())
                .setTimeout(redisConfiguration.getTimeout())
                .setAddress(url);

        client = Redisson.create(config);
    }

    @Override
    public void stop() throws Exception {
        client.shutdown();
    }

    public void addPerson(final Person person){
        RBucket<Person> bucket = client.getBucket(person.getFirstName());
        bucket.set(person);
    }

    public Optional<Person> getPerson(String name){
        RBucket<Person> bucket = client.getBucket(name);
        return Optional.ofNullable(bucket.get());
    }

    public Optional<Person> deletePerson(String name){
        RBucket<Person> bucket = client.getBucket(name);
        return Optional.ofNullable(bucket.getAndDelete());
    }

    public void updatePerson(String name, Person person){
        RBucket<Person> bucket = client.getBucket(name);
        bucket.set(person);
    }
}