package com.chang.springbootmall.controller;

import com.chang.springbootmall.constant.ProductCategory;
import com.chang.springbootmall.controller.vo.ProductQueryVo;
import com.chang.springbootmall.controller.vo.ProductRequestVo;
import com.chang.springbootmall.model.Product;
import com.chang.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) ProductCategory category,
                                                     @RequestParam(required = false) String search) {
        ProductQueryVo productQueryVo = new ProductQueryVo();
        productQueryVo.setCategory(category);
        productQueryVo.setSearch(search);
        List<Product> productList = productService.findProducts(productQueryVo);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.findProductById(productId);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequestVo requestVo) {
        Integer productId = productService.createProduct(requestVo);

        return ResponseEntity
                .status(HttpStatus.CREATED).body(productService.findProductById(productId));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @Valid @RequestBody ProductRequestVo requestVo) {
        Product product = productService.findProductById(productId);
        if (product == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId, requestVo);

        return ResponseEntity
                .status(HttpStatus.OK).body(productService.findProductById(productId));
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
