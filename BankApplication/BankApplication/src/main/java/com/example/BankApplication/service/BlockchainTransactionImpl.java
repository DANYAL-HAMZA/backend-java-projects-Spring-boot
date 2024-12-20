package com.example.BankApplication.service;

import com.example.BankApplication.dto.BlockchainTransactionRequest;
import com.example.BankApplication.dto.Response;
import com.example.BankApplication.dto.TokenData;
import com.example.BankApplication.email.dto.EmailDetails;
import com.example.BankApplication.email.service.EmailService;
import com.example.BankApplication.entity.BlockchainTransaction;
import com.example.BankApplication.entity.User;
import com.example.BankApplication.repository.BlockchainTransactionRepository;
import com.example.BankApplication.repository.UserRepository;
import com.example.BankApplication.utils.ResponseUtils;
import org.springframework.stereotype.Service;
import org.web3j.model.Transactions;

import java.math.BigDecimal;

@Service
public class BlockchainTransactionImpl implements BlockchainTransactionService {
    private final BlockchainTransactionRepository blockchainTransactionRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final Transactions blockchainTransactions;
    private final org.Service.Service blockchainService;



    public BlockchainTransactionImpl(BlockchainTransactionRepository blockchainTransactionRepository, UserRepository userRepository, EmailService emailService, Transactions blockchainTransactions, org.Service.Service blockchainService) {
        this.blockchainTransactionRepository = blockchainTransactionRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.blockchainTransactions = blockchainTransactions;
        this.blockchainService = blockchainService;
    }

    @Override
    public Response mintTokenToAddress(BlockchainTransactionRequest blockchainTransactionRequest) throws Exception {
        boolean isSourceAccountExists = userRepository.existsByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());

        if (!isSourceAccountExists ) {
            return Response.builder()
                    .responseCode(ResponseUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }
        User userToMint = userRepository.findByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());
        String walletAddress = userToMint.getWalletAddress();
        BigDecimal tokenAmountToMint = ResponseUtils.calculateTokenPerCashSpecified(blockchainTransactionRequest.getAmount());

        if(ResponseUtils.isValidAddress(walletAddress)) {
            Transactions transactions = blockchainService.loadContract();
            transactions.mintDsc(walletAddress, tokenAmountToMint);


                userToMint.setAccountBalance(userToMint.getAccountBalance().subtract(blockchainTransactionRequest.getAmount()));
                userToMint.setTokenBalance(userToMint.getTokenBalance().add(tokenAmountToMint));
                userRepository.save(userToMint);

        }
        else
        {
            throw new IllegalArgumentException("Invalid Wallet Address");
        }
        BlockchainTransaction transaction = BlockchainTransaction.builder()
                .amount(blockchainTransactionRequest.getAmount())
                .tokenAmount(tokenAmountToMint)
                .transactionTitle("MINT")
                .senderAccountNumber(blockchainTransactionRequest.getSenderAccountNumber())
                .build();
        blockchainTransactionRepository.save(transaction);

        EmailDetails tokenMintAlert = EmailDetails.builder()
                .subject("DANYAL BANK BLOCKCHAIN TOKEN MINT ALERT")
                .recipient(userToMint.getEmail())
                .messageBody("A token amount of " + tokenAmountToMint + "which isequivalent to " + blockchainTransactionRequest.getAmount() +
                        "has been minted to your wallet address")
                .build();
        emailService.sendSimpleEmail(tokenMintAlert);

