package com.chang.springbootmall.controller.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestVO {

    @NotEmpty
    private List<BuyItem> buyItemList;
}
