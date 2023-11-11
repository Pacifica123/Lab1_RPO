package com.example.demo.repos;

import com.example.demo.models.Playlist;
import com.example.demo.models.Song;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

public interface PlaylistRepository {
    Playlist read(Long id);
    List<Playlist> readAll();
    void create(Playlist p);
    void delete(Long id);
    void update(Playlist playlist) throws SQLException;
}
