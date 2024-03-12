package com.chang.springbootmall.service.impl;

import com.chang.springbootmall.controller.vo.BuyItem;
import com.chang.springbootmall.controller.vo.OrderRequestVO;
import com.chang.springbootmall.model.Order;
import com.chang.springbootmall.model.OrderItem;
import com.chang.springbootmall.model.Product;
import com.chang.springbootmall.repo.OrderRepo;
import com.chang.springbootmall.repo.ProductRepo;
import com.chang.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;

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
        // 建立準備傳入order的參數方便create
        int totalAmount = 0;
        // 建立竹筆訂單資料預先創立list
        List<OrderItem> orderItemList = new ArrayList<>();
        //尋找商品資訊
        List<BuyItem> itemList = orderRequestVO.getBuyItemList();
        for (BuyItem buyItem : itemList) {
            Product product = productRepo.findProductById(buyItem.getProductId());
            int price = product.getPrice() * buyItem.getQuantity();
            totalAmount += price;
            // 轉換
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
