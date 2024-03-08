package com.chang.springbootmall.controller.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequestVo {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
