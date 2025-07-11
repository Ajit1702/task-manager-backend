package com.springbootkafka.task_manager.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootkafka.task_manager.dto.TaskEventDto;
import com.springbootkafka.task_manager.model.Task;




@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
	 @Autowired
	 private KafkaTemplate<String, String> kafkaTemplate;
	 
	 private static final String TOPIC = "task-events";
	 private final ObjectMapper objectMapper = new ObjectMapper();
	 
	 
	 public void sendTaskEvent(String eventType, Object payload) {
		 Map<String, Object> event = new HashMap<>();
		 
	        event.put("eventType", eventType);
	        event.put("payload", payload);
	        event.put("timestamp", Instant.now().toString());
	        
	        try {
	            String message = objectMapper.writeValueAsString(event);
	            kafkaTemplate.send(TOPIC, message);
	            log.info("✅ Sent Kafka message: {}", message);
	        } catch (JsonProcessingException e) {
	            log.error("❌ Failed to send Kafka message", e);
	        }
	 }
}
