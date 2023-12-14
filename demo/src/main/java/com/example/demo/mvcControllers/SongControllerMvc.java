package com.example.demo.mvcControllers;

import com.example.demo.TypeMappers;
import com.example.demo.models.Song;
import com.example.demo.repos.SongRepository;
import com.example.demo.requests.SongRequestWithIntDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.text.ParseException;

@Controller
@RequestMapping(value = "mvc/songs")
public class SongControllerMvc {
    private final SongRepository songRepository;
    @Autowired
    public SongControllerMvc(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    @GetMapping()
    public ModelAndView getSongs(){
        ModelAndView m = new ModelAndView("songsList");
        m.addObject("songs", songRepository.readAll());
        return m;
    }
    @GetMapping("/{song_id}")
    public ModelAndView getConcreteSong(@PathVariable("song_id") Long songId){
        ModelAndView m = new ModelAndView("song");
        m.addObject("song", songRepository.read(songId));
        return m;
    }
    @GetMapping("/to_create/song")
    public ModelAndView toCreate(){
        return new ModelAndView("createFormSong");
    }
    @GetMapping("/to_update/song/{song_id}")
    public ModelAndView toUpdate(@PathVariable("song_id") Long songId){
        ModelAndView m = new ModelAndView("updateFormSong");
        Song s = songRepository.read(songId);
        m.addObject("updatedRequest", s);
        return m;
    }
    @PostMapping()
    public ModelAndView createSong(@ModelAttribute SongRequestWithIntDuration song) throws ParseException {
        SongRequestWithIntDuration correctSong = song;
        if (song.isRemix() == null){
            correctSong = new SongRequestWithIntDuration(song.id(),
                    song.name(),
                    song.author(),
                    song.timeLong(),
                    false,
                    song.rating(),
                    song.publicationDate().toString()
            );
        }

        songRepository.create(new Song(
                song.id(),
                song.name(),
                song.author(),
                TypeMappers.intToDuration(song.timeLong()),
                song.isRemix(),
                song.rating(),
                song.publicationDate()
        ));
        ModelAndView m = new ModelAndView("successfulCreateSong");
        return m;
    }

    @PostMapping("/up/{song_id}")
    public ModelAndView updateSong(
            @PathVariable("song_id") Long songId,
            @ModelAttribute(name = "updatedRequest") SongRequestWithIntDuration updatedRequest) throws SQLException {
        Song existingSong = songRepository.read(songId);
        if (existingSong == null) {
            // Если песня не найдена - 404
            return new ModelAndView("failedUpdateSong");
        }
        Song updatedSong = new Song(
                existingSong.id(),
                updatedRequest.name(),
                updatedRequest.author(),
                TypeMappers.intToDuration(updatedRequest.timeLong()),
                updatedRequest.isRemix(),
                updatedRequest.rating(),
                updatedRequest.publicationDate()
        );
        songRepository.update(updatedSong);
        return new ModelAndView("successfulUpdateSong");
    }

    @DeleteMapping("/del/{song_id}")
    public ModelAndView deleteSong(@RequestParam("song_id") Long songId){
        songRepository.delete(songId);
        return new ModelAndView("successfulDeleteSong");
    }

}
