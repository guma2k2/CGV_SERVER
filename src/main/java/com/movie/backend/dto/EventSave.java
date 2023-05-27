package com.movie.backend.dto;

import com.movie.backend.entity.Movie;
import com.movie.backend.entity.Room;
import com.movie.backend.entity.SubtitleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventSave {
    private Room room ;
    private Movie movie ;
    private SubtitleType subtitleType ;
    private LocalDate start_date ;
    private String start_time ;

    private Integer price;



}
