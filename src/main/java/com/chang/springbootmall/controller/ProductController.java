package com.chang.springbootmall.controller;

import com.chang.springbootmall.constant.ProductCategory;
import com.chang.springbootmall.controller.vo.ProductQueryVo;
import com.chang.springbootmall.controller.vo.ProductRequestVo;
import com.chang.springbootmall.model.Product;
import com.chang.springbootmall.service.ProductService;
import com.chang.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // 商品搜尋條件
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            // 排序
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "DESC") String sort,
            // 分頁及顯示筆數
            @RequestParam(defaultValue = "5") @Min(0) @Max(500) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        ProductQueryVo productQueryVo = new ProductQueryVo();
        productQueryVo.setCategory(category);
        productQueryVo.setSearch(search);
        productQueryVo.setOrderBy(orderBy);
        productQueryVo.setSort(sort);
        productQueryVo.setLimit(limit);
        productQueryVo.setOffset(offset);
        List<Product> productList = productService.findProducts(productQueryVo);
        // 計算數量
        Integer total = productService.countProduct(productQueryVo);
        //調整自訂的response
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);


        return ResponseEntity.status(HttpStatus.OK).body(page);
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
