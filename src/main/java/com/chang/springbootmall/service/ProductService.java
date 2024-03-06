package com.chang.springbootmall.service;

import com.chang.springbootmall.constant.ProductCategory;
import com.chang.springbootmall.controller.vo.ProductRequestVo;
import com.chang.springbootmall.model.Product;

import java.util.List;


public interface ProductService {
    List<Product> findProducts(ProductCategory category, String search);

    Product findProductById(Integer productId);

    Integer createProduct(ProductRequestVo requestVo);

    void updateProduct(Integer productId, ProductRequestVo requestVo);

    void deleteProduct(Integer productId);
}
