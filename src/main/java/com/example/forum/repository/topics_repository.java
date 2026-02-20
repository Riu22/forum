package com.example.forum.repository;

import com.example.forum.entity.topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface topics_repository extends JpaRepository<topics, Integer> {
    List<topics> findByCategorySlug(String slug);
}
