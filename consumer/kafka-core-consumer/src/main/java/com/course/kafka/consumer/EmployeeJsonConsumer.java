package com.course.kafka.consumer;

import com.course.kafka.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeJsonConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeJsonConsumer.class);

    @KafkaListener(topics = "t-emploeyy2")
    public void listener(String message) throws JsonProcessingException {
       // to convert Json String to Employee class, we will use Object Mapper using readValue method
        var employee = objectMapper.readValue(message, Employee.class);
        LOG.info("Employee is {}", employee);

    }
}
