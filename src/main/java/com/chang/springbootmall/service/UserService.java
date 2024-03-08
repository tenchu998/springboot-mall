package com.chang.springbootmall.service;

import com.chang.springbootmall.controller.vo.UserRegisterRequestVo;
import com.chang.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequestVo vo);

    User getUserById(Integer userId);
}
