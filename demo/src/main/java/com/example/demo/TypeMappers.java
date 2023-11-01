package com.example.demo;

import org.postgresql.util.PGInterval;

import java.sql.SQLException;
import java.time.Duration;

public class TypeMappers {
    public static Duration intervalToDuration(String interval) throws SQLException {
        PGInterval pgi = new PGInterval(interval);
        Duration duration = Duration.ZERO;
        duration = duration
                .plusSeconds((long)pgi.getSeconds())
                .plusMinutes((long)pgi.getMinutes())
                .plusHours((long)pgi.getHours());
        return duration;
    }
    public static String durationToInterval(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

}

