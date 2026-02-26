package com.example.forum.repository;

import com.example.forum.entity.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface reply_repository extends JpaRepository<reply, String> {
    @Transactional
    void deleteByTopicId(String topicId);
    List<reply> findByTopicId(String topicId);
}