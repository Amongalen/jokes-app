package com.amongalen.jokesapp.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JokeFormInput {
    private List<String> categories;
    private String language;
    private List<String> flags;
    private List<String> jokeTypes;
    private Integer amount;
}
