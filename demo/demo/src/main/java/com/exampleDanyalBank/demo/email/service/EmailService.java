package com.exampleDanyalBank.demo.email.service;

import com.exampleDanyalBank.demo.email.dto.EmailDetails;

public interface EmailService {
    String sendSimpleEmail(EmailDetails emailDetails);
    String sendEmailWithAttachment(EmailDetails emailDetails);

}
