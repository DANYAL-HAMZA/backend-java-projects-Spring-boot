package com.example.order.service.Config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
//import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ObservationRegistoryConfig {
@Bean
    public ObservationRegistry observationRegistry(){
        return new ObservationRegistry() {
            @Override
            public Observation getCurrentObservation() {
                return null;
            }

            @Override
            public Observation.Scope getCurrentObservationScope() {
                return null;
            }

            @Override
            public void setCurrentObservationScope(Observation.Scope scope) {

            }

            @Override
            public ObservationConfig observationConfig() {
                return new ObservationConfig();
            }
        };
    }
   // @Bean
   // public WebClient.Builder observationConfig(){
    //    return WebClient.builder().filter(logRequest());

   // }
   // private ExchangeFilterFunction logRequest(){
    //    return ((request, next) -> {
      //      System.out.println("request:" + request.method() + "" + request.url());
        //    return next.exchange(request);
       // });
    //}
}
