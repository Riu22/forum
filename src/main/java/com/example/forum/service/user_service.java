package com.example.forum.service;

import com.example.forum.dto.*;
import com.example.forum.entity.user;
import com.example.forum.repository.user_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class user_service {

    @Autowired
    private user_repository user_repository;
    @Autowired
    private jwt_service jwt_service;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public user_dto get_user_by_email(String email) {
        user user = user_repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToDto(user);
    }

    public user_dto get_user_by_id(String id) {
        user user = user_repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToDto(user);
    }

    public user_dto mapToDto(user user) {
        permissions_dto permissions = buildAdminPermissions();

        return new user_dto(
                user.getRole(),
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getVersion(),
                user.getAvatarUrl() != null ? user.getAvatarUrl() : "",
                user.getId(),
                permissions
        );
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

    public user_dto get_profile(String token) {
        String email = jwt_service.extract_user_name(token);
        user user = user_repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToDto(user);
    }

    public auth_response update_profile(String token, update_profile_request request) {
        String currentEmail = jwt_service.extract_user_name(token);
        user user = user_repository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.name() != null) user.setName(request.name());

        if (request.email() != null) {
            if (user_repository.existsByEmail(request.email())) {
                throw new RuntimeException("Email already in use");
            }
            user.setEmail(request.email());
        }

        user_repository.save(user);

        user_dto userDto = mapToDto(user);
        String newToken = jwt_service.generate_token_with_claims(userDto);

        return new auth_response(userDto, newToken);
    }

    public user_dto update_password(String token, update_password_request request) {
        String email = jwt_service.extract_user_name(token);
        user user = user_repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user_repository.save(user);
        return mapToDto(user);
    }

}