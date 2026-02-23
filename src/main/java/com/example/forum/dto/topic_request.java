package com.example.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record topic_request(
        String title,
        @JsonProperty("category") String category_name,
        String content
) {
}
