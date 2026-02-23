package com.example.forum.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record replyDto(
        @JsonProperty("_id") String id,
        String content,
        String topic,
        user_dto user,
        @JsonProperty("createdAt") Date createdAt,
        @JsonProperty("updatedAt") Date updatedAt,
        @JsonProperty("__v") int __v) {
}
