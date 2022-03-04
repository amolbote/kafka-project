package com.amol.kafkaproject.controller;

import com.amol.kafkaproject.domain.ProductEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductEventController {

    @PostMapping("/v1/add-product-event")
    public ResponseEntity<ProductEvent> addProductEvent(@RequestBody ProductEvent productEvent){
        //invoke kafka producer
        return ResponseEntity.status(HttpStatus.CREATED).body(productEvent);
    }
}
