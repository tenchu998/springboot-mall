package com.chang.springbootmall.repo.impl;

import com.chang.springbootmall.model.Order;
import com.chang.springbootmall.model.OrderItem;
import com.chang.springbootmall.repo.OrderRepo;
import com.chang.springbootmall.repo.mapper.OrderItemRowMapper;
import com.chang.springbootmall.repo.mapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepoImpl implements OrderRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<OrderItem> getOrderItemById(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                "FROM order_item as oi LEFT JOIN product as p ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM " +
                "`order` WHERE order_id = :orderId";
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        if (!orderList.isEmpty()) {
            return orderList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES " +
                " (:userId, :totalAmount, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        map.put("createdDate", new Date());
        map.put("lastModifiedDate", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) " +
                "VALUES(:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] mapSqlParameterSources = new MapSqlParameterSource[orderItemList.size()];
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            mapSqlParameterSources[i] = new MapSqlParameterSource();
            mapSqlParameterSources[i].addValue("orderId", orderId);
            mapSqlParameterSources[i].addValue("productId", orderItem.getProductId());
            mapSqlParameterSources[i].addValue("quantity", orderItem.getQuantity());
            mapSqlParameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, mapSqlParameterSources);
    }
}
