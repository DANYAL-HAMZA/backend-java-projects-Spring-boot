package com.example.BankApplication.service;

import com.example.BankApplication.dto.TransactionDto;
import com.example.BankApplication.entity.Transaction;
import com.example.BankApplication.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void saveTransaction(TransactionDto transaction) {

        Transaction newTransaction = Transaction.builder()
                .transactionType(transaction.getTransactionType())
                .accountNumber(transaction.getAccountNumber())
                .amount(transaction.getAmount())
                .build();

        transactionRepository.save(newTransaction);
    }
}
