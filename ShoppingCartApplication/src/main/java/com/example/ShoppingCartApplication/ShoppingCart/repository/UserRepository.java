package com.example.ShoppingCartApplication.ShoppingCart.repository;

import com.dailycodework.dreamshops.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
