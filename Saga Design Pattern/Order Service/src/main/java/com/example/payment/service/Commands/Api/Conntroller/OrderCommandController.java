package com.example.payment.service.Commands.Api.Conntroller;

import com.example.payment.service.Commands.Api.Command.CreateOrderCommand;
import com.example.payment.service.Commands.Api.Modules.OrderRestModule;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderCommandController {
    private final CommandGateway commandGateway;

    public OrderCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
@PostMapping
    public String createOrder(@RequestBody OrderRestModule orderRestModule){
        String orderId = UUID.randomUUID().toString();
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .addressId(orderRestModule.getAddressId())
                .productId(orderRestModule.getProductId())
                .quantity(orderRestModule.getQuantity())
                .orderStatus("created")
                .build();
        commandGateway.sendAndWait(createOrderCommand);
return "Order Created";

    }
}
