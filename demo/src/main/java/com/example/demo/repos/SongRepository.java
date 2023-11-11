package com.example.demo.repos;

import com.example.demo.models.Song;

import java.sql.SQLException;
import java.util.List;

public interface SongRepository {
    Song read(Long id);
    List<Song> readAll();
    void create(Song s);
    void delete(Long id);

    void update(Song song) throws SQLException;

}
