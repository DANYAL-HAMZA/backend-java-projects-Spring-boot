package com.example.payment.service.Projection;

import com.example.payment.service.Module.CardDetails;
import com.example.payment.service.Module.User;
import com.example.payment.service.Querry.GetUserPaymentDetailsQuerry;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {
    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuerry getUserPaymentDetailsQuerry){
        CardDetails cardDetails = CardDetails.builder()
                .cardNumber("123456789")
                .cvv(111)
                .name("Danyal")
                .validUntilMonth(01)
                .validUntilYear(2022)
                .build();
        return User.builder()
                .userId(getUserPaymentDetailsQuerry.getUserId())
                .firstName("Hamza")
                .lastName("Danyal")
                .cardDetails(cardDetails)
                .build();
    }
}
