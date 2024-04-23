package com.example.Event.Driven.architecture.Command.api.Events;

import com.example.Event.Driven.architecture.Command.api.Data.ProductEntity;
import com.example.Event.Driven.architecture.Command.api.Data.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandler {
    @Autowired
    private final ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(productCreatedEvent,product);
        productRepository.save(product);


    }
}
