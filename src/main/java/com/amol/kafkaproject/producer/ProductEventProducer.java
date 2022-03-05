package com.amol.kafkaproject.producer;

import com.amol.kafkaproject.domain.ProductEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class ProductEventProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper; // to convert java object to json string

    public void sendProductEvent(ProductEvent productEvent) throws JsonProcessingException {
        Integer key = productEvent.getId();
        String value = objectMapper.writeValueAsString(productEvent);
        ListenableFuture<SendResult<Integer, String>> listenableFuture = kafkaTemplate.sendDefault(key, value);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error sending message for the key : {} and value is : {}, Exception is : {}", key, value, ex.getMessage());
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("Message sent successfully for the key : {} and value is : {}, partition is : {}", key, value, result.getRecordMetadata().partition());
    }

    public SendResult<Integer, String> sendProductEventSynchronously(ProductEvent productEvent) throws JsonProcessingException {
        Integer key = productEvent.getId();
        String value = objectMapper.writeValueAsString(productEvent);
        SendResult<Integer, String> sendResult = null;
        try {
            //get() will wait for response
            sendResult = kafkaTemplate.sendDefault(key, value).get();
            log.info("Message sent successfully for the key : {} and value is : {}, partition is : {}", key, value, sendResult.getRecordMetadata().partition());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            log.error("Error sending message for the key : {} and value is : {}, Exception is : {}", key, value, e.getMessage());
        } catch (Exception e) {
            log.error("Error sending message for the key : {} and value is : {}, Exception is : {}", key, value, e.getMessage());
        }
        return sendResult;
    }
}
