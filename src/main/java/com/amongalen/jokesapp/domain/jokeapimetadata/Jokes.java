package com.amongalen.jokesapp.domain.jokeapimetadata;

import java.util.List;

public class Jokes {
    public int totalCount;
    public List<String> categories;
    public List<String> flags;
    public List<String> types;
    public String submissionURL;
    public IdRange idRange;
    public List<SafeJoke> safeJokes;
}
