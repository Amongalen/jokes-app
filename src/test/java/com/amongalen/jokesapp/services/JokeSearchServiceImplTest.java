package com.amongalen.jokesapp.services;

import com.amongalen.jokesapp.domain.Joke;
import com.amongalen.jokesapp.domain.JokeSearchParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JokeSearchServiceImplTest {

    private JokeSearchServiceImpl jokeService;

    public static MockWebServer mockBackEnd;
    private ObjectMapper MAPPER = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        jokeService = new JokeSearchServiceImpl(baseUrl);
    }

    @Test
    void getJokeAllParams() throws Exception {
        Joke mockJoke = Joke.builder().joke("some joke").build();
        mockBackEnd.enqueue(new MockResponse().setBody(MAPPER.writeValueAsString(mockJoke))
                .addHeader("Content-Type", "application/json"));

        JokeSearchParameters searchParameters = JokeSearchParameters.builder()
                .categories(List.of("misc", "programming"))
                .flags(List.of("nsfw", "explicit"))
                .jokeTypes(List.of("single", "twopart"))
                .language("en")
                .build();
        Mono<Joke> joke = jokeService.getJoke(searchParameters);
        StepVerifier.create(joke)
                .expectNextMatches(joke1 -> joke1.getJoke().equals("some joke"))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/joke/misc,programming?blacklistFlags=nsfw,explicit&type=single,twopart&language=en", recordedRequest.getPath());
    }

    @Test
    void getJokeNoParams() throws Exception {
        Joke mockJoke = Joke.builder().joke("some joke").build();
        mockBackEnd.enqueue(new MockResponse().setBody(MAPPER.writeValueAsString(mockJoke))
                .addHeader("Content-Type", "application/json"));

        JokeSearchParameters searchParameters = JokeSearchParameters.builder().build();
        Mono<Joke> joke = jokeService.getJoke(searchParameters);
        StepVerifier.create(joke)
                .expectNextMatches(joke1 -> joke1.getJoke().equals("some joke"))
                .verifyComplete();

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/joke/any", recordedRequest.getPath());
    }
}