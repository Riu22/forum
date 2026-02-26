package com.example.forum.repository;

import com.example.forum.entity.topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface topics_repository extends JpaRepository<topics, String> {
    List<topics> findByCategorySlug(String slug);
}