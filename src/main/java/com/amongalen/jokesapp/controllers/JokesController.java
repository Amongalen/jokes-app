package com.amongalen.jokesapp.controllers;

import com.amongalen.jokesapp.domain.JokeFormInput;
import com.amongalen.jokesapp.domain.jokeinfo.JokeInfo;
import com.amongalen.jokesapp.services.JokeInfoService;
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

    public JokesController(JokeInfoService jokeInfoService) {
        this.jokeInfoService = jokeInfoService;
    }

    @ModelAttribute("jokeFormInput")
    public JokeFormInput backingJokeFormInput() {
        return new JokeFormInput();
    }

    @ModelAttribute("jokeInfo")
    public Mono<JokeInfo> populateJokeInfo() {
        return jokeInfoService.getJokeInfo();
    }

    @GetMapping({"/", ""})
    public String index() {
        return "index";
    }

    @PostMapping("findJoke")
    public String findJoke(JokeFormInput jokeFormInput, BindingResult result, Model model) {
        return "index";
    }
}
