package com.chang.springbootmall.service.impl;

import com.chang.springbootmall.controller.vo.UserRegisterRequestVo;
import com.chang.springbootmall.model.User;
import com.chang.springbootmall.repo.UserRepo;
import com.chang.springbootmall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public Integer register(UserRegisterRequestVo vo) {
        boolean isExists = userRepo.checkUserExistByEmail(vo.getEmail());
        if (isExists) {
            // 帳號被註冊過 需返回
            log.warn("信箱{}已被註冊過", vo.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "信箱已被註冊過");
        }

        return userRepo.createUser(vo);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepo.getUserById(userId);
    }
}
