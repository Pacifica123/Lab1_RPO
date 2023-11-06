package com.example.demo.controllers;

import com.example.demo.models.Song;
import com.example.demo.repos.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
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
    public ModelAndView getSongs(){
        ModelAndView m = new ModelAndView("songsList");
        m.addObject("songs", songRepository.readAll());
        return m;
    }
    @GetMapping("/{song_id}")
    public Song getSongs(@PathVariable("song_id") Long songId){
        return songRepository.read(songId);
    }

    @PostMapping()
    public ResponseEntity<String> createSong(@ModelAttribute Song song){
        Song correctSong = song;
        if (song.isRemix() == null){
            correctSong = new Song(song.id(),
                    song.name(),
                    song.author(),
                    song.timeLong(),
                    false,
                    song.rating(),
                    song.publicationDate()
            );
        }
        songRepository.create(correctSong);
        return ResponseEntity.ok("Песня успешно добавлена");
    }
    @RequestMapping("/to_create")
    public ModelAndView toCreate(Model model){
        ModelAndView m = new ModelAndView("createSong");

        return m;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleException(EmptyResultDataAccessException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @PutMapping("/up/{song_id}")
    public ResponseEntity<String> updateSong(
            @PathVariable("song_id") Long songId,
            @ModelAttribute Song updatedSong) throws SQLException {
        Song existingSong = songRepository.read(songId);
        if (existingSong == null) {
            // Если песня не найдена - 404
            return ResponseEntity.notFound().build();
        }
        Song updatedSongObj = new Song(
                existingSong.id(),
                updatedSong.name(),
                updatedSong.author(),
                updatedSong.timeLong(),
                updatedSong.isRemix(),
                updatedSong.rating(),
                updatedSong.publicationDate()
        );
        songRepository.update(updatedSongObj);
        return ResponseEntity.ok("Песня обновлена");
    }

    @DeleteMapping("/del/{song_id}")
    public ResponseEntity<String> removeSong(
            @PathVariable("song_id") Long songId){
        Song existingSong = songRepository.read(songId);
        if (existingSong == null) {
            // Если песня не найдена - 404
            return ResponseEntity.notFound().build();
        }
        songRepository.delete(songId);
        return ResponseEntity.ok("Песня успешно удалена");
    }
}
