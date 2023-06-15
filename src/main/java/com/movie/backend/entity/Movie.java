package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.movie.backend.ultity.FileUploadUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true)
    private String title ;
    @Column(length = 1000)
    private String description;

    private int duration_minutes;

    private LocalDate release_date;

    @Enumerated(EnumType.STRING)
    private Language language ;

    @Enumerated(EnumType.STRING)
    private Rating rating;
    private String director ;
    private String cast ;

    @Column(name = "is_showing")
    private boolean isShowing ;
    @Column(length = 1000)
    private String poster_url ;
    private String trailer ;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "movie_genre" ,
            joinColumns =  @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonManagedReference
    private Set<Genre> genres = new HashSet<>();

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }
    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
    }

    @Transient
    public String getPhotosImagePath() {
        String baseUrl = FileUploadUtil.baseUrl ;
        if (id == null || poster_url == null) return "https://www.pngitem.com/pimgs/m/150-1503945_transparent-user-png-default-user-image-png-png.png";
        return baseUrl + "/movie-posters/" + this.id + "/" + this.poster_url;
    }


}
