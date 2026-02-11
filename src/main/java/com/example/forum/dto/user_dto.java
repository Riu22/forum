package com.example.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record user_dto(
        String role,
        String _id, // Campo compatible con formato tipo MongoDB
        String email,
        String name,
        int __v,    // Versión del documento
        String avatarUrl,
        String id,  // ID duplicado como en tu ejemplo
        permissions_dto permissions
) {}