        return Response.builder()
                .responseCode(ResponseUtils.TOKEN_MINT_SUCCESSFUL_CODE)
                .responseMessage(ResponseUtils.TOKEN_MINT_SUCCESSFUL_MESSAGE)
                .tokenData(TokenData.builder()
                        .tokenBalance(userToMint.getTokenBalance())
                        .accountName(userToMint.getFirstName() + userToMint.getLastName())
                        .accountNumber(userToMint.getAccountNumber())
                        .accountBalance(userToMint.getAccountBalance())
                        .build())
                .build();

    }

    @Override
    public Response transferTokensToNonAccountHolder(BlockchainTransactionRequest blockchainTransactionRequest) throws Exception {

        boolean isSourceAccountExists = userRepository.existsByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());

        if (!isSourceAccountExists ) {
            return Response.builder()
                    .responseCode(ResponseUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }

        User tokenSender = userRepository.findByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());
        String senderAddress = tokenSender.getWalletAddress();
        String receiverAddress = blockchainTransactionRequest.getNonAccountHolderWalletAddress();
        BigDecimal amountToTransfer = ResponseUtils.calculateTokenPerCashSpecified(blockchainTransactionRequest.getAmount());
        //This is where we send the token here and also check if token transfer was successful before updating variables

        if(ResponseUtils.isValidAddress(senderAddress) && ResponseUtils.isValidAddress(receiverAddress)) {
            Transactions transactions = blockchainService.loadContract();
            transactions.transferFromUser1ToUser2(amountToTransfer,senderAddress,receiverAddress);

            tokenSender.setTokenBalance(tokenSender.getTokenBalance().subtract(amountToTransfer));
            userRepository.save(tokenSender);

        }
        else
        {
            throw new IllegalArgumentException("Invalid Wallet Address");
        }

        BlockchainTransaction transaction = BlockchainTransaction.builder()
                .isBankAccountHolder(false)
                .amount(amountToTransfer)
                .transactionTitle("TOKEN TRANSFER TO A NON ACCOUNT HOLDER")
                .senderAccountNumber(blockchainTransactionRequest.getSenderAccountNumber())
                .receiverWalletAddress(blockchainTransactionRequest.getNonAccountHolderWalletAddress())
                .build();
        blockchainTransactionRepository.save(transaction);

        EmailDetails tokenDebitAlert = EmailDetails.builder()
                .subject("DANYAL BANK BLOCKCHAIN TOKEN DEBIT ALERT")
                .recipient(tokenSender.getEmail())
                .messageBody("A token amount equivalent to " + blockchainTransactionRequest.getAmount() +
                        "has been transferred from your wallet to"+ blockchainTransactionRequest.getNonAccountHolderWalletAddress())
                .build();
        emailService.sendSimpleEmail(tokenDebitAlert);

        EmailDetails tokenCreditAlert = EmailDetails.builder()
                .subject("DANYAL BANK BLOCKCHAIN TOKEN CREDIT ALERT")
                .recipient(blockchainTransactionRequest.getNonAccountHolderEmail())
                .messageBody("A token amount of" + amountToTransfer +
                        "has been transferred from" + tokenSender.getLastName() + tokenSender.getFirstName() +
                        " to your blockchain wallet address " + blockchainTransactionRequest.getNonAccountHolderWalletAddress()
                )
                .build();
        emailService.sendSimpleEmail(tokenCreditAlert);

        return Response.builder()
                .responseCode(ResponseUtils.TOKEN_TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(ResponseUtils.TOKEN_TRANSFER_SUCCESSFUL_MESSAGE)
                .tokenData(TokenData.builder()
                        .tokenBalance(tokenSender.getTokenBalance())
                        .accountName(tokenSender.getFirstName() + tokenSender.getLastName())
                        .accountNumber(tokenSender.getAccountNumber())
                        .accountBalance(tokenSender.getAccountBalance())
                        .build())
                .build();

    }

    @Override
    public Response transferTokensToAccountHolder(BlockchainTransactionRequest blockchainTransactionRequest) throws Exception {
        boolean isSourceAccountExists = userRepository.existsByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());
        boolean isDestinationAccountExists = userRepository.existsByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());

        if (!isSourceAccountExists || !isDestinationAccountExists) {
            return Response.builder()
                    .responseCode(ResponseUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }

        User tokenSender = userRepository.findByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());
        User tokenReceiver = userRepository.findByAccountNumber(blockchainTransactionRequest.getReceiverAccountNumber());

        String senderAddress = tokenSender.getWalletAddress();
        String receiverAddress = tokenReceiver.getWalletAddress();
        BigDecimal amountToTransfer = ResponseUtils.calculateTokenPerCashSpecified(blockchainTransactionRequest.getAmount());
        //check if token transfer was successful before updating variables
        //we do not update account balance here because the account balance only decreases when tokens
        //are minted to a user's address(ie converting cash into token) and increases when tokens
        // are burnt from a user's address(ie converting tokens back to cash)
        if(ResponseUtils.isValidAddress(senderAddress) && ResponseUtils.isValidAddress(receiverAddress)) {
            Transactions transactions = blockchainService.loadContract();
            transactions.transferFromUser1ToUser2(amountToTransfer, senderAddress, receiverAddress);

            tokenSender.setTokenBalance(tokenSender.getTokenBalance().subtract(amountToTransfer));
            tokenReceiver.setTokenBalance(tokenReceiver.getTokenBalance().add(amountToTransfer));
            userRepository.save(tokenSender);
            userRepository.save(tokenReceiver);

        }
        else {
            throw new IllegalArgumentException("Invalid Address");
        }

        BlockchainTransaction transaction = BlockchainTransaction.builder()
                .isBankAccountHolder(true)
                .amount(amountToTransfer)
                .transactionTitle("TOKEN TRANSFER TO AN ACCOUNT HOLDER")
                .senderAccountNumber(blockchainTransactionRequest.getSenderAccountNumber())
                .receiverAccountNumber(blockchainTransactionRequest.getReceiverAccountNumber())
                .build();
        blockchainTransactionRepository.save(transaction);

        EmailDetails tokenDebitAlert = EmailDetails.builder()
                .subject("DANYAL BANK BLOCKCHAIN TOKEN DEBIT ALERT")
                .recipient(tokenSender.getEmail())
                .messageBody("A token amount equivalent to " + blockchainTransactionRequest.getAmount() +
                        "has been transfered from your wallet to"+ blockchainTransactionRequest.getReceiverAccountNumber())
                .build();
        emailService.sendSimpleEmail(tokenDebitAlert);

        EmailDetails tokenCreditAlert = EmailDetails.builder()
                .subject("DANYAL BANK BLOCKCHAIN TOKEN CREDIT ALERT")
                .recipient(tokenReceiver.getEmail())
                .messageBody("A token amount of" + amountToTransfer +
                        "has been transferred from" + tokenSender.getLastName() + tokenSender.getFirstName() +
                        " to your blockchain wallet address associated with your bank account number"
                        + blockchainTransactionRequest.getReceiverAccountNumber())
                        .build();
        emailService.sendSimpleEmail(tokenCreditAlert);

        return Response.builder()
                .responseCode(ResponseUtils.TOKEN_TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(ResponseUtils.TOKEN_TRANSFER_SUCCESSFUL_MESSAGE)
                .tokenData(TokenData.builder()
                        .tokenBalance(tokenSender.getTokenBalance())
                        .accountName(tokenSender.getFirstName() + tokenSender.getLastName())
                        .accountNumber(tokenSender.getAccountNumber())
                        .accountBalance(tokenSender.getAccountBalance())
                        .build()
                )
                .build();
    }

    @Override
    public Response convertTokenToCash(BlockchainTransactionRequest blockchainTransactionRequest) throws Exception {
        boolean isSourceAccountExists = userRepository.existsByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());

        if (!isSourceAccountExists) {
            return Response.builder()
                    .responseCode(ResponseUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(ResponseUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .data(null)
                    .build();
        }
        User userToConvert = userRepository.findByAccountNumber(blockchainTransactionRequest.getSenderAccountNumber());
        String userWalletAddress = userToConvert.getWalletAddress();
        BigDecimal tokenAmountToBurn = ResponseUtils.calculateTokenPerCashSpecified(blockchainTransactionRequest.getAmount());
        //This is where we burn the token from the user's address
        //we check for a successful burn and update the respective variables before saving the user
        if(ResponseUtils.isValidAddress(userWalletAddress)) {
            Transactions transactions = blockchainService.loadContract();
            transactions.burnDsc(tokenAmountToBurn, userWalletAddress);

            userToConvert.setTokenBalance(userToConvert.getTokenBalance().subtract(tokenAmountToBurn));
            userToConvert.setAccountBalance(userToConvert.getAccountBalance().add(blockchainTransactionRequest.getAmount()));
            userRepository.save(userToConvert);
        }

        BlockchainTransaction transaction = BlockchainTransaction.builder()
                .isBankAccountHolder(true)
                .amount(blockchainTransactionRequest.getAmount())
                .tokenAmount(tokenAmountToBurn)
                .transactionTitle("TOKEN BURN")
                .senderAccountNumber(blockchainTransactionRequest.getSenderAccountNumber())
                .build();
        blockchainTransactionRepository.save(transaction);

        EmailDetails tokenBurnAlert = EmailDetails.builder()
                .subject("DANYAL BANK BLOCKCHAIN TOKEN BURN ALERT")
                .recipient(userToConvert.getEmail())
                .messageBody("A token amount of " + tokenAmountToBurn + " which is equivalent to " + blockchainTransactionRequest.getAmount() +
                        "has been burnt from your wallet ")
                .build();
        emailService.sendSimpleEmail(tokenBurnAlert);

        return Response.builder()
                .responseCode(ResponseUtils.TOKEN_BURN_SUCCESSFUL_CODE)
                .responseMessage(ResponseUtils.TOKEN_BURN_SUCCESSFUL_MESSAGE)
                .tokenData(TokenData.builder()
                        .tokenBalance(userToConvert.getTokenBalance())
                        .accountName(userToConvert.getFirstName() + userToConvert.getLastName())
                        .accountNumber(userToConvert.getAccountNumber())
                        .accountBalance(userToConvert.getAccountBalance())
                        .build())
                .build();
    }
}
