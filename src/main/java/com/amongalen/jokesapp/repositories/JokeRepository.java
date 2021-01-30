package com.amongalen.jokesapp.repositories;

import com.amongalen.jokesapp.domain.Joke;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface JokeRepository extends ReactiveMongoRepository<Joke, String> {
    public Flux<Joke> findAllByTypeName(String jokeTypeName);
}
