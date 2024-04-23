package org.example.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvidrer implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = customUserDetailService.loadUserByUsername(username);
        return checkPassword(user, password);
    }

    private Authentication checkPassword(UserDetails user, String password1) {
        if(passwordEncoder.matches(user.getPassword(), password1)) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(),
            user.getAuthorities());
        } else {
            throw new BadCredentialsException("BAD CREDENTIALS");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
