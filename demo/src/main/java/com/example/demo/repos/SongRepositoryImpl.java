package com.example.demo.repos;

import com.example.demo.SongRowMapper;
import com.example.demo.TypeMappers;
import com.example.demo.models.Song;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Objects;

@Repository
public class SongRepositoryImpl implements SongRepository {

    private final RowMapper<Song> rowMapper = new SongRowMapper();


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
        if (!Objects.isNull(s)){
            String timeLong_interval = TypeMappers.durationToInterval(s.timeLong());
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("id", s.id())
                    .addValue("name", s.name())
                    .addValue("author", s.author())
                    .addValue("timeLong", timeLong_interval)
                    .addValue("isRemix", s.isRemix())
                    .addValue("rating", s.rating())
                    .addValue("publicationDate", s.publicationDate());

            npJdbcTemplate.update("""
                    INSERT INTO songs (name, author, time_long, is_remix, rating, publication_date)
                VALUES(:name, :author, :timeLong::interval, :isRemix, :rating, :publicationDate)
                    """, parameters);
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM songs WHERE id = ?", id);
    }

    @Override
    public void update(Song s) throws SQLException {
        String timeLong_interval = TypeMappers.durationToInterval(s.timeLong());
        PGInterval interval = new PGInterval(timeLong_interval);
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", s.id())
                .addValue("name", s.name())
                .addValue("author", s.author())
                .addValue("timeLong", interval)
                .addValue("isRemix", s.isRemix())
                .addValue("rating", s.rating())
                .addValue("publicationDate", s.publicationDate());

        npJdbcTemplate.update("""
                    UPDATE songs SET name = :name, author = :author, time_long = :timeLong, is_remix = :isRemix, rating = :rating, publication_date = :publicationDate WHERE id = :id
                    """, parameters);
    }

}
