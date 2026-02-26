package com.example.forum.service;

import com.example.forum.dto.topic_request;
import com.example.forum.dto.topicsDto;
import com.example.forum.dto.categoriDto_nested;
import com.example.forum.dto.replyDto;
import com.example.forum.entity.categori;
import com.example.forum.entity.topics;
import com.example.forum.entity.user;
import com.example.forum.repository.categori_repository;
import com.example.forum.repository.reply_repository;
import com.example.forum.repository.topics_repository;
import com.example.forum.repository.user_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class topics_service {
    @Autowired
    topics_repository topics_repository;
    @Autowired
    categori_repository categori_repository;
    @Autowired
    user_service user_service;
    @Autowired
    jwt_service jwt_service;
    @Autowired
    user_repository user_repository;
    @Autowired
    reply_repository reply_repository;



    public List<topicsDto> get_topics_by_category(String slug) {
        List<topics> entities = topics_repository.findByCategorySlug(slug);
        return entities.stream()
                .map(this::convert_to_dto)
                .toList();
    }

    private topicsDto convert_to_dto(topics entity) {
        categoriDto_nested catDto = null;
        if (entity.getCategory() != null) {
            categori cat = entity.getCategory();
            catDto = new categoriDto_nested(
                    cat.getId(),
                    cat.getModerator() != null ? List.of(cat.getModerator().getId()) : List.of(),
                    cat.getTitle(),
                    cat.getDescription(),
                    cat.getSlug(),
                    cat.getColor(),
                    cat.get__v()
            );
        }

        List<replyDto> replies = reply_repository.findByTopicId(entity.getId()).stream()
                .map(r -> new replyDto(
                        r.getId(),
                        r.getContent(),
                        entity.getId(),
                        r.getUser() != null ? user_service.mapToDto(r.getUser()) : null,
                        r.getCreated_at(),
                        r.getUpdated_at(),
                        r.get__v()
                ))
                .toList();

        return new topicsDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getViews(),
                entity.getNumber_of_replies(),
                entity.getCreated_at(),
                entity.getUpdated_at(),
                catDto,
                entity.getUser() != null ? user_service.mapToDto(entity.getUser()) : null,
                0,
                replies
        );
    }

    public topicsDto create_topic(String slug, topic_request request, String token) {
        System.out.println("Token recibido: " + token);

        categori cat = categori_repository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        user currentUser = null;
        if (token != null) {
            String email = jwt_service.extract_user_name(token);
            currentUser = user_repository.findByEmail(email).orElse(null);
            System.out.println("Email extraído del token: " + email);
        }

        topics newTopic = new topics();
        newTopic.setTitle(request.title());
        newTopic.setContent(request.content());
        newTopic.setCategory(cat);
        newTopic.setUser(currentUser);
        newTopic.setViews(0);
        newTopic.setNumber_of_replies(0);
        newTopic.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
        newTopic.setUpdated_at(new java.sql.Date(System.currentTimeMillis()));

        topics savedTopic = topics_repository.save(newTopic);
        return convert_to_dto(savedTopic);
    }

    public topicsDto get_topic_by_id(String id) {
        System.out.println("Buscando topic con id: " + id);
        topics topic = topics_repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic no encontrado"));
        return convert_to_dto(topic);
    }
    public topicsDto update_topic(String id, topic_request request, String token) {
        topics existing = topics_repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic no encontrado"));
        existing.setTitle(request.title());
        existing.setContent(request.content());
        existing.setUpdated_at(new java.sql.Date(System.currentTimeMillis()));
        topics saved = topics_repository.save(existing);
        return convert_to_dto(saved);
    }

    public void delete_topic(String id, String token) {
        topics existing = topics_repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic no encontrado"));
        reply_repository.deleteByTopicId(id);
        topics_repository.delete(existing);
    }
}