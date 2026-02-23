package com.example.forum.controller;

import com.example.forum.dto.*;
import com.example.forum.service.categori_service;
import com.example.forum.service.reply_service;
import com.example.forum.service.topics_service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class index_controller {
    @Autowired
    categori_service categori_service;
    @Autowired
    topics_service topics_service;
    @Autowired
    reply_service reply_service;



    @GetMapping("/categories")
    public ResponseEntity<List<categoriDto>> get_categories() {
        return ResponseEntity.ok(categori_service.get_categories());
    }

    @PostMapping("/categories")
    public ResponseEntity<?> post_categories(@RequestBody request_categoriDto categoriDto) {
        return ResponseEntity.ok(categori_service.post_categori(categoriDto.title(), categoriDto.description()));
    }

    @GetMapping("/categories/{slug}")
    public ResponseEntity<?> get_category_by_slug(@PathVariable String slug) {
        return ResponseEntity.ok(categori_service.get_category_by_slug(slug));
    }

    @GetMapping("/categories/{slug}/topics")
    public ResponseEntity<?> get_topics_by_category(@PathVariable String slug) {
        List<topicsDto> topics = topics_service.get_topics_by_category(slug);
        return ResponseEntity.ok(topics);
    }

    @PostMapping("/categories/{slug}/topics")
    public ResponseEntity<?> create_topic(@PathVariable String slug, @RequestBody topic_request request, HttpServletRequest http_request) {
        String auth_header = http_request.getHeader("Authorization");
        String token = (auth_header != null && auth_header.startsWith("Bearer "))
                ? auth_header.substring(7)
                : null;
        return ResponseEntity.ok(topics_service.create_topic(slug, request, token));
    }

    @PostMapping("/topics")
    public ResponseEntity<?> create_topic_by_body(@RequestBody topic_request request, HttpServletRequest http_request) {
        String auth_header = http_request.getHeader("Authorization");
        String token = (auth_header != null && auth_header.startsWith("Bearer "))
                ? auth_header.substring(7)
                : null;
        return ResponseEntity.ok(topics_service.create_topic(request.category_name(), request, token));
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<?> get_topic_by_id(@PathVariable String id) {
        return ResponseEntity.ok(topics_service.get_topic_by_id(id));
    }

    @PostMapping("/topics/{topic_id}/replies")
    public ResponseEntity<?> create_reply(
            @PathVariable String topic_id,
            @RequestBody reply_request request,
            HttpServletRequest http_request) {
        String auth_header = http_request.getHeader("Authorization");
        String token = (auth_header != null && auth_header.startsWith("Bearer "))
                ? auth_header.substring(7)
                : null;
        return ResponseEntity.ok(reply_service.create_reply(topic_id, request.content(), token));
    }
    @PutMapping("/topics/{topic_id}/replies/{reply_id}")
    public ResponseEntity<?> update_reply(
            @PathVariable String topic_id,
            @PathVariable String reply_id,
            @RequestBody reply_request request,
            HttpServletRequest http_request) {
        String auth_header = http_request.getHeader("Authorization");
        String token = (auth_header != null && auth_header.startsWith("Bearer "))
                ? auth_header.substring(7) : null;
        return ResponseEntity.ok(reply_service.update_reply(reply_id, request.content(), token));
    }

    @DeleteMapping("/topics/{topic_id}/replies/{reply_id}")
    public ResponseEntity<?> delete_reply(
            @PathVariable String topic_id,
            @PathVariable String reply_id,
            HttpServletRequest http_request) {
        String auth_header = http_request.getHeader("Authorization");
        String token = (auth_header != null && auth_header.startsWith("Bearer "))
                ? auth_header.substring(7) : null;
        reply_service.delete_reply(reply_id, token);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/categories/{slug}")
    public ResponseEntity<?> update_category(@PathVariable String slug, @RequestBody request_categoriDto request) {
        return ResponseEntity.ok(categori_service.update_category(slug, request.title(), request.description()));
    }

    @DeleteMapping("/categories/{slug}")
    public ResponseEntity<?> delete_category(@PathVariable String slug) {
        categori_service.delete_category(slug);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<?> update_topic(@PathVariable String id, @RequestBody topic_request request, HttpServletRequest http_request) {
        String auth_header = http_request.getHeader("Authorization");
        String token = (auth_header != null && auth_header.startsWith("Bearer "))
                ? auth_header.substring(7) : null;
        return ResponseEntity.ok(topics_service.update_topic(id, request, token));
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<?> delete_topic(@PathVariable String id, HttpServletRequest http_request) {
        String auth_header = http_request.getHeader("Authorization");
        String token = (auth_header != null && auth_header.startsWith("Bearer "))
                ? auth_header.substring(7) : null;
        topics_service.delete_topic(id, token);
        return ResponseEntity.ok().build();
    }

}
