package com.movie.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Table(name = "booking_seat")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking ;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat ;

}
