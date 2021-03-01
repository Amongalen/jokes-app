package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.jokeinfo.JokeInfo;
import com.amongalen.jokesapp.repositories.JokeInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class JokeInfoServiceImpl implements JokeInfoService {
    private final JokeInfoRepository jokeInfoRepository;

    public JokeInfoServiceImpl(JokeInfoRepository jokeInfoRepository) {
        this.jokeInfoRepository = jokeInfoRepository;
    }

    @Override
    public Mono<JokeInfo> getJokeInfo() {
        return jokeInfoRepository.findAll().next();
    }
}
