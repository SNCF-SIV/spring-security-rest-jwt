package com.sncf.siv.poc.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@PropertySource("classpath:application.properties")
@EnableMongoRepositories("com.sncf.siv.poc.security.repository")
public class DatabaseConfig {

    @Value("${db.mongo.host}")
    private String host;

    @Value("${db.mongo.database}")
    private String database;

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        final MongoClient mongoClient = new MongoClient(host);
        return new SimpleMongoDbFactory(mongoClient, database);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }
}
