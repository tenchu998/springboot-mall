package com.chang.springbootmall.service.impl;

import com.chang.springbootmall.controller.vo.UserLoginRequestVO;
import com.chang.springbootmall.controller.vo.UserRegisterRequestVO;
import com.chang.springbootmall.model.User;
import com.chang.springbootmall.repo.UserRepo;
import com.chang.springbootmall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public Integer register(UserRegisterRequestVO vo) {
        boolean isExists = userRepo.checkUserExistByEmail(vo.getEmail());
        if (isExists) {
            // 帳號被註冊過 需返回
            log.warn("信箱{}已被註冊過", vo.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "信箱已被註冊過");
        }
        // password加密
        String hashedPassword = DigestUtils.md5DigestAsHex(vo.getPassword().getBytes());
        vo.setPassword(hashedPassword);

        return userRepo.createUser(vo);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepo.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequestVO vo) {
        boolean isExists = userRepo.checkUserExistByEmail(vo.getEmail());
        if (!isExists) {
            // 帳號未被註冊
            log.warn("信箱{}尚未被註冊過，無法進行登入", vo.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "信箱尚未被註冊過，無法進行登入");
        }

        User userByEmail = userRepo.getUserByEmail(vo.getEmail());
        // hash使用者的密碼
        String hashedPassword = DigestUtils.md5DigestAsHex(vo.getPassword().getBytes());
        //比對
        boolean isPasswordCorrect = hashedPassword.equals(userByEmail.getPassword());
        if (isPasswordCorrect) {
            return userByEmail;
        } else {
            log.warn("email{}之登入密碼不正確，無法進行登入", vo.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
