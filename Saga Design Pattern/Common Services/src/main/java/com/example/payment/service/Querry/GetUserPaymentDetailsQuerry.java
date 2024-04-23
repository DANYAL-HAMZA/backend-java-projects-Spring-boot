package com.example.payment.service.Querry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPaymentDetailsQuerry {
    private String userId;
    //all queries must access the database to retrieve user payment information,
    // so this class must be utilized in the user service controller where an api is created to retrieve
    //the user payment info, where this class is sent to the query gateway.
}
