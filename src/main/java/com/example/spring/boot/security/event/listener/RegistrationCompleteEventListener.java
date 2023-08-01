package com.example.spring.boot.security.event.listener;

import com.example.spring.boot.security.entity.User;
import com.example.spring.boot.security.event.RegistrationCompleteEvent;
import com.example.spring.boot.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;

@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private UserService userService;

    public RegistrationCompleteEventListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        /* Create the Verification Token for the User with Link */
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(user, token);

        /* Send Mail to user */
        String url = event.getApplicationUrl()
                + "verifyRegistration?token="
                + token;

        /* Send Verification Email() */
        log.info("Click the link to verify your account: {}", url);
    }
}
