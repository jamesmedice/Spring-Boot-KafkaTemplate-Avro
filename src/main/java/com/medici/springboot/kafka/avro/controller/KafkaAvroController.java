package com.medici.springboot.kafka.avro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medici.springboot.kafka.avro.model.Student;
import com.medici.springboot.kafka.avro.service.ProducerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class KafkaAvroController {

	@Autowired
	ProducerService producerService;

	@PostMapping(value = "/avro")
	public ResponseEntity<?> kafkaMessage(@RequestBody Student message) {
		boolean status = producerService.sendMessage(message);
		return ResponseEntity.ok().body(status);
	}

}
