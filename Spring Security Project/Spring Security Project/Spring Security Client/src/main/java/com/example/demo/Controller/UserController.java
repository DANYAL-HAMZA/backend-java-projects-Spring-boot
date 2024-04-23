package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Event.RegisterationCompleteEvent;
import com.example.demo.Model.PasswordModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
@Slf4j
@RestController

public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/")
    public String home() {
        return "WELCOME";
    }

    @PostMapping("/register")
    //for creating url, u will need an http request, hence we inject it as a parameter in the method below
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = service.registerUser(userModel);
        eventPublisher.publishEvent(new RegisterationCompleteEvent(user, applicationUrl(request)));
        return "SUCCESS" + " ";
    }

    @GetMapping("/verifyregisteration")
    public String verifyRegisteration(@RequestParam("token") String token) {
        String result = service.validateVerificatiionToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "USER VERIFIED SUCCESSFULLY";
        }
        return "BAD USER";
    }

    private String applicationUrl(HttpServletRequest request) {

        return "http://" +
                request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @PostMapping("resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user = service.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if (user != null) {
            String token = UUID.randomUUID().toString();
            service.createPasswordResetTokenForUser(user, token);
            url = passwordResetTokenMail(user, applicationUrl(request), token);

        }
        return url;
    }
    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        String result = service.validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase("VALID")) {
            return "INVALID";
        }
        Optional<User> user = service.getUserByPasswordToken(token);
        if (user.isPresent()) {
            service.changePassword(user.get(), passwordModel.getNewPassword());
        } else {
            return "INVALID TOKEN";
        }
        return "PASSWORD SAVED SUCCESSFULLY";
    }
    @PostMapping("/changepassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {
        User user = service.findUserByEmail(passwordModel.getEmail());
        if(!service.checkIfValidOldPassword(user, passwordModel.getOldPassword())) {
            return "INVALID OLD PASSWORD";
        }
        service.changePassword(user, passwordModel.getNewPassword());
        return "PASSWORD CHANGED SUCCESSFULLY";
    }
    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url = applicationUrl + "/savepassword?token="+token;
        //use log slf4j to print the logger instead of sending verification email
        // to save time
        log.info("Click the link to reset your password:" + url);
return url;
    }
}

