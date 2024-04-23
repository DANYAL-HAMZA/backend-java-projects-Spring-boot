package com.example.Twilio.demo;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender{
    private final TwilioConfiguration twilioConfiguration;
    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsSender.class);
@Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
    if(isPhoneNumberValid(smsRequest.getPhoneNumber())) {
      MessageCreator creator =  Message.creator(new PhoneNumber(smsRequest.getPhoneNumber()), new PhoneNumber(twilioConfiguration.getTrialNumber())
                , smsRequest.getMessage());
      creator.create();
      logger.info("sms sent {}" + smsRequest);
    }
    else {
        throw new IllegalArgumentException("phone number" + smsRequest.getPhoneNumber() + "is not valid");
    }
}
//here we define a method to validate the phone number, you can use googles library to validate the phone number. But we
    //will pass the value to true for now.
    private boolean isPhoneNumberValid(String phoneNumber) {
    return true;
    }
    }
