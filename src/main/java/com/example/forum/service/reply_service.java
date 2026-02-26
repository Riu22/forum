package com.example.forum.service;

import com.example.forum.dto.categoriDto_nested;
import com.example.forum.dto.replyDto;
import com.example.forum.dto.topicsDto;
import com.example.forum.entity.categori;
import com.example.forum.entity.reply;
import com.example.forum.entity.topics;
import com.example.forum.entity.user;
import com.example.forum.repository.reply_repository;
import com.example.forum.repository.topics_repository;
import com.example.forum.repository.user_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class reply_service {
    @Autowired
    reply_repository reply_repository;
    @Autowired
    topics_repository topics_repository;
    @Autowired
    user_repository user_repository;
    @Autowired
    jwt_service jwt_service;
    @Autowired
    user_service user_service;


    public replyDto create_reply(String topic_id, String content, String token) {
        topics topic = topics_repository.findById(topic_id)
                .orElseThrow(() -> new RuntimeException("Topic no encontrado"));

        user currentUser = null;
        if (token != null) {
            String email = jwt_service.extract_user_name(token);
            currentUser = user_repository.findByEmail(email).orElse(null);
        }

        reply newReply = new reply();
        newReply.setContent(content);
        newReply.setTopic(topic);
        newReply.setUser(currentUser);
        newReply.setCreated_at(new java.sql.Date(System.currentTimeMillis()));
        newReply.setUpdated_at(new java.sql.Date(System.currentTimeMillis()));
        newReply.set__v(0);

        reply saved = reply_repository.save(newReply);

        return new replyDto(
                saved.getId(),
                saved.getContent(),
                topic_id,
                currentUser != null ? user_service.mapToDto(currentUser) : null,
                saved.getCreated_at(),
                saved.getUpdated_at(),
                saved.get__v()
        );

    }public replyDto update_reply(String reply_id, String content, String token) {
        reply existing = reply_repository.findById(reply_id)
                .orElseThrow(() -> new RuntimeException("Reply no encontrada"));

        existing.setContent(content);
        existing.setUpdated_at(new java.sql.Date(System.currentTimeMillis()));

        reply saved = reply_repository.save(existing);

        return new replyDto(
                saved.getId(),
                saved.getContent(),
                saved.getTopic().getId(),
                saved.getUser() != null ? user_service.mapToDto(saved.getUser()) : null,
                saved.getCreated_at(),
                saved.getUpdated_at(),
                saved.get__v()
        );
    }

    public void delete_reply(String reply_id, String token) {
        reply existing = reply_repository.findById(reply_id)
                .orElseThrow(() -> new RuntimeException("Reply no encontrada"));
        reply_repository.delete(existing);
    }

    public List<replyDto> get_replies_by_topic(String topic_id) {
        List<reply> rawReplies = reply_repository.findByTopicId(topic_id);
        System.out.println("Topic id: " + topic_id + " - Replies encontradas: " + rawReplies.size());
        return rawReplies.stream()
                .map(r -> new replyDto(
                        r.getId(),
                        r.getContent(),
                        topic_id,
                        r.getUser() != null ? user_service.mapToDto(r.getUser()) : null,
                        r.getCreated_at(),
                        r.getUpdated_at(),
                        r.get__v()
                ))
                .toList();
    }


}