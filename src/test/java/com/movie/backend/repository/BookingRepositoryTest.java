//package com.movie.backend.repository;
//
//import com.movie.backend.TestConfig;
//import com.movie.backend.entity.Booking;
//import com.movie.backend.entity.Event;
//import com.movie.backend.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//public class BookingRepositoryTest {
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Autowired
//    private EventRepository eventRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private Event event;
//    private Booking booking;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        // Create and save a User
//        user = new User();
//        user.setFirstName("testuser");
//        user.setLastName("testuser");
//
//        user.setEmail("testuser@example.com");
//        user = userRepository.saveAndFlush(user);
//
//        // Create and save an Event
//        event = new Event();
//        event.setStart_date(LocalDateTime.now().toLocalDate());
//        event = eventRepository.saveAndFlush(event);
//
//        // Create and save a Booking associated with the Event
//        booking = new Booking();
//        booking.setId(1L);
//        booking.setUser(user);
//        booking.setEvent(event);
//        booking.setCreated_time(LocalDateTime.now());
//        bookingRepository.save(booking);
//    }
//
//    @Test
//    void testListBookingByEvent() {
//        Long eventId = event.getId();
//        List<Booking> bookings = bookingRepository.listBookingByEvent(eventId);
//
//        assertNotNull(bookings);
//        assertFalse(bookings.isEmpty());
//        assertEquals(1, bookings.size());
//        assertEquals(eventId, bookings.get(0).getEvent().getId());
//    }
//
//}
