package com.course.kafka.consumer;

import com.course.kafka.entity.PurchaseRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseRequestConsumer {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Qualifier("cachePurchaseRequest")
    private Cache<Integer, Boolean> cache;

    private Boolean isExistsInCache(int purchaseRequesteId) {
        return Optional.ofNullable(cache.getIfPresent(purchaseRequesteId))
                .orElse(false);
    }

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestConsumer.class);

    @KafkaListener(topics = "t-purchase-request", groupId = "pr")
    private void consume(String message) throws JsonProcessingException {
        var purchaseRequest = objectMapper.readValue(message, PurchaseRequest.class);
        var processed = isExistsInCache(purchaseRequest.getId());
        if(processed){
            return;
        }
        LOG.info("Purchase is processing {}", purchaseRequest);

        //we must put the id in cache, so the nest possible duplicate msg will be ignored
        cache.put(purchaseRequest.getId(), true);
    }
}
