package com.chang.springbootmall.controller;

import com.chang.springbootmall.controller.vo.UserRegisterRequestVo;
import com.chang.springbootmall.model.User;
import com.chang.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequestVo vo) {
//        boolean isExists = userService.checkUserExistByEmail(vo.getEmail());
//        if(isExists){
//            // 帳號被註冊過 需返回
//        }
        Integer userId = userService.register(vo);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
