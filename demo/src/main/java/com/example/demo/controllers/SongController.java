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
        ModelAndView m = new ModelAndView("ListOfSongs");
        m.addObject("songs", songRepository.readAll());
        return m;
    }
//    public List<Song> getSongs(){
//        return songRepository.readAll();
//    }
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
    public ResponseEntity<String> createSong(@ModelAttribute Song song){
        if (song.isRemix() == null){
            Song correctSong = new Song(song.id(),
                    song.name(),
                    song.author(),
                    song.timeLong(),
                    false,
                    song.rating(),
                    song.publicationDate()
            );
            songRepository.create(correctSong);
        }
        else {
            songRepository.create(song);
        }
        return ResponseEntity.ok("Песня успешно добавлена");
    }
    @RequestMapping("/to_create")
    public ModelAndView toCreate(Model model){
//        model.addAttribute("song", new Song());
        ModelAndView m = new ModelAndView("CreateSongForm");

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
