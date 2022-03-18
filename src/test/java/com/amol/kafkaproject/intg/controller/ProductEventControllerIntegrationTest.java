package com.amol.kafkaproject.intg.controller;

import com.amol.kafkaproject.domain.Product;
import com.amol.kafkaproject.domain.ProductEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import  static  org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"default-product-events"}, partitions = 3)
@TestPropertySource(properties = {
        "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"
})
public class ProductEventControllerIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void postProductEvent(){
        //given
        Product product = Product.builder().id(9999).name("student").build();
        ProductEvent productEvent = ProductEvent.builder().id(1111).product(product).build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON.toString());

        HttpEntity<ProductEvent> httpEntity = new HttpEntity<>(productEvent,httpHeaders);

        //when
        ResponseEntity<ProductEvent> responseEntity = testRestTemplate.exchange("/v1/add-product-event", HttpMethod.POST, httpEntity, ProductEvent.class);

        //then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}
