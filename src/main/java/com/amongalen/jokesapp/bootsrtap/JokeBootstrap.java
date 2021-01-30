package com.amongalen.jokesapp.bootsrtap;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.JokeType;
import com.amongalen.jokesapp.repositories.JokeRepository;
import com.amongalen.jokesapp.repositories.JokeTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class JokeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final JokeRepository jokeRepository;
    private final JokeTypeRepository jokeTypeRepository;

    public JokeBootstrap(JokeRepository jokeRepository, JokeTypeRepository jokeTypeRepository) {
        this.jokeRepository = jokeRepository;
        this.jokeTypeRepository = jokeTypeRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadJokes();
    }

    private void loadJokes() {
        JokeType type1 = JokeType.builder().name("programming").build();
        JokeType type2 = JokeType.builder().name("general").build();
        JokeType savedType1 = jokeTypeRepository.save(type1).block();
        JokeType savedType2 = jokeTypeRepository.save(type2).block();

        Joke joke1 = Joke.builder()
                .type(savedType1)
                .setup("There are 10 types of people in this world...")
                .punchline("Those who understand binary and those who don't")
                .build();
        Joke joke2 = Joke.builder()
                .type(savedType1)
                .setup("The punchline often arrives before the set-up.")
                .punchline("Do you know the problem with UDP jokes?")
                .build();
        Joke joke3 = Joke.builder()
                .type(savedType2)
                .setup("What do you call a factory that sells passable products?")
                .punchline("A satisfactory")
                .build();
        Joke joke4 = Joke.builder()
                .type(savedType2)
                .setup("What do prisoners use to call each other?")
                .punchline("Cell phones.")
                .build();

        jokeRepository.save(joke1).block();
        jokeRepository.save(joke2).block();
        jokeRepository.save(joke3).block();
        jokeRepository.save(joke4).block();

        log.error("######## amount of jokes: " + jokeRepository.findAll().collectList().block().size());
    }
}