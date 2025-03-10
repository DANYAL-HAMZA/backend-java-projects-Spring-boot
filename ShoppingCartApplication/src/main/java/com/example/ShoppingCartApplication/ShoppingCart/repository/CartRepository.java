package com.example.ShoppingCartApplication.ShoppingCart.repository;

import com.dailycodework.dreamshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
