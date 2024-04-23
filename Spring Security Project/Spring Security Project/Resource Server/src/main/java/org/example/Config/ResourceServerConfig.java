package org.example.Config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

@EnableWebSecurity

public class ResourceServerConfig {
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/api/**")
                .access(hasAuthority("SCOPE_api.read"))
                .and()
                .oauth2ResourceServer()
                .jwt();
                return http.build();
    }
}
