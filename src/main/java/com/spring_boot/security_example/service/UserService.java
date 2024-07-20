package com.spring_boot.security_example.service;

import com.spring_boot.security_example.entity.User;
import com.spring_boot.security_example.entity.VerificationToken;
import com.spring_boot.security_example.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
