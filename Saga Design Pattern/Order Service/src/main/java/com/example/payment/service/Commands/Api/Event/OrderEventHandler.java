package com.example.payment.service.Commands.Api.Event;

import com.example.payment.service.Commands.Api.Entity.Order;
import com.example.payment.service.Commands.Api.Repository.OrderRepository;
import com.example.payment.service.Events.OrderCancelledEvent;
import com.example.payment.service.Events.OrderCompletedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderEventHandler {
    private final OrderRepository orderRepository;

    public OrderEventHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
        Order order = new Order();
        BeanUtils.copyProperties(orderCreatedEvent,order);
        orderRepository.save(order);
    }
    @EventHandler
    public void on(OrderCompletedEvent orderCompletedEvent){
        Order order = orderRepository.findById(orderCompletedEvent.getOrderId()).get();
        order.setOrderStatus(orderCompletedEvent.getOrderStatus());
        orderRepository.save(order);

    }
    @EventHandler
    public void on(OrderCancelledEvent orderCancelledEvent){
        Order order = orderRepository.findById(orderCancelledEvent.getOrderId()).get();
        order.setOrderStatus(orderCancelledEvent
                .getOrderStatus());
        orderRepository.save(order);
    }
}
