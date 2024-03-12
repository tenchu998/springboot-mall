package com.chang.springbootmall.service;

import com.chang.springbootmall.controller.vo.UserLoginRequestVO;
import com.chang.springbootmall.controller.vo.UserRegisterRequestVO;
import com.chang.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequestVO vo);

    User getUserById(Integer userId);

    User login(UserLoginRequestVO vo);
}
