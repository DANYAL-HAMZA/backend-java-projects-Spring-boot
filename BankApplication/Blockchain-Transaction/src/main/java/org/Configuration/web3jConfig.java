package org.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class web3jConfig {
    @Bean
    public Web3j web3j(){
        return Web3j.build(new HttpService(providerUrl));

    }
    @Value("${web3j.provider.url}")
    private String providerUrl;

    @Value("${wallet.private.key}")
    private  String privateKey;
    @Bean
    public Credentials credentials(){
        return Credentials.create(privateKey);
    }
}
