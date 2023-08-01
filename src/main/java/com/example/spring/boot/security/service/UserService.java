package com.example.spring.boot.security.service;

import com.example.spring.boot.security.entity.User;
import com.example.spring.boot.security.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);
}
