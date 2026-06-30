package com.example.producer.service;

import com.example.producer.dto.Customer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerProducer {
    private final KafkaTemplate<String, Customer> kafkaTemplate;

    public CustomerProducer(KafkaTemplate<String, Customer> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(Customer customer) {
        kafkaTemplate.send("customer-topic", customer.getCustomerId(), customer);
    }
}
