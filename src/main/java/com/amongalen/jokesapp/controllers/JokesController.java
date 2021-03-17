package com.amongalen.jokesapp.controllers;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.SearchParameters;
import com.amongalen.jokesapp.domain.jokeapimetadata.JokeInfo;
import com.amongalen.jokesapp.services.JokeApiMetadataService;
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

    private final JokeApiMetadataService jokeApiMetadataService;
    private final JokeSearchService jokeSearchService;

    public JokesController(JokeApiMetadataService jokeApiMetadataService, JokeSearchService jokeSearchService) {
        this.jokeApiMetadataService = jokeApiMetadataService;
        this.jokeSearchService = jokeSearchService;
    }

    @ModelAttribute("searchParameters")
    public SearchParameters backingSearchParameters() {
        return new SearchParameters();
    }

    @ModelAttribute("jokeInfo")
    public Mono<JokeInfo> populateJokeInfo() {
        return jokeApiMetadataService.getJokeInfo();
    }

    @GetMapping({"/", "", "index"})
    public String index(Joke joke) {
        return "index";
    }

    @PostMapping("findJoke")
    public String findJoke(SearchParameters jokeFormInput, BindingResult result, Model model) {
        Mono<Joke> joke = jokeSearchService.getJoke(jokeFormInput);
        model.addAttribute("joke", joke);
        return "index";
    }
}
