package com.spring_boot.security_example.controller;

import com.spring_boot.security_example.entity.User;
import com.spring_boot.security_example.entity.VerificationToken;
import com.spring_boot.security_example.event.RegistrationCompleteEvent;
import com.spring_boot.security_example.model.PasswordModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.spring_boot.security_example.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import com.spring_boot.security_example.service.UserService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
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
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Send";
    }

    @PostMapping("/restPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";

        if(user != null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){
        String result = userService.validatePassowrdResetToken(token);

        if(!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }
        Optional<User> optionalUser = userService.getUserByPasswordResetToken(token);
        if(optionalUser.isPresent()){
            userService.changePassword(optionalUser.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully";
        }else{
            return "Invalid Token";
        }
    }

    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        // Sending Mail to user
        String url = applicationUrl
                + "/savePassword?token="
                + token;

        /* Send Verification Email() */
        log.info("Click the link to reset your password: {}", url);
        return url;
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        // Sending Mail to user
        String url = applicationUrl
                + "/verifyRegistration?token="
                + verificationToken.getToken();

        /* Send Verification Email() */
        log.info("Click the link to verify your account: {}", url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return  "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
