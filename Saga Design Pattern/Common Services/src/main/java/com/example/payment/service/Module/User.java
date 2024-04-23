package com.example.payment.service.Module;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private CardDetails cardDetails;
//The reason why user is not stored in database is, the user details are collected
// when they are registering to use the service. So we are assuming that we already have
// the user details in database. That is why we directly wrote the query to retrieve the user payment
// details which was handled in the user service by the projection
// If we want to create a user instance in database, we can create an API to  register user following
// the CQRS and SAGA design patterns.
// the user already contains the card details, so the validate payment command must contain a
//  card details property, so it can access it directly from the user
//   the card details can be a class on its own,
//
//   hence we can create a separate class for it and embed it into the user class
}
