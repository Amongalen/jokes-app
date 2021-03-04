package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.JokeSearchParameters;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class JokeSearchServiceImpl implements JokeSearchService {


    private final String baseUrl;

    public JokeSearchServiceImpl(@Value("${joke.api.baseurl}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Mono<Joke> getJoke(JokeSearchParameters searchParameters) {
        String categories = String.join(",", searchParameters.getCategories());
        String finalCategories = StringUtils.isBlank(categories) ? "any" : categories;
        LinkedMultiValueMap<String, String> paramsMap = getParamsMap(searchParameters);
        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        return client.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("joke/")
                                .path(finalCategories)
                                .queryParams(paramsMap)
                                .build()

                ).retrieve()
                .bodyToMono(Joke.class);
    }

    private LinkedMultiValueMap<String, String> getParamsMap(JokeSearchParameters searchParameters) {
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
