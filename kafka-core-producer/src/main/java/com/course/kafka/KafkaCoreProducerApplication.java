package com.course.kafka;

import com.course.kafka.entity.Employee;
import com.course.kafka.entity.PaymentRequest;
import com.course.kafka.entity.PurchaseRequest;
import com.course.kafka.producer.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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

	@Autowired
	private PurchaseRequestProducer purchaseRequestProducer;

	@Autowired
	private PaymentRequetProducer paymentRequetProducer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaCoreProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws JsonProcessingException {
		var pr1 = new PurchaseRequest(5551, "PR-Frist", 991, "R$");
		var pr2 = new PurchaseRequest(5552, "PR-Second", 992, "R$");
		var pr3 = new PurchaseRequest(5553, "PR-Third", 993, "R$");
		purchaseRequestProducer.sendMessage(pr1);
		purchaseRequestProducer.sendMessage(pr2);
		purchaseRequestProducer.sendMessage(pr3);
		//pretend that there is something wrong, so the producer accidentally publish same message for
		//the first purchase request
		purchaseRequestProducer.sendMessage(pr1);


		var pay1 = new PaymentRequest("1",100, "R$", "note something","PIX");
		var pay2 = new PaymentRequest("2",200, "R$", "note something","TED");
		var pay3 = new PaymentRequest("3",300, "R$", "note something","DOC");
		paymentRequetProducer.sendMessage(pay1);
		paymentRequetProducer.sendMessage(pay2);
		paymentRequetProducer.sendMessage(pay3);


	}
	/*@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 30; i++){
			var key = "key-" + (i%4);
			var value = "value" + i + " with the key " + key;
			kafkaKeyProducer.send(key, value);
		}
	}*/

}
