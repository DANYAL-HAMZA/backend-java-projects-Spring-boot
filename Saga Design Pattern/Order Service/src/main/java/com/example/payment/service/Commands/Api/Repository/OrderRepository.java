package com.example.payment.service.Commands.Api.Repository;

import com.example.payment.service.Commands.Api.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,String> {
}
