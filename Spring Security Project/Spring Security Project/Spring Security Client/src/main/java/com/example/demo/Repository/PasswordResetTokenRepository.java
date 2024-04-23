package com.example.demo.Repository;

import com.example.demo.Entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {


    static PasswordResetToken findByToken(String token) {
        return null;
    }

}


