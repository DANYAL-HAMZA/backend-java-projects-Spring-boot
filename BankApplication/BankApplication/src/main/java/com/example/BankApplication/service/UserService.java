package com.example.BankApplication.service;

import com.example.BankApplication.dto.*;

import java.util.List;

public interface UserService {
    Response registerUser(UserRequest userRequest);



    List<Response> allUsers();
    Response fetchUser(Long id);
    Response balanceEnquiry(String accountNumber);
    Response nameEnquiry(String accountNumber);
    Response credit(TransactionRequest transactionRequest);
    Response debit(TransactionRequest transactionRequest);
Response login(LogInDto logInDto);
    Response transfer(TransferRequest request);
    Response updateUser(UserRequest userRequest);
}
