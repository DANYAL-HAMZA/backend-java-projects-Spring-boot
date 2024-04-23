package com.example.payment.service.Projection.Controller;

import com.example.payment.service.Module.User;
import com.example.payment.service.Querry.GetUserPaymentDetailsQuerry;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {
    private transient QueryGateway queryGateway;
@Autowired
    public UserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
    //all commands and queries must be pushed to the query gateway. This is done in the controller class
    //hence the query (GetUserPaymentDetailsQuery) defined in the common services is
    // handled and pushed to the query gateway here in the user controller.

    @GetMapping("/{userId}")
    public User getUserPaymentDetails(@PathVariable String userId){
        GetUserPaymentDetailsQuerry getUserPaymentDetailsQuerry = new GetUserPaymentDetailsQuerry();
        User user = queryGateway.query(getUserPaymentDetailsQuerry,
                        ResponseTypes.instanceOf(User.class)).join();
        return user;
    }
}
