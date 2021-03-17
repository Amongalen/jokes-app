package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.jokeapimetadata.JokeInfo;
import com.amongalen.jokesapp.repositories.JokeInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class JokeApiMetadataServiceImpl implements JokeApiMetadataService {
    private final JokeInfoRepository jokeInfoRepository;

    public JokeApiMetadataServiceImpl(JokeInfoRepository jokeInfoRepository) {
        this.jokeInfoRepository = jokeInfoRepository;
    }

    @Override
    public Mono<JokeInfo> getJokeInfo() {
        return jokeInfoRepository.findAll().next();
    }
}
