package com.chang.springbootmall.service.impl;

import com.chang.springbootmall.constant.ProductCategory;
import com.chang.springbootmall.controller.vo.ProductRequestVo;
import com.chang.springbootmall.model.Product;
import com.chang.springbootmall.repo.ProductRepo;
import com.chang.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> findProducts(ProductCategory category, String search) {
        return productRepo.findProducts(category, search);
    }

    @Override
    public Product findProductById(Integer productId) {
        return productRepo.findProductById(productId);

    }

    @Override
    public Integer createProduct(ProductRequestVo requestVo) {
        return productRepo.createProduct(requestVo);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequestVo requestVo) {
        productRepo.updateProduct(productId, requestVo);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepo.deleteProduct(productId);
    }
}
