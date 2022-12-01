package com.course.kafka.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service(value = "myFoodOrderErrorHandler")
public class FoodOrderHandler implements ConsumerAwareListenerErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FoodOrderHandler.class);

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        LOG.warn("Food order error, sending to elasticserch: {}, because: {}", message.getPayload(),
                exception.getMessage());

        //it's up to you wich exception we will rethrow, in this case we will rethrow all runtime exception

        if (exception.getCause() instanceof RuntimeException){
            throw exception;
        }

        return null;
    }
}
