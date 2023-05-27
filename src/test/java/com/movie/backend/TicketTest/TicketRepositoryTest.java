package com.movie.backend.TicketTest;

import com.movie.backend.entity.Booking;
import com.movie.backend.entity.Ticket;
import com.movie.backend.entity.User;
import com.movie.backend.repository.BookingRepository;
import com.movie.backend.repository.TicketRepository;
import com.movie.backend.repository.UserRepository;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository ;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveTicket() {
        User user = userRepository.findById(1L).get() ;
        Booking booking = bookingRepository.findById(7L).get() ;
        Ticket ticket = Ticket.builder()
                .booking(booking)
                .user(user)
                .qrCode("thuanhocnguvl")
                .build();
        ticketRepository.save(ticket);
    }
}
