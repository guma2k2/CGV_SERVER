//package com.movie.backend.repository;
//
//import com.movie.backend.TestConfig;
//import com.movie.backend.entity.Cinema;
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
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//public class RoomRepositoryTest {
//
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @Autowired
//    private CinemaRepository cinemaRepository;
//
//    private Cinema cinema;
//    private Room room1;
//    private Room room2;
//
//    @BeforeEach
//    void setUp() {
//        // Create and save a cinema
//        cinema = new Cinema();
//        cinema.setName("Grand Cinema");
//        cinema = cinemaRepository.save(cinema);  // H2 will generate an ID
//
//        // Create and save rooms associated with the cinema
//        room1 = new Room();
//        room1.setName("Room A");
//        room1.setCinema(cinema);
//        room1 = roomRepository.save(room1);
//
//        room2 = new Room();
//        room2.setName("Room B");
//        room2.setCinema(cinema);
//        room2 = roomRepository.save(room2);
//    }
//
//    @Test
//    void testFindByCinema() {
//        List<Room> rooms = roomRepository.findByCinema("Grand Cinema");
//        assertNotNull(rooms);
//        assertFalse(rooms.isEmpty());
//        assertEquals(2, rooms.size());
//    }
//
//    @Test
//    void testFindByCinemaAndName() {
//        Room foundRoom = roomRepository.findByCinemaAndName("Grand Cinema", "Room A");
//        assertNotNull(foundRoom);
//        assertEquals("Room A", foundRoom.getName());
//    }
//}
