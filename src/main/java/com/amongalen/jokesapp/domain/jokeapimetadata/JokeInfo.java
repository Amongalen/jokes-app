package com.amongalen.jokesapp.domain.jokeapimetadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JokeInfo {
    public boolean error;
    public String version;
    public Jokes jokes;
    public List<String> formats;
    public int jokeLanguages;
    public int systemLanguages;
    public String info;
    public long timestamp;
}
