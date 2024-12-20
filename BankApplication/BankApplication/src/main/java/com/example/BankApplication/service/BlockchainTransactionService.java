package com.example.BankApplication.service;

import com.example.BankApplication.dto.BlockchainTransactionRequest;
import com.example.BankApplication.dto.Response;

public interface BlockchainTransactionService {
    Response mintTokenToAddress(BlockchainTransactionRequest blockchainTransactionRequest) throws Exception;
    Response transferTokensToNonAccountHolder(BlockchainTransactionRequest blockchainTransactionRequest) throws Exception;

    Response transferTokensToAccountHolder(BlockchainTransactionRequest blockchainTransactionRequest) throws Exception;

    Response convertTokenToCash(BlockchainTransactionRequest blockchainTransactionRequest) throws Exception;

    }

