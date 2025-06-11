package com.ravi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ravi.entity.Order;
import com.ravi.exception.ErrorResponse;
import com.ravi.kafka.OrderEventProducer;
import com.ravi.model.OrderRequest;
import com.ravi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderEventProducer orderEventProducer ;

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {


        Order placedOrder = orderService.placeOrder(orderRequest);
        log.info("placeOrder called - "+placedOrder);

        if (placedOrder != null) {
            orderEventProducer.sendOrderCreatedEvent(placedOrder);
            return new ResponseEntity<>(placedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not available", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<Order>>  fetchAllOrders() {

        log.info("fetch All Orders{}");

        return Optional.ofNullable(orderService.getOrders())
                .map(orders -> new ResponseEntity<>(orders, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT));
    }
}
