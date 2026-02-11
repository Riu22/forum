package com.example.forum.controller;

import com.example.forum.dto.auth_response;
import com.example.forum.dto.login_request;
import com.example.forum.dto.user_dto;
import com.example.forum.dto.permissions_dto;
import com.example.forum.service.jwt_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class auth_controller {

    @Autowired
    private jwt_service jwt_service;

    @Autowired
    private AuthenticationManager auth_manager;
    @PostMapping("/login")
    public auth_response login(@RequestBody login_request request) {
        auth_manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        // Generate permissions (fetch from DB later)
        var permissions = new permissions_dto(
                List.of("own_topics:write", "own_topics:delete",
                        "own_replies:write", "own_replies:delete"),
                List.of()
        );

        var user_data = new user_dto(
                "user",
                "698bf88005181800130b4ccd",
                "prueba@gmail.com",
                "prueba",
                0,
                "",
                "698bf88005181800130b4ccd",
                permissions
        );

        // Generate token with user claims
        String token = jwt_service.generate_token_with_claims(user_data);

        return new auth_response(user_data, token);
    }
}
