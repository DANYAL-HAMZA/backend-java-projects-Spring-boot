package com.exampleDanyalBank.demo.service;

import com.exampleDanyalBank.demo.dto.*;

import java.util.List;

public interface UserService {
    Response registerUser(UserRequest userRequest);


Response logIn(LogInDto logInDto);
    List<Response> allUsers();

    Response fetchUser(Long id);
    Response balanceEnquiry(String accountNumber);
    Response nameEnquiry(String accountNumber);
    Response credit(TransactionRequest transactionRequest);
    Response debit(TransactionRequest transactionRequest);
    Response updateUser(UserRequest userRequest);

    Response transfer(TransferRequest request);
}
