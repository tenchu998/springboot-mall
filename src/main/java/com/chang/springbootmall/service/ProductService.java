package com.chang.springbootmall.service;

import com.chang.springbootmall.controller.vo.ProductRequestVo;
import com.chang.springbootmall.model.Product;


public interface ProductService {
    Product findProductById(Integer productId);

    Integer createProduct(ProductRequestVo requestVo);

}
