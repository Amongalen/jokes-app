package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.SearchParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class JokeSearchServiceImpl implements JokeSearchService {


    private final WebClient client;

    public JokeSearchServiceImpl(@Value("${joke.api.baseurl}") String baseUrl) {
        client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public Mono<Joke> getJoke(SearchParameters searchParameters) {
        List<String> categories = searchParameters.getCategories();
        String categoriesPath = categories.isEmpty() ? "any" : String.join(",", categories);
        return client.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("joke/")
                                .path(categoriesPath)
                                .queryParams(getParamsMap(searchParameters))
                                .build()
                ).retrieve()
                .bodyToMono(Joke.class);
    }

    private LinkedMultiValueMap<String, String> getParamsMap(SearchParameters searchParameters) {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (!searchParameters.getFlags().isEmpty()) {
            map.add("blacklistFlags", String.join(",", searchParameters.getFlags()));
        }
        if (!searchParameters.getJokeTypes().isEmpty()) {
            map.add("type", String.join(",", searchParameters.getJokeTypes()));
        }

        if (searchParameters.getLanguage() != null) {
            map.add("language", searchParameters.getLanguage());
        }
        return map;
    }
}
