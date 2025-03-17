package com.movie.backend.repository;

import com.movie.backend.TestConfig;
import com.movie.backend.entity.Booking;
import com.movie.backend.entity.Event;
import com.movie.backend.entity.Ticket;
import com.movie.backend.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventRepository eventRepository;

    private User user;
    private Event event;
    private Booking booking;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("asfa@asdfasf");
        user.setFirstName("asfa@asdfasf");
        user.setLastName("asfa@asdfasf");

        user = userRepository.saveAndFlush(user); // Save user first

        event = new Event();
        event.setId(1L);
        event = eventRepository.saveAndFlush(event); // Save event first

        booking = new Booking();
        booking.setId(1L);
        booking.setUser(user);
        booking.setEvent(event);
        booking = bookingRepository.save(booking); // Save booking before ticket

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setBooking(booking);
        ticket.setCreatedTime(LocalDateTime.now().minusDays(3));

        ticket = ticketRepository.save(ticket); // Now save ticket
    }


    @Test
    void testFindByUser() {
        Long userId = 1L;
        List<Ticket> tickets = ticketRepository.findByUser(userId);
        assertNotNull(tickets);
        assertFalse(tickets.isEmpty());
        assertEquals(userId, tickets.get(0).getBooking().getUser().getId());
    }


    @Test
    void testFindByDateMovie() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        List<Ticket> tickets = ticketRepository.findByDateMovie(startDate, endDate);
        assertNotNull(tickets);
        assertFalse(tickets.isEmpty());
        assertTrue(tickets.get(0).getCreatedTime().isAfter(startDate) && tickets.get(0).getCreatedTime().isBefore(endDate));
    }

}
