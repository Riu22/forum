package com.example.forum.repository;

import com.example.forum.entity.categori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface categori_repository extends JpaRepository<categori, String> {
    categori findBySlug(String slug);
    Optional<categori> findByNombre(String nombre);
}
