package com.example.Twilio.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final Service service;
@Autowired
    public Controller(Service service) {
        this.service = service;
    }
@PostMapping("/sendsms")
    public void sendSms(@Validated  @RequestBody SmsRequest smsRequest) {
        service.sendSms(smsRequest);
    }
}
