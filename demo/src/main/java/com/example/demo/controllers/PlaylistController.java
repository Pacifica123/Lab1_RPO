package com.example.demo.controllers;

import com.example.demo.models.Genre;
import com.example.demo.models.Playlist;
import com.example.demo.models.Song;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/playlists")
public class PlaylistController {
    private List<Playlist> playlists; // для теста
    public PlaylistController() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Playlist p1 = new Playlist(
                1L,
                "Documentary Crime Music",
                "Pacifica Music",
                false,
                formatter.parse("25.09.2013"),
                Genre.ALTERNATIVE
        );
        playlists = new ArrayList<>();
        playlists.add(p1);
    }
    @GetMapping()
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    @GetMapping("/find")
    public List<Playlist> searchSongs(@RequestParam("subs") String subname){
        return playlists.stream()
                .filter(p -> p.name().contains(subname))
                .collect(Collectors.toList());
    }

    @PostMapping()
    public Playlist createSong(@RequestBody Playlist playlist){
        playlists.add(playlist);
        return playlist;
    }

    @DeleteMapping("/{playlist_id}")
    public ResponseEntity<String> deleteSong(@PathVariable("playlist_id") Long playlistId) {
        Playlist playlistToRemove = findPlaylistById(playlistId);
        if (playlistToRemove != null) {
            playlists.remove(playlistToRemove);
            return ResponseEntity.noContent().build(); // Возвращаем HTTP-статус 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Если песня не найдена, возвращаем HTTP-статус 404 Not Found
        }
    }
//    почему-то не работает "->"
    private Playlist findPlaylistById(Long playlistId) {
        for (Playlist p: playlists
        ) {
            if (Objects.equals(p.id(), playlistId)) return p;
        }
        return null;
    }

}
