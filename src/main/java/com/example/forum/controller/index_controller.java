package com.example.forum.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class index_controller {
    @GetMapping("/categories")
    public String getCategories() {
        return "categories";
    }
    @PostMapping("/categories")/*{title: "prueba categoria", description: "bla bla bla"}
    description
:
        "bla bla bla"
    title
:
        "prueba categoria"
        */
    public String postCategories() {
        return "categories";
    }
}
