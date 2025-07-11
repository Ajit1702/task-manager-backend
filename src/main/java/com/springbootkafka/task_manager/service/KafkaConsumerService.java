package com.springbootkafka.task_manager.service;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "task-events", groupId = "task-logger")
    public void consume(String message) {
        System.out.println("🔔 Kafka Message Received: " + message);
    }
}
