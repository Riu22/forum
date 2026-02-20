package com.example.forum.dto;

import java.util.List;
import java.util.Map;

public record permissions_dto(
        List<String> root,
        Map<String, List<String>> categories
) {}