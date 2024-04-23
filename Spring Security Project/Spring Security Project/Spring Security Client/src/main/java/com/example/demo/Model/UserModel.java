package com.example.demo.Model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String Password;
    private String matchingPassword;
}
