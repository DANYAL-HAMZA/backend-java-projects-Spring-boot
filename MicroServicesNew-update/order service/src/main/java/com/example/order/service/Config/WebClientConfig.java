package com.example.order.service.Config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
//import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
  //  @Bean
  //  @LoadBalanced
  //  public WebClient.Builder webClientBuilder() {
     //   return WebClient.builder();
@Bean
    public RestTemplate restTemplate(){

        return new RestTemplate();
    }
    }










//}
