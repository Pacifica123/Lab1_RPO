package com.example.demo.repos;

import com.example.demo.models.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongRepositoryImpl implements SongRepository {
    private static final String CREATE = """
                INSERT INTO songs (id, name, author, time_long, is_remix, rating, publication_date)
                VALUES(:id, :name, :author, :timeLong, :isRemix, :rating, :publicationDate)
            """;
    private final RowMapper<Song> rowMapper = new DataClassRowMapper<>(Song.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate npJdbcTemplate;

    @Autowired
    public SongRepositoryImpl(
            JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate npjt
    ){
        this.jdbcTemplate = jdbcTemplate;
        this.npJdbcTemplate = npjt;
    }



    @Override
    public Song read(Long id) {

        try {
            return jdbcTemplate.queryForObject("SELECT * FROM songs WHERE id = ?", rowMapper, id);
        }
        catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("Не удалось найти song с таким id", e.getExpectedSize());
        }
    }

    @Override
    public List<Song> readAll() {
        return jdbcTemplate.query("SELECT * FROM songs", rowMapper);
    }

    @Override
    public void create(Song s) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(s);
        npJdbcTemplate.update(CREATE, parameterSource);
    }
}
