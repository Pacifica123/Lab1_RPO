package com.example.demo.repos;

import com.example.demo.models.Playlist;

import java.util.List;

public interface PlaylistRepository {
    Playlist read(Long id);
    List<Playlist> readAll();
    void create(Playlist p);
}
