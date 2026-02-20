package com.example.forum.service;

import com.example.forum.dto.user_dto;
import com.example.forum.dto.permissions_dto;
import com.example.forum.entity.user;
import com.example.forum.repository.user_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class user_service {

    @Autowired
    private user_repository user_repository;

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

    private user_dto mapToDto(user user) {
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
}