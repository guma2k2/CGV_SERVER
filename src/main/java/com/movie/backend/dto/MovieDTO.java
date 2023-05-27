package com.movie.backend.dto;

import com.movie.backend.entity.Language;
import com.movie.backend.entity.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long id ;
    private String title ;
    private String description;

    private int duration_minutes;
    private LocalDate release_date;
    private Language language ;
    private Rating rating;
    private String director ;
    private String cast ;
    private boolean isShowing ;
    private String poster_url ;
    private String trailer ;
    private Set<GenreDTO> genres;
    private String photosImagePath;

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster_url='" + photosImagePath + '\'' +
                '}';
    }

}
