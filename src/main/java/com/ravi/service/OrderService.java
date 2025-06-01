package com.ravi.service;

import com.ravi.model.OrderRequest;

public interface OrderService {

    public Long placeOrder(OrderRequest orderRequest);

}
