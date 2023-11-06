package com.example.demo.controllers;

import com.example.demo.models.Genre;
import com.example.demo.models.Playlist;
import com.example.demo.models.Song;
import com.example.demo.repos.PlaylistRepository;
import com.example.demo.requests.PlaylistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistController(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @GetMapping
    public List<Playlist> getPlaylists() {
        return playlistRepository.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        Playlist playlist = playlistRepository.read(id);
        if (playlist != null) {
            return new ResponseEntity<>(playlist, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<String> createPlaylist(@RequestBody PlaylistRequest request) {
        try {
            Boolean isPrivate = true;
            if (request.getPrivate() == null){
                isPrivate = false;
            }
            Playlist playlist = new Playlist(
                    request.getId(),
                    request.getName(),
                    request.getAuthor(),
                    isPrivate,
                    request.getCreationDate(),
                    request.getGenre()
            );

            playlistRepository.create(playlist);
            return ResponseEntity.status(HttpStatus.CREATED).body("Плейлист создан.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось создать плейлист..");
        }
    }
//    private List<Playlist> playlists; // для теста
//    public PlaylistController() throws ParseException {
//        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
//        Playlist p1 = new Playlist(
//                1L,
//                "Documentary Crime Music",
//                "Pacifica Music",
//                false,
//                formatter.parse("25.09.2013"),
//                Genre.ALTERNATIVE
//        );
//        playlists = new ArrayList<>();
//        playlists.add(p1);
//    }
//    @GetMapping()
//    public List<Playlist> getPlaylists() {
//        return playlists;
//    }
//
//    @GetMapping("/find")
//    public List<Playlist> searchSongs(@RequestParam("subs") String subname){
//        return playlists.stream()
//                .filter(p -> p.name().contains(subname))
//                .collect(Collectors.toList());
//    }
//
//    @PostMapping()
//    public Playlist createSong(@RequestBody Playlist playlist){
//        playlists.add(playlist);
//        return playlist;
//    }
//
//    @DeleteMapping("/{playlist_id}")
//    public ResponseEntity<String> deleteSong(@PathVariable("playlist_id") Long playlistId) {
//        Playlist playlistToRemove = findPlaylistById(playlistId);
//        if (playlistToRemove != null) {
//            playlists.remove(playlistToRemove);
//            return ResponseEntity.noContent().build(); // Возвращаем HTTP-статус 204 No Content
//        } else {
//            return ResponseEntity.notFound().build(); // Если песня не найдена, возвращаем HTTP-статус 404 Not Found
//        }
//    }
////    почему-то не работает "->"
//    private Playlist findPlaylistById(Long playlistId) {
//        for (Playlist p: playlists
//        ) {
//            if (Objects.equals(p.id(), playlistId)) return p;
//        }
//        return null;
//    }

}
