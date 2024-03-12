package com.chang.springbootmall.repo;

import com.chang.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderRepo {
    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);
}
