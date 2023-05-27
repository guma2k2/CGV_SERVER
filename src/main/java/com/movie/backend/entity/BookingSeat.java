package com.movie.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table(name = "booking_seat")
@Entity
@AllArgsConstructor
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

    @ManyToOne
    private Event event ;

    public  BookingSeat(Seat seat , Booking booking) {
        this.seat = seat;
        this.booking = booking;
        this.event = booking.getEvent();
    }

    public BookingSeat() {
    }
}
