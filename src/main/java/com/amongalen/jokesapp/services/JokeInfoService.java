package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.jokeinfo.JokeInfo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface JokeInfoService {
    Mono<JokeInfo> getJokeInfo();
}
