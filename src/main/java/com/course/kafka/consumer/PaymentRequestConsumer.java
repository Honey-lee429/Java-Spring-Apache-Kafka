package com.course.kafka.consumer;

import com.course.kafka.entity.PaymentRequest;
import com.course.kafka.entity.PaymentRequestCachKey;
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
public class PaymentRequestConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(PaymentRequestConsumer.class);

    @Autowired
    @Qualifier("cachePaymentRequest")
    private Cache<PaymentRequestCachKey, Boolean> cache;

    private boolean isExistInCache(PaymentRequestCachKey purchaseRequestId) {
        return Optional.ofNullable(cache.getIfPresent(purchaseRequestId)).orElse(false);
    }

    @KafkaListener(topics = "t-payment-request", groupId = "")
    public void consumer(String message) throws JsonProcessingException {
        // to convert Json String to Employee class, we will use Object Mapper using readValue method
        var paymentRequest = objectMapper.readValue(message, PaymentRequest.class);

        var cachekey = new PaymentRequestCachKey(paymentRequest.getPaymentNumber(), paymentRequest.getAmount(),
                paymentRequest.getTransactionType());

        var processed = isExistInCache(cachekey);

        if (processed){
            return;
        }

        LOG.info("Processing {} ", paymentRequest);
        cache.put(cachekey, true);
    }
}
