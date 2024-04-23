package com.example.demo.Event.Listener;

import com.example.demo.Entity.User;
import com.example.demo.Event.RegisterationCompleteEvent;
import com.example.demo.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Slf4j
public class RegisterationCompleteEventListener implements ApplicationListener<RegisterationCompleteEvent> {

        //create verification token for user with link and attach it to the url so that when the user clicks they will be
        //redirected to the application.
    @Autowired
    private UserService service;
    @Override
    public void onApplicationEvent(RegisterationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.saveVerificationTokenForUser(token, user);
        //create link and send mail
        String url = event.getApplicationUrl() + "/verifyregisteration?token="+token;
        //use log slf4j to print the logger instead of sending verification email
        // to save time
        log.info("Click the link to verify your account:" + url);

    }
}
