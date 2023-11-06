package com.example.demo.requests;

import com.example.demo.models.Genre;

import java.util.Date;

public class PlaylistRequest {
    Long id;
    String name;
    String author;
    Boolean isPrivate;
    Date creationDate;
    String genreString;
    Integer genreNumb;
    public PlaylistRequest() {
        // Конструктор по умолчанию
    }

    public PlaylistRequest(
            Long id,
            String name,
            String author,
            Boolean isPrivate,
            Date creationDate,
            String genreString) {
        this.id = id;
        this.name = name; this.author = author;
        this.isPrivate = isPrivate;
        this.creationDate = creationDate;
        this.genreString = genreString;
    }
    public PlaylistRequest(
            Long id,
            String name,
            String author,
            Boolean isPrivate,
            Date creationDate,
            int genreNumb) {
        this.id = id;
        this.name = name; this.author = author;
        this.isPrivate = isPrivate;
        this.creationDate = creationDate;
        this.genreNumb = genreNumb;
    }

    public String getGenreString() {
        return genreString;
    }
    public Genre getGenre(){
        if (genreString != null) {
            return Genre.valueOf(genreString);
        } else if (genreNumb != null) {
            if (genreNumb >= 0 && genreNumb < Genre.values().length){
                return Genre.values()[genreNumb];
            }
            else throw new IllegalArgumentException("Жанр не найден для номера " + genreNumb);
        } else {
            throw new IllegalArgumentException("Не указан жанр для плейлиста");
        }


    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getAuthor() {
        return author;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }
}
