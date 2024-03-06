package com.chang.springbootmall.controller.vo;

import com.chang.springbootmall.constant.ProductCategory;
import lombok.Data;

@Data
public class ProductQueryVo {
    private ProductCategory category;
    private String search;
}
