package com.example.demo.mvcControllers;

import com.example.demo.models.Playlist;
import com.example.demo.models.Song;
import com.example.demo.repos.PlaylistRepository;
import com.example.demo.requests.PlaylistRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@Controller
@RequestMapping(value = "mvc/playlists")
public class PlaylistControllerMvc {
    private final PlaylistRepository playlistRepository;
    @Autowired
    public PlaylistControllerMvc(PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
    }
    @GetMapping()
    public ModelAndView getPlaylist(){
        ModelAndView m = new ModelAndView("playlistList");
        m.addObject("playlists", playlistRepository.readAll());
        return m;
    }
    @GetMapping("/{playlist_id}")
    public ModelAndView getConcretePlaylist(@PathVariable("playlist_id") Long playlistId){
        ModelAndView m = new ModelAndView("playlist");
        m.addObject("playlist", playlistRepository.read(playlistId));
        return m;
    }
    @GetMapping("/to_create/playlist")
    public ModelAndView toCreate(){
        return new ModelAndView("createFormPlaylist");
    }
    @GetMapping("/to_update/playlist/{playlist_id}")
    public ModelAndView toUpdate(@PathVariable("playlist_id") Long playlistId){
        ModelAndView m = new ModelAndView("updateFormPlaylist");
        m.addObject("playlist", playlistRepository.read(playlistId));
        return m;
    }
    @PostMapping()
    public ModelAndView createPlaylist(@ModelAttribute Playlist playlist){
        //TODO: сервис для проверки входных данных
        playlistRepository.create(playlist);
        ModelAndView m = new ModelAndView("successfulCreatePlaylist");
        return m;
    }
    @PutMapping("/up/{playlist_id}")
    public ModelAndView updatePlaylist(
            @PathVariable("playlist_id") Long playlistId,
            @ModelAttribute PlaylistRequest updateRequest
    ) throws SQLException {
        Playlist existPlaylist = playlistRepository.read(playlistId);
        if (existPlaylist == null){
            return new ModelAndView("failedUpdatePlaylist");
        }
        Playlist updatedPlaylist = new Playlist(
                existPlaylist.id(),
                updateRequest.getName(),
                updateRequest.getAuthor(),
                updateRequest.getPrivate(),
                updateRequest.getCreationDate(),
                updateRequest.getGenre()
        );
        playlistRepository.update(updatedPlaylist);
        return new ModelAndView("successfulUpdatePlaylist");
    }
    @DeleteMapping()
    public ModelAndView deletePlaylist(@PathVariable("playlist_id") Long playlistId){
        playlistRepository.delete(playlistId);
        return new ModelAndView("successfulDeletePlaylist");
    }
}
