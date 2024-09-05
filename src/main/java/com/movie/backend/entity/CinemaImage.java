package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.movie.backend.ultity.FileUploadUtil;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "cinema_images")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    @JsonBackReference
    private Cinema cinema ;

    public CinemaImage(String name) {
        this.name = name;
    }

    public CinemaImage(String name, Cinema cinema) {
        this.name = name;
        this.cinema = cinema;
    }

    @Transient
    public String getExtraImagePath() {
        return this.name;
    }
}
