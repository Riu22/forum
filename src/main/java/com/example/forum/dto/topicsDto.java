package com.example.forum.dto;

import com.example.forum.entity.user;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

public record topicsDto (
        @JsonProperty("_id") String id,
        String title,
        String content,
        int views,
        @JsonProperty("numberOfReplies") Integer numberOfReplies,
        @JsonProperty("createdAt") Date createdAt,
        @JsonProperty("updatedAt") Date updatedAt,
        categoriDto_nested category,
        user_dto user,
        @JsonProperty("__v") int __v,
        List<replyDto> replies
)
{}
