package com.amongalen.jokesapp.repositories;

import com.amongalen.jokesapp.domain.Joke;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface JokeRepository extends ReactiveMongoRepository<Joke, String> {

}
