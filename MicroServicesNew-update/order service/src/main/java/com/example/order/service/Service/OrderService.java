package com.example.order.service.Service;

import com.example.order.service.Module.InventoryResponse;
import com.example.order.service.Module.Order;
import com.example.order.service.Module.OrderLineItems;
import com.example.order.service.Module.OrderRequest;
import com.example.order.service.Repository.OrderRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional

public class OrderService {
    //@Autowired
    //private final WebClient.Builder webClientBuilder;
    @Autowired
    private final ObservationRegistry observationRegistory;
    private final OrderRepository orderRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public OrderService( RestTemplate restTemplate, ObservationRegistry observationRegistory, OrderRepository orderRepository) {
        //this.webClientBuilder = webClientBuilder;
        this.restTemplate = restTemplate;
        this.observationRegistory = observationRegistory;
        this.orderRepository = orderRepository;
    }

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsList().stream()
                .map(OrderLineItems -> mapToDdo(OrderLineItems)).toList();
        order.setOrderLineItemsList(orderLineItemsList);
        List<String> codes = order.getOrderLineItemsList().stream().map(orderLineItems1 ->
                orderLineItems1.getCode()).toList();
        //Use the webclient.get() method and pass the url of the inventory service in order to notify the inventory
        //service anytime an order is made
        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistory);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");

        //try {
           // return inventoryServiceObservation.observe(() -> {
                InventoryResponse[] inventoryResponseArray = restTemplate.
                        postForObject("http://localhost:5002/inventory",codes,InventoryResponse[].class);

               // InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                      //  .uri("http://localhost:5002/inventory", uriBuilder ->
                        //        uriBuilder.queryParam("code", codes).build())
                     //   .retrieve()
                     //   .bodyToMono(InventoryResponse[].class)
                     //   .block();
                boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                        .allMatch(InventoryResponse::isInStock);
                if (allProductsInStock) {
                    orderRepository.save(order);
                    return "order placed";
                } else {
                    throw new IllegalArgumentException("product is not in stock");
                }
           // });
        //} catch (WebClientResponseException e) {
            //System.out.println(e.getResponseBodyAsString());
        }
        //return "order placed";
    //}

    private OrderLineItems mapToDdo(OrderLineItems orderLineItems) {
        OrderLineItems orderLineItems1 = new OrderLineItems();
        orderLineItems1.setPrice(orderLineItems1.getPrice());
        orderLineItems1.setCode(orderLineItems1.getCode());
        orderLineItems1.setQuantity(orderLineItems1.getQuantity());
        return orderLineItems1;

    }
}