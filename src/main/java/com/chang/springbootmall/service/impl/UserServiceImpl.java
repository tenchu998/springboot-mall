package com.chang.springbootmall.service.impl;

import com.chang.springbootmall.controller.vo.UserRegisterRequestVo;
import com.chang.springbootmall.model.User;
import com.chang.springbootmall.repo.UserRepo;
import com.chang.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public Integer register(UserRegisterRequestVo vo) {
        return userRepo.createUser(vo);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepo.getUserById(userId);
    }
}
