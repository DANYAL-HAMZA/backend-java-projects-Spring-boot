package com.exampleDanyalBank.demo.controller;

import com.exampleDanyalBank.demo.entity.Transaction;
import com.exampleDanyalBank.demo.service.BankStatement;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.*;
@RestController
@AllArgsConstructor
@RequestMapping("/bankStatement")
public class TransactionController {
    private BankStatement bankStatement;
    @GetMapping("/")
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate, @RequestParam String endDate) throws DocumentException, FileNotFoundException {
        return bankStatement.generateStatement(accountNumber, startDate, endDate);

    }
}
