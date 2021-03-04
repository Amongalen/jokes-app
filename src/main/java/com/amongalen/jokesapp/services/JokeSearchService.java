package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.JokeSearchParameters;
import reactor.core.publisher.Mono;

public interface JokeSearchService {
    Mono<Joke> getJoke(JokeSearchParameters searchParameters);
}
