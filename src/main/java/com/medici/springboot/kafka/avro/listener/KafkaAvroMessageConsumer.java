package com.medici.springboot.kafka.avro.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.medici.springboot.kafka.avro.model.Student;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaAvroMessageConsumer {

	@KafkaListener(topics = "local-avro-topic", groupId = "local-avro")
	public void listen(Student message) {
		log.info("Received Messasge in group : {}", message);
	}
}
