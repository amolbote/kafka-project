package com.amol.kafkaproject.controller;

import com.amol.kafkaproject.domain.ProductEvent;
import com.amol.kafkaproject.producer.ProductEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductEventController {

    @Autowired
    ProductEventProducer productEventProducer;

    @PostMapping("/v1/add-product-event")
    public ResponseEntity<ProductEvent> addProductEvent(@RequestBody ProductEvent productEvent) throws JsonProcessingException {
        //invoke kafka producer
        productEventProducer.sendProductEvent(productEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(productEvent);
    }
}
