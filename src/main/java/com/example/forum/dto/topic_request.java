package com.example.forum.dto;

public record topic_request(
        String title,
        String category_name,
        String content
) {
}
