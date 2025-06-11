package com.ravi.kafka;

import com.ravi.model.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "order-events-topic";

    public void sendOrderCreatedEvent(OrderRequest orderRequest) {

        kafkaTemplate.send(TOPIC, orderRequest.toString());

    }

}
