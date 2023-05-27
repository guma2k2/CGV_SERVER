package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.*;
import com.movie.backend.dto.CinemaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room ;
    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sub_type_id")
    private SubtitleType subtitleType;
    private LocalDate start_date ;

    private String start_time ;

    private Integer price ; // default with normal seat

    public Event(Long id) {
        this.id = id;
    }

    @Transient
    public String getEndTime() {
        String[] hourSecond = start_time.split(":");
        int hour = Integer.parseInt(hourSecond[0]);
        int minute = Integer.parseInt(hourSecond[1]);
        LocalTime end_time = LocalTime.of(hour , minute);
        LocalDateTime dateTime = start_date.atTime(end_time);
        int duration = movie.getDuration_minutes();
        LocalDateTime endTime = dateTime.plusMinutes(duration);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String ans = endTime.format(formatter);

        return ans;
    }
    @Transient
    public String getStartTime() {
        String[] hourSecond = start_time.split(":");
        int hour = Integer.parseInt(hourSecond[0]);
        int minute = Integer.parseInt(hourSecond[1]);
        LocalTime end_time = LocalTime.of(hour , minute);
        LocalDateTime dateTime = start_date.atTime(end_time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String ans = dateTime.format(formatter);
        return ans;
    }
    @Transient
    public String getCinemaName() {
        return room.getCinema().getName();
    }

    @Transient
    public String getPoster() {
        return movie.getPoster_url();
    }
}
