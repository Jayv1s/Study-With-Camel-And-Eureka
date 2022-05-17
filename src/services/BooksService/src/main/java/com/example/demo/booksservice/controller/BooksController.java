package com.example.demo.booksservice.controller;

import com.example.demo.booksservice.entities.Books;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BooksController {

    private Books books = new Books("Teste 1", "Jayv1");
    private Books books2 = new Books("Teste 2", "Jayv2");
    private Books books3 = new Books("Teste 3", "Jayv3");

    private List<Books> booksList = Arrays.asList(books, books2, books3);
    @GetMapping(value="/livros", produces = MediaType.APPLICATION_JSON_VALUE)
    public String index() {
        String json = new Gson().toJson(booksList);
        return json;
    }
}
