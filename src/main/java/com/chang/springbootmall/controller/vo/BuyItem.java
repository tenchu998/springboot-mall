package com.chang.springbootmall.controller.vo;

import lombok.Data;
import lombok.NonNull;

@Data
public class BuyItem {

    @NonNull
    private Integer productId;
    @NonNull
    private Integer quantity;
}
