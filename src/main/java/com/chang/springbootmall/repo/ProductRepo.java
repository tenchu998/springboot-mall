package com.chang.springbootmall.repo;

import com.chang.springbootmall.controller.vo.ProductQueryVO;
import com.chang.springbootmall.controller.vo.ProductRequestVO;
import com.chang.springbootmall.model.Product;

import java.util.List;

public interface ProductRepo {

    List<Product> findProducts(ProductQueryVO productQueryVo);

    public Product findProductById(Integer productId);

    Integer createProduct(ProductRequestVO requestVo);

    void updateProduct(Integer productId, ProductRequestVO requestVo);

    void deleteProduct(Integer productId);

    Integer countProduct(ProductQueryVO productQueryVo);

    void updateStock(Integer productId, Integer stock);
}
