package com.example.forum.service;

import com.example.forum.repository.user_repository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class custom_user_details_service implements UserDetailsService {

    private final user_repository userRepository;

    public custom_user_details_service(user_repository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Buscando usuario: " + email); // <- log

        return userRepository.findByEmail(email)
                .map(user -> {
                    System.out.println("Usuario encontrado: " + user.getEmail());
                    System.out.println("Password en BD: " + user.getPassword());
                    return org.springframework.security.core.userdetails.User
                            .withUsername(user.getEmail())
                            .password(user.getPassword())
                            .authorities(user.getRole() != null ? user.getRole() : "admin")
                            .build();
                })
                .orElseThrow(() -> {
                    System.out.println("Usuario NO encontrado: " + email);
                    return new UsernameNotFoundException("Usuario no encontrado: " + email);
                });
    }
}