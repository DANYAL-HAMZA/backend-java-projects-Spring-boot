package com.example.payment.service.Command.api.Event;

import com.example.payment.service.Command.api.Entity.Shipment;
import com.example.payment.service.Command.api.Repository.ShipmentRepository;
import com.example.payment.service.Events.OrderShippedEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventHandler {
    private final ShipmentRepository shipmentRepository;

    public ShipmentEventHandler(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void on(OrderShippedEvent orderShippedEvent){
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(orderShippedEvent,shipment);
        shipmentRepository.save(shipment);

    }
}
