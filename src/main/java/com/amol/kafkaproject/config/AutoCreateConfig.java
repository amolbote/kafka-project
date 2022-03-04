package com.amol.kafkaproject.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
//This code will be executed only for local profile
@Profile("local")
public class AutoCreateConfig {

    @Bean
    public NewTopic productEvents (){
        return TopicBuilder.name("auto-product-events")
                .partitions(3)
                .replicas(3)
                .build();
    }
}
