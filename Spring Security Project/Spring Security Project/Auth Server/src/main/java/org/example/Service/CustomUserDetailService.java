package org.example.Service;

import org.example.Entity.User;
import org.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional

public class
CustomUserDetailService implements UserDetailsService {
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder(11) ;
    }
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(emailId);
        if(user==null) {
            throw new UsernameNotFoundException("no user found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(List.of(user.getRole()))
        );
    }
    private Collection<? extends GrantedAuthority>  getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String role:roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
