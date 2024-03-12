package com.chang.springbootmall.service.impl;

import com.chang.springbootmall.controller.vo.ProductQueryVO;
import com.chang.springbootmall.controller.vo.ProductRequestVO;
import com.chang.springbootmall.model.Product;
import com.chang.springbootmall.repo.ProductRepo;
import com.chang.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> findProducts(ProductQueryVO productQueryVo) {
        return productRepo.findProducts(productQueryVo);
    }

    @Override
    public Product findProductById(Integer productId) {
        return productRepo.findProductById(productId);

    }

    @Override
    public Integer createProduct(ProductRequestVO requestVo) {
        return productRepo.createProduct(requestVo);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequestVO requestVo) {
        productRepo.updateProduct(productId, requestVo);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepo.deleteProduct(productId);
    }

    @Override
    public Integer countProduct(ProductQueryVO productQueryVo) {
        return productRepo.countProduct(productQueryVo);
    }
}
