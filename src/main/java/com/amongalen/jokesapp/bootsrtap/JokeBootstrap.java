package com.amongalen.jokesapp.bootsrtap;

import com.amongalen.jokesapp.domain.jokeinfo.JokeInfo;
import com.amongalen.jokesapp.repositories.JokeInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JokeBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final JokeInfoRepository jokeInfoRepository;

    public JokeBootstrap(JokeInfoRepository jokeInfoRepository) {
        this.jokeInfoRepository = jokeInfoRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadJokeInfo();
    }

    private void loadJokeInfo() {
        WebClient client = WebClient.builder()
                .baseUrl("https://v2.jokeapi.dev/info")
                .build();
        JokeInfo jokeInfo = client.get().exchangeToMono(response -> {
            if (response.statusCode()
                    .equals(HttpStatus.OK)) {
                return response.bodyToMono(JokeInfo.class);
            } else if (response.statusCode()
                    .is4xxClientError()) {
                return Mono.just(JokeInfo.builder().error(true).build());
            } else {
                return response.createException()
                        .flatMap(Mono::error);
            }
        }).block();
        if (!jokeInfo.error) {
            jokeInfoRepository.save(jokeInfo).block();
        }
        log.error("######## Joke info loaded");
        log.error("######## Joke info in repository: " + jokeInfoRepository.findAll().count().block().equals(1L));
    }
}