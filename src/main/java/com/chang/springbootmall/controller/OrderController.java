package com.chang.springbootmall.controller;

import com.chang.springbootmall.controller.vo.OrderRequestVO;
import com.chang.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId ,
                                         @RequestBody @Valid OrderRequestVO orderRequestVO){
       Integer ordreId = orderService.createOrder(userId,orderRequestVO);

       return  ResponseEntity.status(HttpStatus.CREATED).body(ordreId);
    }
}
