package com.spring_boot.security_example.event.listener;

import com.spring_boot.security_example.entity.User;
import com.spring_boot.security_example.event.RegistrationCompleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import com.spring_boot.security_example.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create the Verification Token for the User with Link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token ,user);

        // Sending Mail to user
        String url = event.getApplicationURL()
                + "/verifyRegistration?token="
                + token;

        /* Send Verification Email() */
        log.info("Click the link to verify your account: {}", url);
    }
}
