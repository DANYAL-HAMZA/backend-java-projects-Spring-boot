package com.example.Event.Driven.architecture.Command.api.Aggregate;

import com.example.Event.Driven.architecture.Command.api.Command.CreateProductCommand;
import com.example.Event.Driven.architecture.Command.api.Events.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

@AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    //aggregating or command handling

    @CommandHandler
    @Autowired
    public ProductAggregate(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }
    public ProductAggregate(){
    }

    //publishing update to  the event store
    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
            this.name = productCreatedEvent.getName();
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

}
