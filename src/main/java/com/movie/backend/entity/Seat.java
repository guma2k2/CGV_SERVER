package com.movie.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String seat_name;

    private int row_num;
    private int column_num;
    @Builder.Default
    private SeatType type = SeatType.NORMAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Room room;


}
