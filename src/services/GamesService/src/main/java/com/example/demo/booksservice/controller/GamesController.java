package com.example.demo.booksservice.controller;

import com.example.demo.booksservice.entities.Games;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

@RestController
public class GamesController {

    private Games games = new Games("Game 1", "Jayv1");
    private Games games2 = new Games("Game 2", "Jayv2");
    private Games games3 = new Games("Game 3", "Jayv3");

    private List<Games> gamesList = Arrays.asList(games, games2, games3);
    @GetMapping(value="/jogos", produces = MediaType.APPLICATION_JSON_VALUE)
    public String index() {
        String json = new Gson().toJson(gamesList);

        return json;
    }
}
