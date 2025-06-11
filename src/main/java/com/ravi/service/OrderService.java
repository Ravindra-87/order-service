package com.ravi.service;

import com.ravi.entity.Order;
import com.ravi.model.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order placeOrder(OrderRequest orderRequest);
    public List<Order> getOrders();
}
