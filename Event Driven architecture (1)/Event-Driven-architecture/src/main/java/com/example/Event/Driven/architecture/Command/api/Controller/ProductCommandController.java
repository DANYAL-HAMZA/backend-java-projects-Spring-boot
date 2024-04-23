package com.example.Event.Driven.architecture.Command.api.Controller;

import com.example.Event.Driven.architecture.Command.api.Command.CreateProductCommand;
import com.example.Event.Driven.architecture.Command.api.Model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductCommandController {

   private final CommandGateway commandGateway;

    public ProductCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }


    @PostMapping
    public String addProduct(@RequestBody ProductRestModel productRestModel) {
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .quantity(productRestModel.getQuantity())
                .build();
        String result = commandGateway.sendAndWait(createProductCommand);
        return result;
    }

}
