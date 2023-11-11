package com.example.demo.models;

import java.util.Date;

/**
 *
 * @param id
 * @param name название
 * @param author автор
 * @param isPrivate // TODO: виден ли плейлист всем
 * @param creationDate дата создания
 * @param genre жанр (перечисление)
 */
public record Playlist(
        Long id,
        String name,
        String author,
        boolean isPrivate,
        Date creationDate,
        Genre genre
) { }
