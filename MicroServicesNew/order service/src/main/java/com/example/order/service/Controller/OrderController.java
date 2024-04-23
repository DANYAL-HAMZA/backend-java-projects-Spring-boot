package com.example.order.service.Controller;

import com.example.order.service.Module.OrderRequest;
import com.example.order.service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController

public class OrderController {
    private final OrderService orderService;
@Autowired
    public OrderController(OrderService orderService) {

    this.orderService = orderService;

}

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
    orderService.placeOrder(orderRequest);
        return "ORDER MADE SUCESSFULLY";

    }
}
