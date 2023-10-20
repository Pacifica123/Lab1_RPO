package com.example.demo.models;

/**
 * Связка песни с плейлистом куда она добавлена
 * @param id
 * @param songId добавленный трек (что)
 * @param playlistId пополненный плейлист (куда)
 */
public record PlaylistSongLink(
        Long id,
        Long songId,
        Long playlistId
) {
}
