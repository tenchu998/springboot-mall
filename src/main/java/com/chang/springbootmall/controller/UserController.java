package com.chang.springbootmall.controller;

import com.chang.springbootmall.controller.vo.UserLoginRequestVO;
import com.chang.springbootmall.controller.vo.UserRegisterRequestVO;
import com.chang.springbootmall.model.User;
import com.chang.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequestVO vo) {

        Integer userId = userService.register(vo);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequestVO vo) {
        User user = userService.login(vo);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
