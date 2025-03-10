package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private String capacity ; // 10x10 : rows X columns

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Cinema cinema ;

    @OneToMany(fetch = FetchType.EAGER , mappedBy = "room")
    @Builder.Default
    @JsonIgnore
    @JsonManagedReference
    private List<Seat> seats = new ArrayList<>();


    public Room(Long id) {
    }
}
