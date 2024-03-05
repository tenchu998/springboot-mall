package com.chang.springbootmall.repo;

import com.chang.springbootmall.controller.vo.ProductRequestVo;
import com.chang.springbootmall.model.Product;

public interface ProductRepo {

    public Product findProductById(Integer productId);

    Integer createProduct(ProductRequestVo requestVo);
}
