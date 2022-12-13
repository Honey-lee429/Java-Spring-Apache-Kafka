package com.course.kafka.consumer;

import com.course.kafka.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class Image2Consumer {

    @Autowired
    private ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(Image2Consumer.class);



    //Spring provides annotation @RetryableTopic to configure non blocking retry
    @RetryableTopic(
            //Spring engine will creates some topic automatically to handle the non blocking mechanism (autoCreateTopics = "true")
            autoCreateTopics = "true", attempts = "4",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_DELAY_VALUE,
            backoff = @Backoff(delay = 3000, maxDelay = 10_000, multiplier = 1.5, random = true),
            dltTopicSuffix = "-dead"
    )
    //we use the consumer record to see the partition
    @KafkaListener(topics = "t-image2", concurrency = "2")
    public void consume(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        // to convert Json String to Object class, we will use Object Mapper using readValue method
        var image = objectMapper.readValue(consumerRecord.value(),Image.class);

        //we will simulate the failed API call if the image type is "svg".
        if (image.getType().equalsIgnoreCase("svg")){
            log.warn("Throwing exception on partition {} for image {}", consumerRecord.partition(), image);
            throw new IllegalArgumentException("Simulate API call failed");
        }
        log.info("Processing on partition {} for image {}",consumerRecord.partition(), image);

    }
}
