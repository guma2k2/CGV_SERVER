//package com.movie.backend.repository;
//
//import com.movie.backend.TestConfig;
//import com.movie.backend.entity.Event;
//import com.movie.backend.entity.Room;
//import com.movie.backend.entity.Seat;
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
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//public class SeatRepositoryTest {
//
//
//    @Autowired
//    private SeatRepository seatRepository;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @Autowired
//    private EventRepository eventRepository;
//
//    private Room room;
//    private Event event;
//    private Seat seat1;
//    private Seat seat2;
//
//    @BeforeEach
//    void setUp() {
//        // Create and save a room
//        room = new Room();
//        room.setId(1L);
//        room.setName("Room A");
//        room = roomRepository.save(room);
//
//        // Create and save seats in the room
//        seat1 = new Seat();
//        seat1.setId(1L);
//        seat1.setSeat_name("A1");
//        seat1.setRoom(room);
//        seat1 = seatRepository.save(seat1);
//
//        seat2 = new Seat();
//        seat2.setId(2L);
//        seat2.setSeat_name("A2");
//        seat2.setRoom(room);
//        seat2 = seatRepository.save(seat2);
//
//        // Create and save an event associated with the room
//        event = new Event();
//        event.setId(1L);
//        event.setRoom(room);
//        event = eventRepository.save(event);
//    }
//
//    @Test
//    void testFindByNameRoom() {
//        String[] seatNames = {"A1", "A2"};
//        List<Seat> seats = seatRepository.findByNameRoom(seatNames, room.getId());
//
//        assertNotNull(seats);
//        assertFalse(seats.isEmpty());
//        assertEquals(2, seats.size());
//    }
//}
