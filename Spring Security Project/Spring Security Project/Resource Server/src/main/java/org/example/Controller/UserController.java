package org.example.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/api/users")
    public String[] getUsers() {
        return new String[]{
                "Danyal",
                "Hamza",
                "Alpha"
        };
    }
}
