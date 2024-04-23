package com.example.payment.service.Command.api.Repository;

import com.example.payment.service.Command.api.Entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,String> {
}
