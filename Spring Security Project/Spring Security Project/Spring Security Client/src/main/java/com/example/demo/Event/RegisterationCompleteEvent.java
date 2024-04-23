package com.example.demo.Event;

import com.example.demo.Entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class RegisterationCompleteEvent extends ApplicationEvent {
    private final User user;
    private final String applicationUrl;

    public RegisterationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;

    }
}
