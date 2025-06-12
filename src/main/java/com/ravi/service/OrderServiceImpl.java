package com.ravi.service;

import com.ravi.dto.ProductDto;
import com.ravi.entity.Order;
import com.ravi.model.OrderRequest;
import com.ravi.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired (required=false)
    OrderRepository orderRepository;

    @Autowired
    WebClient webClient;


    @Override
    public Order placeOrder(OrderRequest orderRequest) {

        log.info("before palcing order");

        ProductDto productDto=webClient.get()
                            .uri(uriBuilder -> uriBuilder.path("/products/fetchProduct/"+orderRequest.getProductId()).build())
                            .retrieve()
                            .bodyToMono(ProductDto.class)
                            .block();
        log.info("productDto=="+productDto);
        return Optional.ofNullable(productDto)
                .map(dto -> {
                    Order order = new Order();
                    order.setProductId(dto.getProductId());
                    order.setProductName(dto.getProductName());
                    order.setProductPrice(dto.getPrice());

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String formattedDate = formatter.format(new Date());
                    order.setOrderDate(formattedDate);
                    order.setOrderStatus("PENDING");

                    return orderRepository.save(order);
                })
                .orElse(null);
    }


    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
