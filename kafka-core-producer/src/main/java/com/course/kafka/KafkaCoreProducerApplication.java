package com.course.kafka;

import com.course.kafka.entity.Employee;
import com.course.kafka.producer.Employee2JsonProducer;
import com.course.kafka.producer.EmployeeJsonProducer;
import com.course.kafka.producer.HelloKafkaProducer;
import com.course.kafka.producer.KafkaKeyProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.PrimitiveIterator;
import java.util.concurrent.ThreadLocalRandom;
@EnableScheduling
@SpringBootApplication
public class KafkaCoreProducerApplication implements CommandLineRunner {

	@Autowired
	private KafkaKeyProducer kafkaKeyProducer;

	@Autowired
	private EmployeeJsonProducer employeeJsonProducer;

	@Autowired
	private Employee2JsonProducer employee2JsonProducer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaCoreProducerApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 30; i++){
			var key = "key-" + (i%4);
			var value = "value" + i + " with the key " + key;
			kafkaKeyProducer.send(key, value);
		}
	}
}
