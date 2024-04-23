package com.example.payment.service.Command.api.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Shipment {
    @Id
    private String shipmentId;
    private String shipmentStatus;
    private String orderId;
}
