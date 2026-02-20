package com.example.forum.service;

import com.example.forum.dto.topic_request;
import com.example.forum.dto.topicsDto;
import com.example.forum.entity.categori;
import com.example.forum.entity.topics;
import com.example.forum.repository.categori_repository;
import com.example.forum.repository.topics_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class topics_service {
    @Autowired
    topics_repository topics_repository;
    @Autowired
    categori_repository categori_repository;

    public List<topicsDto> get_topics_by_category(String slug) {
        List<topics> entities = topics_repository.findByCategorySlug(slug);
        return entities.stream()
                .map(this::convert_to_dto)
                .toList();
    }

    private topicsDto convert_to_dto(topics entity) {
        return new topicsDto(
                String.valueOf(entity.getId()),
                entity.getTitle(),
                entity.getContent(),
                entity.getViews(),
                entity.getNumber_of_replies(),
                entity.getCreated_at(),
                entity.getUpdated_at(),
                entity.getCategory() != null ? String.valueOf(entity.getCategory().getId()) : null,
                null
        );
    }

    public topicsDto create_topic(topic_request request) {
        categori cat = categori_repository.findByNombre(request.category_name())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        topics newTopic = new topics();
        newTopic.setTitle(request.title());
        newTopic.setContent(request.content());
        newTopic.setCategory(cat);
        newTopic.setViews(0);
        newTopic.setNumber_of_replies(0);
        newTopic.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
        newTopic.setUpdated_at(new java.sql.Date(System.currentTimeMillis()));

        topics savedTopic = topics_repository.save(newTopic);

        return convert_to_dto(savedTopic);
    }
}
