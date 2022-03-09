package com.amol.kafkaproject.controller;

import com.amol.kafkaproject.domain.ProductEvent;
import com.amol.kafkaproject.producer.ProductEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProductEventController {

    @Autowired
    ProductEventProducer productEventProducer;

    @PostMapping("/v1/add-product-event")
    public ResponseEntity<ProductEvent> addProductEvent(@RequestBody ProductEvent productEvent) throws JsonProcessingException {
        //invoke kafka producer
        log.info("------------- Before sendProductEvent -----------------------");
        //productEventProducer.sendProductEvent(productEvent);
        //SendResult<Integer, String> sendResult = productEventProducer.sendProductEventSynchronously(productEvent);
        //productEventProducer.sendProductEventUsingTopic(productEvent);
        productEventProducer.sendProductEventUsingProducerRecord(productEvent);
        //log.info("SendResult is : {}", sendResult.toString());
        log.info("------------- After sendProductEvent -----------------------");
        return ResponseEntity.status(HttpStatus.CREATED).body(productEvent);
    }
}
