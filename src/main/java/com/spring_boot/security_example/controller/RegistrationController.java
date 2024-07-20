package com.spring_boot.security_example.controller;

import com.spring_boot.security_example.entity.User;
import com.spring_boot.security_example.event.RegistrationCompleteEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.spring_boot.security_example.model.UserModel;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import com.spring_boot.security_example.service.UserService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    private final ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User Verifies Successfully";
        }else{
            return "Bad User";
        }
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(){
        return null;
    }

    private String applicationUrl(HttpServletRequest request) {
        return  "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
