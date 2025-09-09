package com.ecommerce.web.security;

import com.ecommerce.core.entity.User;
import com.ecommerce.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("DEBUG: Looking for user: " + username);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        
        System.out.println("DEBUG: Found user: " + user.getUsername() + ", enabled: " + user.isEnabled() + ", locked: " + user.isAccountNonLocked());
        System.out.println("DEBUG: Password hash: " + user.getPassword().substring(0, 10) + "...");
        
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
            .accountExpired(false)
            .accountLocked(!user.isAccountNonLocked())
            .credentialsExpired(false)
            .disabled(!user.isEnabled())
            .build();
    }
}