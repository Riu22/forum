package com.example.forum.controller;

import com.example.forum.dto.auth_response;
import com.example.forum.dto.login_request;
import com.example.forum.dto.user_dto;
import com.example.forum.service.jwt_service;
import com.example.forum.service.user_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class auth_controller {

    @Autowired
    private jwt_service jwt_service;

    @Autowired
    private AuthenticationManager auth_manager;

    @Autowired
    private user_service user_service;

    @PostMapping("/login")
    public ResponseEntity<auth_response> login(@RequestBody login_request request) {
        try {
            // Autenticar usuario
            Authentication authentication = auth_manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            user_dto user_data = user_service.get_user_by_email(request.username());

            String token = jwt_service.generate_token_with_claims(user_data);

            return ResponseEntity.ok(new auth_response(user_data, token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}