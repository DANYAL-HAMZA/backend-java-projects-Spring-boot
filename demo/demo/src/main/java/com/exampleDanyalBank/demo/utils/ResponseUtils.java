package com.exampleDanyalBank.demo.utils;

import java.math.BigInteger;
import java.util.Random;

public class ResponseUtils {

    //handling custom responses
    public static final String USER_EXISTS_CODE = "001";
    public static final String USER_EXISTS_MESSAGE = "User with provided email already exists!";
    public static final String SUCCESS = "002";
    public static final String USER_REGISTERED_SUCCESS = "User has been successfully registered!";
    public static final String SUCCESS_MESSAGE = "Successfully Done!";
    public static final String USER_NOT_FOUND_MESSAGE = "This User Doesn't Exist!";
    public static final String USER_NOT_FOUND_CODE = "003";
    public static final String SUCCESSFUL_TRANSACTION = "004";
    public static final String ACCOUNT_CREDITED = "Account has been credited";

    public static final int LENGTH_OF_ACCOUNT_NUMBER = 10;
    public static final String ACCOUNT_NOT_EXIST_CODE = "005";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "Account does not exist";
    public static final String INSUFFICIENT_BALANCE_MESSAGE  = "Insufficient balance";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";

    public static final String ACCOUNT_DEBITED_MESSAGE = "Account debited successfully";
    public static final String ACCOUNT_DEBITED_SUCCESS_CODE = "007";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "transfer was successfully";
    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
    public static final String USER_UPDATED_SUCCESSFULLY = " User has been successfully updated";







    public static String generateAccountNumber(int len){
        String accountNumber = "";
        int x;
        char[] stringChars = new char[len];

        for (int i = 0; i < len; i++) //4
        {
            Random random = new Random();
            x = random.nextInt(9);

            stringChars[i] = Integer.toString(x).toCharArray()[0];
        }


        accountNumber = new String(stringChars);
        return accountNumber.trim();

    }





}
