package com.example.ShoppingCartApplication.ShoppingCart.repository;

import com.dailycodework.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}