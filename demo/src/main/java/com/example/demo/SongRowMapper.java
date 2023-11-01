package com.example.demo;

import com.example.demo.models.Song;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SongRowMapper implements RowMapper<Song> {
    @Override
    public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Song(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("author"),
                TypeMappers.intervalToDuration(rs.getString("time_long")),
                rs.getBoolean("is_remix"),
                rs.getDouble("rating"),
                rs.getDate("publication_date")
        );
    }
}
