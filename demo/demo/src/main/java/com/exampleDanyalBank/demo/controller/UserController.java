package com.exampleDanyalBank.demo.controller;

import com.exampleDanyalBank.demo.dto.*;
import com.exampleDanyalBank.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")

@Tag(name = "User Account Management Apis")
public class UserController {
@Autowired
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
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
            summary = "log in",
            description = "Logs users into their respective accounts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
@PostMapping("/login")
    public Response login(@RequestBody LogInDto logInDto){
        return userService.logIn(logInDto);

}




}
