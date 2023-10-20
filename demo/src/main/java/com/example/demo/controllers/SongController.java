package com.example.demo.controllers;

import com.example.demo.models.Song;
import com.example.demo.repos.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/songs")
public class SongController {
    private final SongRepository songRepository;
    @Autowired
    public SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    @GetMapping()
    public List<Song> getSongs(){
        return songRepository.readAll();
    }
    @GetMapping("/{song_id}")
    public Song getSongs(@PathVariable("song_id") Long songId){
        return songRepository.read(songId);
    }
//    @GetMapping("/find")
//    public List<Song> searchSongs(@RequestParam("subs") String subname){
//        return songs.stream()
//                .filter(song -> song.name().contains(subname))
//                .collect(Collectors.toList());
//    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createSong(@RequestBody Song song){
        songRepository.create(song);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleException(EmptyResultDataAccessException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }



//    @PutMapping("/{song_id}")
//    public Song updateSong(@PathVariable("song_id") Long songId, @RequestBody Song song){
//        Song updatedSong = findSongById(songId);
//        if (updatedSong != null) {
//            // Обновляем существующий объект
//            updatedSong = new Song(
//                    songId,
//                    song.name(),
//                    song.author(),
//                    song.timeLong(),
//                    song.isRemix(),
//                    song.rating(),
//                    song.publicationDate()
//            );
//            songs.remove(findSongById(songId));
//            songs.add(updatedSong);
//        }
//
//        return updatedSong;
//    }

}
