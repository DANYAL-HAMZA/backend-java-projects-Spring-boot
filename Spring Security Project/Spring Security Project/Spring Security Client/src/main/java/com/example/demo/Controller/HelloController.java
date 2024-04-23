package com.example.demo.Controller;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.*;

@RestController
public class HelloController {

    private WebClient webClient;
    @GetMapping("/api/hello")
    public String hello() {
        return "HELLO USER, NICE TO MEET YOU";
    }
    @GetMapping("/api/users")
    public String[] getUsers(@RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
                                 OAuth2AuthorizedClient client) {
        return webClient.get()
                .uri("http://127.0.0.1:8090/api/users")
                .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(
                        client))
                .retrieve()
                .bodyToMono(String[].class)
                .block();
    }

    }


