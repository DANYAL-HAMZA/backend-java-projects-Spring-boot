package com.example.order.service.Service;

import com.example.order.service.Module.InventoryResponse;
import com.example.order.service.Module.Order;
import com.example.order.service.Module.OrderLineItems;
import com.example.order.service.Module.OrderRequest;
import com.example.order.service.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
@Transactional
public class OrderService {
    private final WebClient webClient;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(WebClient webClient, OrderRepository orderRepository) {
        this.webClient = webClient;
        this.orderRepository = orderRepository;
    }

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList =  orderRequest.getOrderLineItemsList().stream().map(OrderLineItems -> mapToDdo(OrderLineItems)).toList();
                order.setOrderLineItemsList(orderLineItemsList);
        List<String> codes = order.getOrderLineItemsList().stream().map(orderLineItems1 -> orderLineItems1.getCode()).toList();
        //Use the webclient.get() method and pass the url of the inventory service in order to notify the inventory
        //service anytime an order is made
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:5002/{code}", uriBuilder -> uriBuilder.queryParam("codes", codes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("product is not in stock");
        }


    }

    private OrderLineItems mapToDdo(OrderLineItems orderLineItems) {
        OrderLineItems orderLineItems1 = new OrderLineItems();
        orderLineItems1.setPrice(orderLineItems1.getPrice());
        orderLineItems1.setCode(orderLineItems1.getCode());
        orderLineItems1.setQuantity(orderLineItems1.getQuantity());
        return orderLineItems1;
    }
    }
