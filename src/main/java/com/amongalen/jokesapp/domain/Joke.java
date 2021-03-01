package com.amongalen.jokesapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Joke {
    String id;
    String category;
    String type;
    String setup;
    String delivery;
    String joke;
    JokeFlag flags;
    String lang;
    boolean safe;
    boolean error;
    boolean favourite;
}
