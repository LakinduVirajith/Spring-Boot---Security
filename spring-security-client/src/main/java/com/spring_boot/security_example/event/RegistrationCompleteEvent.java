package com.spring_boot.security_example.event;

import com.spring_boot.security_example.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final User user;
    private final String applicationURL;

    public RegistrationCompleteEvent(User user, String applicationURL){
        super(user);
        this.user = user;
        this.applicationURL = applicationURL;
    }
}
