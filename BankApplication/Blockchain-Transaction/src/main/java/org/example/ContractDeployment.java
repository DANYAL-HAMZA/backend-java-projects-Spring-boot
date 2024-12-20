package org.example;

import org.web3j.crypto.Credentials;
import org.web3j.model.Transactions;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

/**
 * Hello world!
 *
 */
public class ContractDeployment{
         String DSC_address = "0xBaceA1DA6fCa6Bb2BB7DcF89Fb071F8c7D203bC7";


    public static void main( String[] args ) throws Exception {
         String DSC_address = "0xBaceA1DA6fCa6Bb2BB7DcF89Fb071F8c7D203bC7";

        Web3j web3j = Web3j.build(new HttpService(""));

        Credentials credentials = Credentials.create("f28436a34d7a0ad0d6994851bde71266ff4010f0a6e658f51ba2d9152d285272");

        Transactions transactions = Transactions.deploy(web3j,credentials,new DefaultGasProvider(),DSC_address).send();
        String contractAddress = transactions.getContractAddress();
        System.out.println( "contract address" + contractAddress);
    }
}
