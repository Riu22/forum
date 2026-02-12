package com.example.forum.dto;

public record register_request(
        String email,
        String name,
        String password,
        String moderateCategory,
        String role
) {
}
