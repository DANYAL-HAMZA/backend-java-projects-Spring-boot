package org.Service;

import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.web3j.crypto.Credentials;
import org.web3j.model.Transactions;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;


@org.springframework.stereotype.Service


public class Service {
    private final Web3j web3j;
    private final Credentials credentials;
    private Transactions transactions;
    private String DSC_address = "0xBaceA1DA6fCa6Bb2BB7DcF89Fb071F8c7D203bC7";

    public Service(Web3j web3j, Credentials credentials, Transactions transactions) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.transactions = transactions;
    }
//
    public Transactions loadContract()throws Exception {
        String contractAddress = "";
        transactions = Transactions.load(contractAddress,web3j,credentials,new DefaultGasProvider());
        return transactions;

    }
}
