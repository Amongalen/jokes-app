package com.amongalen.jokesapp.repositories;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.JokeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class JokeRepositoryTest {
    @Autowired
    JokeRepository jokeRepository;
    @Autowired
    JokeTypeRepository jokeTypeRepository;

    @BeforeEach
    void setUp() {
        jokeRepository.deleteAll().block();
        jokeTypeRepository.deleteAll().block();
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

        jokeRepository.save(joke1).block();
        jokeRepository.save(joke2).block();
        jokeRepository.save(joke3).block();
    }

    @Test
    public void findAllByTypeName() {
        List<Joke> programmingJokes = jokeRepository.findAllByTypeName("programming").collectList().block();
        List<Joke> generalJokes = jokeRepository.findAllByTypeName("general").collectList().block();

        assertEquals(2, programmingJokes.size());
        assertEquals(1, generalJokes.size());
    }

    @Test
    public void findAllByTypeNameNotFound() {
        List<Joke> fooJokes = jokeRepository.findAllByTypeName("foo").collectList().block();

        assertEquals(0, fooJokes.size());
    }
}