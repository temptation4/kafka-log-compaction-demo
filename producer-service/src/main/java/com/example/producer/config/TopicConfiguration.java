package com.example.producer.config;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.TopicBuilder;
@Configuration
public class TopicConfiguration{
 @Bean
 public NewTopic customerTopic(){
  return TopicBuilder.name("customer-topic")
   .partitions(3)
   .replicas(1)
   .config(TopicConfig.CLEANUP_POLICY_CONFIG,TopicConfig.CLEANUP_POLICY_COMPACT)
   .build();
 }
}
