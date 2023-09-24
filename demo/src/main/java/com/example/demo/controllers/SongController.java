package com.example.demo.controllers;

import com.example.demo.models.Song;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/songs")
public class SongController {
    private final List<Song> songs; // для теста
    public SongController() throws ParseException {

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Song song = new Song(
                1L,
                "Dark Around The Corner",
                "Pacifica Music",
                240,
                false,
                4.8,
                formatter.parse("24.09.2013")
        );
        Song song2 = new Song(
                2L,
                "Around The Corner",
                "Pacifica Music",
                134,
                true,
                4.7,
                formatter.parse("24.11.2013")
        );
        songs = List.of(song, song2);
    }
    @GetMapping()
    public List<Song> getSongs(){
        return songs;
    }
    @GetMapping("/{song_id}")
    public Song getSongs(@PathVariable("song_id") Long songId){
        return songs.stream()
                .filter(song -> song.id().equals(songId))
                .findAny()
                .orElse(null);
    }
    @GetMapping("/find/{substring}")
    public List<Song> searchSongs(@PathVariable("substring") String subname){
        return songs.stream()
                .filter(song -> song.name().contains(subname))
                .collect(Collectors.toList());
    }
}
