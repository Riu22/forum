package com.example.forum.dto;

import java.util.List;

public record permissions_dto(
        List<String> root,
        List<String> categories
) {}