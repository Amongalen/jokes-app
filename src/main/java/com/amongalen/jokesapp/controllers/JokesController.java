package com.amongalen.jokesapp.controllers;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.JokeSearchParameters;
import com.amongalen.jokesapp.domain.jokeinfo.JokeInfo;
import com.amongalen.jokesapp.services.JokeInfoService;
import com.amongalen.jokesapp.services.JokeSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class JokesController {

    private final JokeInfoService jokeInfoService;
    private final JokeSearchService jokeSearchService;

    public JokesController(JokeInfoService jokeInfoService, JokeSearchService jokeSearchService) {
        this.jokeInfoService = jokeInfoService;
        this.jokeSearchService = jokeSearchService;
    }

    @ModelAttribute("jokeFormInput")
    public JokeSearchParameters backingJokeFormInput() {
        return new JokeSearchParameters();
    }

    @ModelAttribute("jokeInfo")
    public Mono<JokeInfo> populateJokeInfo() {
        return jokeInfoService.getJokeInfo();
    }

    @GetMapping({"/", "", "index"})
    public String index(Joke joke) {
        return "index";
    }

    @PostMapping("findJoke")
    public String findJoke(JokeSearchParameters jokeFormInput, BindingResult result, Model model) {
        Mono<Joke> joke = jokeSearchService.getJoke(jokeFormInput);
        model.addAttribute("joke", joke);
        return "index";
    }
}
