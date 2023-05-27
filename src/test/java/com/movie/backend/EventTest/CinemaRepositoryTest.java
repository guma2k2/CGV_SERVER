package com.movie.backend.EventTest;

import com.movie.backend.entity.Cinema;
import com.movie.backend.entity.Room;
import com.movie.backend.repository.CinemaRepository;
import com.movie.backend.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class CinemaRepositoryTest {
    @Autowired
    private CinemaRepository cinemaRepository ;

    @Autowired
    private RoomRepository roomRepository ;

//    CGV Vincom Bà Triệu
//    CGV Times City
    @Test
    public void findByName() {
        String name1 = "CGV Vincom Bà Triệu";
        String name2 = "CGV Times City";
        List<Room> rooms = roomRepository.findByCinema(name1);
        rooms.forEach(room -> System.out.println(room.getName()));
    }
}
