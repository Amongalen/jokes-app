package com.amongalen.jokesapp.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JokeSearchParameters {
    private String language;
    @Singular
    private List<String> categories;
    @Singular
    private List<String> flags;
    @Singular
    private List<String> jokeTypes;
}
