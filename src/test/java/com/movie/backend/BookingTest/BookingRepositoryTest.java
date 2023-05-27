package com.movie.backend.BookingTest;

import com.movie.backend.entity.*;
import com.movie.backend.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository ;

    @Autowired
    private SeatRepository seatRepository ;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository ;

    @Autowired
    private BookingSeatRepository bookingSeatRepository ;

    @Test
    public void testSaveBooking() {
        User user = userRepository.findById(1L).get() ;
        Seat seat1 = seatRepository.findById(1L).get();
        Seat seat2 = seatRepository.findById(2L).get();
        Event event = eventRepository.findById(4L).get();

        Booking booking = Booking.builder()
                .seats("A1, A2")
                .user(user)
                .event(event)
                .total_amount(150000L)
                .build();
        bookingRepository.save(booking);
        BookingSeat bookingSeat1 = BookingSeat.builder()
                .booking(booking)
                .seat(seat1)
                .event(event)
                .build();
        BookingSeat bookingSeat2 = BookingSeat.builder()
                .booking(booking)
                .seat(seat2)
                .event(event)
                .build();
        bookingSeatRepository.save(bookingSeat1);
        bookingSeatRepository.save(bookingSeat2);
    }
}
