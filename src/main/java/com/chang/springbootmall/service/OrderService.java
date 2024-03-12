package com.chang.springbootmall.service;

import com.chang.springbootmall.controller.vo.OrderRequestVO;
import com.chang.springbootmall.model.Order;

public interface OrderService {
    Integer createOrder(Integer userId, OrderRequestVO orderRequestVO);

    Order getOrderById(Integer ordreId);

}
