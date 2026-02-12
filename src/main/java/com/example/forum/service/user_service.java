package com.example.forum.service;

import com.example.forum.dto.user_dto;
import com.example.forum.dto.permissions_dto;
import com.example.forum.entity.user;
import com.example.forum.repository.user_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        permissions_dto permissions = new permissions_dto(
                user.getPermissions() != null ? user.getPermissions() :
                        List.of("own_topics:write", "own_topics:delete",
                                "own_replies:write", "own_replies:delete"),
                List.of()
        );

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
}