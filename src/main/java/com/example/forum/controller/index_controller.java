package com.example.forum.controller;

import com.example.forum.dto.categoriDto;
import com.example.forum.dto.request_categoriDto;
import com.example.forum.dto.topic_request;
import com.example.forum.dto.topicsDto;
import com.example.forum.service.categori_service;
import com.example.forum.service.topics_service;
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
    public ResponseEntity<?> create_topic(@RequestBody topic_request request) {
        return ResponseEntity.ok(topics_service.create_topic(request));
    }

}
