package com.example.forum.service;

import com.example.forum.dto.categoriDto;
import com.example.forum.dto.request_categoriDto;
import com.example.forum.entity.categori;
import com.example.forum.repository.categori_repository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class categori_service {
    @Autowired
    categori_repository categori_repository;

    public List<categoriDto> get_categories() {
        return categori_repository.findAll().stream()
                .map(cat -> new categoriDto(
                        cat.getId(),
                        Collections.emptyList(),
                        cat.getTitle(),
                        cat.getDescription(),
                        cat.getSlug(),
                        cat.getColor(),
                        cat.get__v()
                ))
                .toList();
    }


    public categoriDto post_categori(String title, String description) {
        categori entiti = new categori();
        entiti.setTitle(title);
        entiti.setDescription(description);
        entiti.setColor(generateRandomHexColor());
        entiti.set__v(0);

        categori saved_categori = categori_repository.save(entiti);

        // Now getId() has the generated UUID
        saved_categori.setSlug(saved_categori.getId() + "-" + saved_categori.getTitle().replace(" ", "-"));
        categori_repository.save(saved_categori); // save again with the slug

        return new categoriDto(
                saved_categori.getId(),
                Collections.emptyList(),
                saved_categori.getTitle(),
                saved_categori.getDescription(),
                saved_categori.getSlug(),
                saved_categori.getColor(),
                saved_categori.get__v()
        );
    }

    private String generateRandomHexColor() {
        java.util.Random obj = new java.util.Random();
        int rand_num = obj.nextInt(0xffffff + 1);
        return String.format("#%06x", rand_num);
    }

    public categoriDto get_category_by_slug(String slug) {
        categori cat = categori_repository.findBySlug(slug);
        if (cat == null) {
            return null;
        }
        return new categoriDto(
                cat.getId(),
                Collections.emptyList(),
                cat.getTitle(),
                cat.getDescription(),
                cat.getSlug(),
                cat.getColor(),
                cat.get__v()
        );
    }
}
