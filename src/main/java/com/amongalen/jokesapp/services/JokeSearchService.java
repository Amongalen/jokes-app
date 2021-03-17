package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.SearchParameters;
import reactor.core.publisher.Mono;

public interface JokeSearchService {
    Mono<Joke> getJoke(SearchParameters searchParameters);
}
