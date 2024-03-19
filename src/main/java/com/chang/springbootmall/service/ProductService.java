package com.chang.springbootmall.service;

import com.chang.springbootmall.controller.vo.ProductQueryVO;
import com.chang.springbootmall.controller.vo.ProductRequestVO;
import com.chang.springbootmall.model.Product;

import java.util.List;


public interface ProductService {
    List<Product> findProducts(ProductQueryVO productQueryVo);

    Product findProductById(Integer productId);

    Integer createProduct(ProductRequestVO requestVo);

    void updateProduct(Integer productId, ProductRequestVO requestVo);

    void deleteProduct(Integer productId);

    Integer countProduct(ProductQueryVO productQueryVo);
}
