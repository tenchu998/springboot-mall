package com.chang.springbootmall.repo;

import com.chang.springbootmall.controller.vo.UserRegisterRequestVo;
import com.chang.springbootmall.model.User;

public interface UserRepo {
    Integer createUser(UserRegisterRequestVo vo);

    User getUserById(Integer userId);
}
