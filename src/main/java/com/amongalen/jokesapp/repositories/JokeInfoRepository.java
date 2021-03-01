package com.amongalen.jokesapp.repositories;

import com.amongalen.jokesapp.domain.jokeinfo.JokeInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface JokeInfoRepository extends ReactiveMongoRepository<JokeInfo, String> {
}
