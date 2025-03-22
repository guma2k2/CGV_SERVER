//package com.movie.backend.repository;
//
//import com.movie.backend.TestConfig;
//import com.movie.backend.entity.Cinema;
//import com.movie.backend.entity.Event;
//import com.movie.backend.entity.Room;
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
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//public class EventRepositoryTest {
//
//    @Autowired
//    private EventRepository eventRepository;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//
//    @Autowired
//    private CinemaRepository cinemaRepository;
//
//    private Cinema cinema;
//    private Room room;
//    private Event event1, event2;
//    private LocalDate eventDate = LocalDate.now();
//
//    @BeforeEach
//    void setUp() {
//        // Create and save Cinema
//        cinema = new Cinema();
//        cinema.setName("Awesome Cinema");
//        cinema = cinemaRepository.saveAndFlush(cinema);
//        // Create and save Room
//        room = new Room();
//        room.setName("IMAX");
//        room.setCinema(cinema);
//        room = roomRepository.saveAndFlush(room);
//
//        // Create and save Event 1
//        event1 = new Event();
//        event1.setStart_date(eventDate);
//        event1.setRoom(room);
//        eventRepository.saveAndFlush(event1);
//
//        // Create and save Event 2
//        event2 = new Event();
//        event2.setStart_date(eventDate);
//        event2.setRoom(room);
//        eventRepository.saveAndFlush(event2);
//    }
//
//    @Test
//    void testListByDateCinema() {
//        List<Event> events = eventRepository.listByDateCinema(eventDate, cinema.getId());
//
//        assertNotNull(events);
//        assertFalse(events.isEmpty());
//        assertEquals(2, events.size());
//        assertEquals(eventDate, events.get(0).getStart_date());
//        assertEquals(cinema.getId(), events.get(0).getRoom().getCinema().getId());
//    }
//
//    @Test
//    void testListByDateRoom() {
//        List<Event> events = eventRepository.listByDateRoom(room.getId(), eventDate);
//
//        assertNotNull(events);
//        assertFalse(events.isEmpty());
//        assertEquals(2, events.size());
//        assertEquals(eventDate, events.get(0).getStart_date());
//        assertEquals(room.getId(), events.get(0).getRoom().getId());
//    }
//
//
//}
