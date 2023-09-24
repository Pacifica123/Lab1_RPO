package com.example.demo.models;

import java.util.Date;

public record Song(
        Long id,
        String name,
        String author,
        Integer time_Long, // в секундах
        Boolean is_Remix,
        Double rating,  // TODO: будет ограничение оценки
        Date publication_Date

) { }
