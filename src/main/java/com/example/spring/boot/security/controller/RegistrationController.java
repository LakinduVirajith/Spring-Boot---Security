package com.example.spring.boot.security.controller;

import com.example.spring.boot.security.entity.User;
import com.example.spring.boot.security.model.UserModel;
import com.example.spring.boot.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel){
        User user = userService.registerUser(userModel);
        return "Successfully Registered";
    }
}
