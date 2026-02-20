package com.example.forum.dto;

import com.example.forum.entity.user;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public record topicsDto (
    String id,
    String title,
    String content,
    int views,
    @JsonProperty("numberOfReplies")
    Integer numberOfReplies,
    @JsonProperty("createdAt")
    Date createdAt,
    @JsonProperty("updatedAt")
    Date updatedAt,
    String category,//el id de la categoria
    user_dto user // el dto del user
)
{}
