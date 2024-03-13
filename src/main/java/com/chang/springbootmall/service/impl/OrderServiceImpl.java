package com.chang.springbootmall.service.impl;

import com.chang.springbootmall.controller.vo.BuyItem;
import com.chang.springbootmall.controller.vo.OrderRequestVO;
import com.chang.springbootmall.model.Order;
import com.chang.springbootmall.model.OrderItem;
import com.chang.springbootmall.model.Product;
import com.chang.springbootmall.repo.OrderRepo;
import com.chang.springbootmall.repo.ProductRepo;
import com.chang.springbootmall.repo.UserRepo;
import com.chang.springbootmall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderRepo.getOrderById(orderId);

        List<OrderItem> orderItemList = orderRepo.getOrderItemById(orderId);
        order.setOderItemList(orderItemList);

        return order;
    }

    @Override
    @Transactional
    public Integer createOrder(Integer userId, OrderRequestVO orderRequestVO) {
        // 檢核使用者存在
        if (null == userRepo.getUserById(userId)) {
            log.warn("該userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該userId不存在");
        }
        // 建立準備傳入order的參數方便create
        int totalAmount = 0;
        // 建立竹筆訂單資料預先創立list
        List<OrderItem> orderItemList = new ArrayList<>();
        //尋找商品資訊
        List<BuyItem> itemList = orderRequestVO.getBuyItemList();
        for (BuyItem buyItem : itemList) {
            Product product = productRepo.findProductById(buyItem.getProductId());
            // 檢查商品庫存是否足夠
            if (product.getStock() < buyItem.getQuantity()) {
                log.warn("該商品編號{} ：商品名 ：{} ，庫存數量不足，無法購買，剩餘庫存{}，預計購買數量 {}",
                        buyItem.getProductId(), product.getProductName(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該商品庫存數量不足，無法購買");
            }
            // 扣除商品數量
            productRepo.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());
            // 計算總價錢
            int price = product.getPrice() * buyItem.getQuantity();
            totalAmount += price;
            // 轉換buyItem to orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(price);
            orderItemList.add(orderItem);
        }
        Integer orderId = orderRepo.createOrder(userId, totalAmount);

        orderRepo.createOrderItem(orderId, orderItemList);

        return orderId;
    }

}
