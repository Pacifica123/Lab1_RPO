package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Duration;
import java.util.Date;

/**
 *
 * @param id
 * @param name название трека
 * @param author автор
 * @param timeLong длительность
 * @param isRemix оригинал если false
 * @param rating средняя оценка пользователей
 * @param publicationDate дата загрузки
 */
public record Song(
        Long id,
        String name,
        String author,
        Duration timeLong,
        Boolean isRemix,
        Double rating,  // TODO: будет ограничение оценки
        @JsonFormat(pattern = "yyyy-MM-dd") Date publicationDate

) {  }
