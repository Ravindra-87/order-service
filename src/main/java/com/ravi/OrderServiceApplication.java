package com.ravi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;


@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class})
public class OrderServiceApplication {

/*
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://purchasingfuture.zapto.org/") // required for uriBuilder to work
                .build();
    }*/



    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/") // required for uriBuilder to work
                .build();
    }



    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
