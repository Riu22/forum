package com.example.forum.service;

import com.example.forum.repository.user_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class custom_user_details_service implements UserDetailsService {
    @Autowired
    private user_repository user_repository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Buscando usuario: " + email);

        return user_repository.findByEmail(email)
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