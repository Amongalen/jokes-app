package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.jokeapimetadata.JokeInfo;
import reactor.core.publisher.Mono;

public interface JokeApiMetadataService {
    Mono<JokeInfo> getJokeInfo();
}
