package com.example.demo.repos;

import com.example.demo.models.Playlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class PlaylistRepositoryImpl implements PlaylistRepository {
    private static final String CREATE = """
                INSERT INTO playlists (name, author, is_private, creation_date, genre)
                VALUES(:name, :author, :isPrivate, :creationDate, :genre)
            """;
    private final RowMapper<Playlist> rowMapper = new DataClassRowMapper<>(Playlist.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate npJdbcTemplate;

    @Autowired
    public PlaylistRepositoryImpl(
            JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate npjt
    ){
        this.jdbcTemplate = jdbcTemplate;
        this.npJdbcTemplate = npjt;
    }

    @Override
    public Playlist read(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM playlists WHERE id = ?", rowMapper, id);
        }
        catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("Не удалось найти плейлист с таким id", e.getExpectedSize());
        }
    }

    @Override
    public List<Playlist> readAll() {
        return jdbcTemplate.query("SELECT * FROM playlists", rowMapper);
    }

    @Override
    public void create(Playlist p) {
        if (!Objects.isNull(p)){
            SqlParameterSource parameters = new MapSqlParameterSource()
//                    .addValue("id", p.id())
                    .addValue("name", p.name())
                    .addValue("author", p.author())
                    .addValue("isPrivate", p.isPrivate())
                    .addValue("creationDate", p.creationDate())
                    .addValue("genre", p.genre().name());

            npJdbcTemplate.update(CREATE, parameters);
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM playlists WHERE id = ?", id);
    }

    @Override
    public void update(Playlist p) throws SQLException {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", p.id())
                .addValue("name", p.name())
                .addValue("author", p.author())
                .addValue("isPrivate", p.isPrivate())
                .addValue("creationDate", p.creationDate())
                .addValue("genre", p.genre().toString());

        npJdbcTemplate.update("""
                    UPDATE playlists SET name = :name, author = :author, is_private = :isPrivate, creation_date = :creationDate, genre = :genre WHERE id = :id
                    """, parameters);
    }
}
