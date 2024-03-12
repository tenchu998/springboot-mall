package com.chang.springbootmall.service;

import com.chang.springbootmall.controller.vo.OrderRequestVO;

public interface OrderService {
    Integer createOrder(Integer userId, OrderRequestVO orderRequestVO);
}
