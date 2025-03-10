package com.movie.backend.repository;

import com.movie.backend.TestConfig;
import com.movie.backend.entity.Cinema;
import com.movie.backend.entity.City;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestConfig.class})
public class CinemaRepositoryTest {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CityRepository cityRepository;

    private City city;
    private Cinema cinema1, cinema2;

    @BeforeEach
    void setUp() {
        // Create and save City
        city = new City();
        city.setName("random_city");
        city.setPostal_code("ramdom_postal");
        cityRepository.saveAndFlush(city);

        // Create and save Cinema 1
        cinema1 = new Cinema();
        cinema1.setName("Awesome Cinema");
        cinema1.setCity(city);
        cinemaRepository.save(cinema1);

        // Create and save Cinema 2
        cinema2 = new Cinema();
        cinema2.setName("Luxury Cinema");
        cinema2.setCity(city);
        cinemaRepository.save(cinema2);
    }

    @Test
    void testFindByName() {
        Cinema foundCinema = cinemaRepository.findByName("Awesome Cinema");

        assertNotNull(foundCinema);
        assertEquals("Awesome Cinema", foundCinema.getName());
    }

    @Test
    void testFindByCity() {
        List<Cinema> cinemas = cinemaRepository.findByCity(city.getId());

        assertNotNull(cinemas);
        assertFalse(cinemas.isEmpty());
        assertEquals(2, cinemas.size());
        assertEquals(city.getId(), cinemas.get(0).getCity().getId());
    }


}
