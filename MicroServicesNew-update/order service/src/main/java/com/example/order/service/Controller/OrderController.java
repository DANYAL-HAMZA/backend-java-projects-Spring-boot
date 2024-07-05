
package com.example.order.service.Controller;

import com.example.order.service.Module.OrderRequest;
import com.example.order.service.Service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController

public class OrderController {
    private final OrderService orderService;
@Autowired
    public OrderController(OrderService orderService) {

    this.orderService = orderService;

}

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    /*Since the timelimiter annotation makes an asynchronous call, we must return a completable faeture*/
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
    orderService.placeOrder(orderRequest);
        return CompletableFuture.supplyAsync(()->"ORDER MADE SUCESSFULLY");

    }
    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
    return CompletableFuture.supplyAsync(()-> "Something went wrong try again sometime");
    }
}
