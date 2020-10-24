package com.medici.springboot.kafka.avro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import com.medici.springboot.kafka.avro.model.Student;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerService {

	@Value("${kafka.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, Student> kafkaTemplate;

	public boolean sendMessage(Student message) {
		try {

			ListenableFuture<SendResult<String, Student>> listenableFuture = kafkaTemplate.send(topicName, message);

			SuccessCallback<SendResult<String, Student>> successCallback = new SuccessCallback<SendResult<String, Student>>() {
				@Override
				public void onSuccess(SendResult<String, Student> result) {
					log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
				}
			};
			FailureCallback failureCallback = new FailureCallback() {
				@Override
				public void onFailure(Throwable ex) {
					log.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
				}
			};

			listenableFuture.addCallback(successCallback, failureCallback);

		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

		return true;
	}
}
