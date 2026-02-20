package com.example.forum.service;

import com.example.forum.dto.auth_response;
import com.example.forum.dto.permissions_dto;
import com.example.forum.dto.register_request;
import com.example.forum.dto.user_dto;
import com.example.forum.entity.user;
import com.example.forum.repository.user_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class register_service {

    @Autowired
    private jwt_service jwt_service;

    @Autowired
    private user_repository user_repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional // Recomendado para asegurar la integridad de la base de datos
    public auth_response register(register_request request) {
        // 1. Validar si el usuario ya existe
        if (user_repository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already in use");
        }

        // 2. Crear la entidad usando el nuevo constructor simplificado
        // No pasamos ID ni Version; JPA los generará automáticamente
        user newUser = new user(
                request.email(),
                request.name(),
                passwordEncoder.encode(request.password()),
                "admin",
                null,
                request.moderateCategory(),
                List.of()
        );

        user savedUser = user_repository.save(newUser);

        user_dto userDto = new user_dto(
                savedUser.getRole(),
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getVersion(),
                savedUser.getAvatarUrl(),
                savedUser.getId(),
                buildAdminPermissions()
        );

        String token = jwt_service.generate_token(savedUser.getEmail(), userDto);

        return new auth_response(userDto, token);
    }

    private permissions_dto buildAdminPermissions() {
        return new permissions_dto(
                List.of(
                        "own_topics:write",
                        "own_topics:delete",
                        "own_replies:write",
                        "own_replies:delete",
                        "categories:write",
                        "categories:delete"
                ),
                Map.of(
                        "*",
                        List.of(
                                "categories_topics:write",
                                "categories_topics:delete",
                                "categories_replies:write",
                                "categories_replies:delete"
                        )
                )
        );
    }
}