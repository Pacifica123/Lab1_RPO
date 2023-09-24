package com.example.demo.models;

import java.util.Date;

public record Playlist(
        Integer id,
        String name,
        String author,
        Boolean is_private, // TODO: виден ли плейлист всем
        Date dateOfCreate,
        Genre genre
) {
    public enum Genre{
        CLASSICAL,
        ALTERNATIVE,
        ROCK,
        FOLK,
        JAZZ
    }
}
