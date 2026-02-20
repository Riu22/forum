package com.example.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record categoriDto(
        @JsonProperty("_id")String id,
        List<String> moderators,
        String title,
        String description,
        String slug,
        String color,
        @JsonProperty("__v")int __v
) {
}
