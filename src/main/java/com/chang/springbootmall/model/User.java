package com.chang.springbootmall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer userId;
    private String email;
    private String password;
    private Date createdDate;
    private Date lastModifiedDate;
}
