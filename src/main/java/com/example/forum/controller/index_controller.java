package com.example.forum.controller;

import com.example.forum.dto.categoriDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class index_controller {
    @GetMapping("/categories")
    public ResponseEntity<categoriDto> getCategories() {
        categoriDto categories = categori_service.getCategories();
        return ResponseEntity.ok(categories);
    }
    /*
    [
    {
        "moderators": [
            "698e02fa05181800130b4cd4"
        ],
        "_id": "698b0197034d110013cdcfba",
        "title": "Dogs and cats",
        "description": "abc",
        "slug": "dogs-and-cats",
        "color": "hsl(15, 50%, 50%)",
        "__v": 0
    },
    {
        "moderators": [],
        "_id": "698e695905181800130b4cde",
        "title": "prueba categoria",
        "description": "bla bla bla",
        "slug": "prueba-categoria",
        "color": "hsl(297, 50%, 50%)",
        "__v": 0
    },
    {
        "moderators": [],
        "_id": "6991ffc505181800130b4ce0",
        "title": "category test",
        "description": "testing category",
        "slug": "category-test",
        "color": "hsl(33, 50%, 50%)",
        "__v": 0
    },
    {
        "moderators": [],
        "_id": "699363bb05181800130b4ce7",
        "title": "category testhtutturturt",
        "description": "2",
        "slug": "category-test-2",
        "color": "hsl(108, 50%, 50%)",
        "__v": 0
    },
    {
        "moderators": [],
        "_id": "699363f005181800130b4ce8",
        "title": "category test 2",
        "description": "3",
        "slug": "category-test-2-2",
        "color": "hsl(87, 50%, 50%)",
        "__v": 0
    },
    {
        "moderators": [],
        "_id": "699365c905181800130b4ce9",
        "title": "test",
        "description": "test",
        "slug": "test",
        "color": "hsl(2, 50%, 50%)",
        "__v": 0
    }
]
     */
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
