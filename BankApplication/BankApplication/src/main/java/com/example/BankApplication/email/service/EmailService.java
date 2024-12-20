package com.example.BankApplication.email.service;

import com.example.BankApplication.email.dto.EmailDetails;

public interface EmailService {
    String sendSimpleEmail(EmailDetails emailDetails);
    String sendEmailWithAttachment(EmailDetails emailDetails);

}
