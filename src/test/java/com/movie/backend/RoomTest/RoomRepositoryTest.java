package com.movie.backend.RoomTest;

import com.movie.backend.entity.Cinema;
import com.movie.backend.entity.Room;
import com.movie.backend.repository.CinemaRepository;
import com.movie.backend.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomRepositoryTest {

    @Autowired
    private CinemaRepository cinemaRepository ;
    @Autowired
    private RoomRepository roomRepository ;


    @Test
    public void testCreateRoom() {
        Room room1 = new Room();
        room1.setName("2D Phụ Đề Việt");
        room1.setCapacity("10x10");

        Room room2 = new Room();
        room2.setName("4DX2D Phụ Đề Việt | Rạp 4DX");
        room2.setCapacity("7x8");

        Room room3 = new Room();
        room3.setName("2D Eng&Viet Sub");
        room3.setCapacity("5x6");

        Room room4 = new Room();
        room4.setName("2D Eng&Viet Sub");
        room4.setCapacity("9x10");


        Room room5 = new Room();
        room5.setName("2D Phụ Đề Việt");
        room5.setCapacity("12x11");

        Room room6 = new Room();
        room6.setName("4DX2D Phụ Đề Việt | Rạp 4DX");
        room6.setCapacity("4x5");

        Room room7 = new Room();
        room7.setName("4DX2D Phụ Đề Việt | Rạp 4DX");
        room7.setCapacity("4x5");

        Room room8 = new Room();
        room8.setName("2D Phụ Đề Việt");
        room8.setCapacity("7x8");

        Room room9 = new Room();
        room9.setName("4DX2D Phụ Đề Việt | Rạp 4DX");
        room9.setCapacity("4x5");

        Room room10 = new Room();
        room10.setName("2D Phụ Đề Việt");
        room10.setCapacity("7x8");

        Room room11 = new Room();
        room11.setName("4DX2D Phụ Đề Việt | Rạp 4DX");
        room11.setCapacity("7x8");

// Assign each room to a cinema
        Cinema cinema1 = cinemaRepository.findById(1L).orElse(null);
        Cinema cinema2 = cinemaRepository.findById(2L).orElse(null);
        Cinema cinema3 = cinemaRepository.findById(3L).orElse(null);

        room1.setCinema(cinema1);
        room2.setCinema(cinema1);
        room3.setCinema(cinema1);
        room10.setCinema(cinema1);
        room11.setCinema(cinema1);

        room4.setCinema(cinema2);
        room5.setCinema(cinema2);
        room6.setCinema(cinema2);
        room7.setCinema(cinema2);
        room8.setCinema(cinema2);

        room9.setCinema(cinema3);
// Save the rooms to the database
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        roomRepository.save(room4);
        roomRepository.save(room5);
        roomRepository.save(room6);
        roomRepository.save(room7);
        roomRepository.save(room8);
        roomRepository.save(room9);
        roomRepository.save(room10);
        roomRepository.save(room11);
    }
}
