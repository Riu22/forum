package com.example.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record user_dto(
        String role,
        String _id,
        String email,
        String name,
        int __v,
        String avatarUrl,
        String id,
        permissions_dto permissions
) {}