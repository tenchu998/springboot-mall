package com.chang.springbootmall.controller;

import com.chang.springbootmall.model.Product;
import com.chang.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable Integer productId) {
        return productService.findProductById(productId);
    }

}
