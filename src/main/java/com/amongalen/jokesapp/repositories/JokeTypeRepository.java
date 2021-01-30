package com.amongalen.jokesapp.repositories;

import com.amongalen.jokesapp.domain.JokeType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface JokeTypeRepository extends ReactiveMongoRepository<JokeType, String> {
}
