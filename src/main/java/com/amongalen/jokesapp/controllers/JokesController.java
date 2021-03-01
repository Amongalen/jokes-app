package com.amongalen.jokesapp.controllers;

import com.amongalen.jokesapp.services.JokeInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JokesController {

    private final JokeInfoService jokeInfoService;

    public JokesController(JokeInfoService jokeInfoService) {
        this.jokeInfoService = jokeInfoService;
    }

    @GetMapping({"/", ""})
    public String index(Model model) {
        model.addAttribute("jokeInfo", jokeInfoService.getJokeInfo());
        return "index";
    }
}
