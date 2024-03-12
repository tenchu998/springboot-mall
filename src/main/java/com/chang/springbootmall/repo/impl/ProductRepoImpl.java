package com.chang.springbootmall.repo.impl;

import com.chang.springbootmall.controller.vo.ProductQueryVO;
import com.chang.springbootmall.controller.vo.ProductRequestVO;
import com.chang.springbootmall.model.Product;
import com.chang.springbootmall.repo.ProductRepo;
import com.chang.springbootmall.repo.mapper.ProductMapper;
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
public class ProductRepoImpl implements ProductRepo {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> findProducts(ProductQueryVO productQueryVo) {
        String sql = "SELECT product_id,product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date " +
                "FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();
        sql = addFilteringSql(productQueryVo, sql, map);
        sql = sql + " ORDER BY " + productQueryVo.getOrderBy() + " " + productQueryVo.getSort();
        // 筆數
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryVo.getLimit());
        map.put("offset", productQueryVo.getOffset());
        return namedParameterJdbcTemplate.query(sql, map, new ProductMapper());
    }

    @Override
    public Product findProductById(Integer productId) {
        String sql = "SELECT product_id,product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        List<Product> products = namedParameterJdbcTemplate.query(sql, map, new ProductMapper());
        if (products.size() > 0) {
            return products.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequestVO requestVo) {
        String sql = "INSERT INTO product (product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, " +
                ":price, :stock, :description, :createdDate, :lastModifiedDate);";
        Map<String, Object> map = new HashMap<>();
        map.put("productName", requestVo.getProductName());
        map.put("category", requestVo.getCategory().toString());
        map.put("imageUrl", requestVo.getImageUrl());
        map.put("price", requestVo.getPrice());
        map.put("stock", requestVo.getStock());
        map.put("description", requestVo.getDescription());
        map.put("createdDate", new Date());
        map.put("lastModifiedDate", new Date());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateProduct(Integer productId, ProductRequestVO requestVo) {
        String sql = "UPDATE product SET product_name= :productName, category= :category, image_url= :imageUrl, " +
                "price= :price, stock= :stock, description= :description, last_modified_date= :lastModifiedDate " +
                "WHERE product_id=:productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productName", requestVo.getProductName());
        map.put("category", requestVo.getCategory().toString());
        map.put("imageUrl", requestVo.getImageUrl());
        map.put("price", requestVo.getPrice());
        map.put("stock", requestVo.getStock());
        map.put("description", requestVo.getDescription());
        map.put("lastModifiedDate", new Date());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id= :productId";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Integer countProduct(ProductQueryVO productQueryVo) {
        String sql = "SELECT COUNT(*) FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();
        sql = addFilteringSql(productQueryVo, sql, map);
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    private String addFilteringSql(ProductQueryVO productQueryVo, String sql, Map<String, Object> map) {
        if (productQueryVo.getCategory() != null) {
            sql += " AND category= :category";
            map.put("category", productQueryVo.getCategory().name());
        }
        if (productQueryVo.getSearch() != null) {
            sql += " AND product_name LIKE :search";
            map.put("search", "%" + productQueryVo.getSearch() + "%");
        }
        return sql;
    }
}
