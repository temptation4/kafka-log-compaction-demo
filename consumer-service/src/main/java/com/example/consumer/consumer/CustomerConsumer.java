package com.example.consumer.consumer;

import com.example.consumer.dto.Customer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerConsumer {

    @KafkaListener(
            topics = "customer-topic",
            groupId = "customer-group"
    )
    public void consume(Customer customer) {

        System.out.println("Received Customer");

        System.out.println(customer);

    }
}