package com.example.BankApplication.controller;

import com.example.BankApplication.dto.*;
import com.example.BankApplication.service.BlockchainTransactionService;
import com.example.BankApplication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/users")

@Tag(name = "User Account Management Api's")
public class UserController {
@Autowired
    private final UserService userService;
@Autowired
private final BlockchainTransactionService blockchainTransactionService;

    public UserController(UserService userService, BlockchainTransactionService blockchainTransactionService){
        this.userService = userService;
        this.blockchainTransactionService = blockchainTransactionService;
    }
@Operation(
        summary = "Create New Account",
        description = "creating a new user and assigning an account number"
)
@ApiResponse(
        responseCode = "201",
        description = "HTTP Status 201 CREATED"
)
    @PostMapping
    public Response registerUser(@RequestBody UserRequest userRequest){
        return userService.registerUser(userRequest);
    }

    @Operation(
            summary = "Fetch all account holders",
            description = "Returns all users who have an account with the bank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping
    public List<Response> allRegisteredUsers(){
        return userService.allUsers();
    }
    @Operation(
            summary = "Fetch User",
            description = "Given an account id, returns the user information"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/{userId}")

    public Response fetchUser(@PathVariable(name = "userId") Long userId) throws Exception {
        return userService.fetchUser(userId);
    }
    @Operation(
            summary = "Balance Enquiry",
            description = "Given an account number, checks the balance of a user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/balEnquiry")
    public Response balanceEnquiry(@RequestParam(name = "accountNumber") String accountNumber){
        return userService.balanceEnquiry(accountNumber);
    }
    @Operation(
            summary = "Name Enquiry",
            description = "Given an account number, returns the user information"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping("/nameEnquiry")
    public Response nameEnquiry(@RequestParam(name = "accountNumber") String accountNumber){
        return userService.nameEnquiry(accountNumber);
    }

    @Operation(
            summary = "update",
            description = "Updates a user by verifying account ownership by account number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PostMapping("/update")

    public Response updateUser(@RequestBody UserRequest userRequest){
        return userService.updateUser(userRequest);
    }
    @Operation(
            summary = "debit",
            description = "Debits an amount from a user's "
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PostMapping("/debit")
    public Response debit(@RequestBody TransactionRequest transactionRequest){
        return userService.debit(transactionRequest);
    }
    @Operation(
            summary = "credit",
            description = "Credits a user's account with an amount"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PostMapping("/credit")
    public Response credit(@RequestBody TransactionRequest transactionRequest){
        return userService.credit(transactionRequest);
    }
    @Operation(
            summary = "transfer",
            description = "Transfers an amount from a sending account to a receiving account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @PostMapping("/transfer")
    public Response transfer(@RequestBody TransferRequest transferRequest){
        return userService.transfer(transferRequest);
    }

    @Operation(
            summary = "mint token ",
            description = "Mints a token that represents an amount to the blockchain wallet address of the user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
@PostMapping("/mintDsc")
    public Response mintTokenToAddress(@RequestBody BlockchainTransactionRequest blockchainTransactionRequest) throws Exception {
        return blockchainTransactionService.mintTokenToAddress(blockchainTransactionRequest);

}
    @Operation(
            summary = "convert token to cash",
            description = "Converts a specified amount of the token to be converted back into cash"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
@PostMapping("/convertTokenToCash")
    public Response convertTokenToCash(@RequestBody BlockchainTransactionRequest blockchainTransactionRequest) throws Exception {
        return blockchainTransactionService.convertTokenToCash(blockchainTransactionRequest);

}
    @Operation(
            summary = "transfer token to non account holder",
            description = "Transfers a bank token amount from an account holder to a non account holder"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
@PostMapping("/transferTokenToNonAccountHolder")
    public Response transferTokenToNonAccountHolder(@RequestBody BlockchainTransactionRequest blockchainTransactionRequest) throws Exception {
        return blockchainTransactionService.transferTokensToNonAccountHolder(blockchainTransactionRequest);
}

    @Operation(
            summary = "transfer bank tokens to account holder",
            description = "Transfers a bank token amount to an account holder"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
@PostMapping("/transferTokenToAccountHolder")
    public Response transferTokenToAccountHolder(@RequestBody BlockchainTransactionRequest blockchainTransactionRequest) throws Exception {
        return blockchainTransactionService.transferTokensToAccountHolder(blockchainTransactionRequest);

}
    @Operation(
            summary = "log in",
            description = "Logs users into their respective accounts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
@PostMapping("/login")
    public Response login(@RequestBody LogInDto logInDto){
        return userService.login(logInDto);
}

}






