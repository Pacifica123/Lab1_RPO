package com.example.demo.requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class SongRequestWithIntDuration {
    Long id;
    String name;
    String author;
    long timeLong;
    Boolean remix;
    Double raiting;
    Date publicationDate;
    public SongRequestWithIntDuration(
            Long id,
            String name,
            String author,
            long timeLong,
            Boolean isRemix,
            Double rating,
            String publicationDate
    ) throws ParseException {
        this.id = id;
        this.name = name;
        this.author = author;
        this.raiting = rating;
        this.remix = isRemix;
        this.timeLong = timeLong;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.publicationDate = dateFormat.parse(publicationDate);
    }

    public Boolean isRemix() {
        return this.remix;
    }

    public String name() {
        return name;
    }

    public String author() {
        return  author;
    }

    public long timeLong() {
        return timeLong;
    }

    public Double rating() {
        return raiting;
    }

    public Date publicationDate() {
        return publicationDate;
    }

    public Long id() {return id;
    }
}
