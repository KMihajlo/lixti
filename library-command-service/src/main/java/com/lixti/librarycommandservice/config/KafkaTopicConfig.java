package com.lixti.librarycommandservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration for the Kafka Topic to create the topics.
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createTopic() {
        return TopicBuilder.name("book-event-topic").build();
    }
}
