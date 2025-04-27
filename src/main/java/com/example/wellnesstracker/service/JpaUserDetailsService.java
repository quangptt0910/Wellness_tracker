package com.example.wellnesstracker.service;

import com.example.wellnesstracker.model.Auth;
import com.example.wellnesstracker.repository.AuthRepository;
import com.example.wellnesstracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Autowired
    public JpaUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        Auth auth = authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Convert your role to a GrantedAuthority. .
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(auth.getRole().name()));

        // Construct and return an instance of Spring Security's User.
        return new org.springframework.security.core.userdetails.User(
                auth.getUsername(),
                auth.getPassword(),
                auth.isEnabled(), // enabled
                true,             // accountNonExpired
                true,             // credentialsNonExpired
                true,             // accountNonLocked
                authorities);
    }

}