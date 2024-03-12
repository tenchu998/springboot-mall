package com.chang.springbootmall.repo;

import com.chang.springbootmall.controller.vo.UserRegisterRequestVO;
import com.chang.springbootmall.model.User;

public interface UserRepo {
    Integer createUser(UserRegisterRequestVO vo);

    User getUserById(Integer userId);

    boolean checkUserExistByEmail(String email);

    User getUserByEmail(String email);
}
