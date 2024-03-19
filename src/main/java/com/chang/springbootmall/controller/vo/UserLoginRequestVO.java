package com.chang.springbootmall.controller.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestVO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
