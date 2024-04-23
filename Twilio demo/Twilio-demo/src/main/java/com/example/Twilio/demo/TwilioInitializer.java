package com.example.Twilio.demo;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;



@Configuration
public class TwilioInitializer {
    private final TwilioConfiguration twilioConfiguration;
    private static Logger logger = LoggerFactory.getLogger(TwilioInitializer.class);
@Autowired
    public TwilioInitializer(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
    logger.info("twilio initialized ... with accountSid ()", twilioConfiguration.getAccountSid());
    }
}
