package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "city")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String name ;
    private String postal_code ;

    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true , mappedBy = "city")
    @JsonIgnore
    private List<Cinema> cinemas = new ArrayList<>();

    public City(Integer id , String name) {
        this.id = id ;
        this.name = name ;
    }
    public void addCinema(Cinema cinema) {
        this.cinemas.add(cinema);
    }
}
