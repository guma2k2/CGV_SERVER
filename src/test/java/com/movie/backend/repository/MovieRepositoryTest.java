//package com.movie.backend.repository;
//
//import com.movie.backend.TestConfig;
//import com.movie.backend.entity.Movie;
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
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({TestConfig.class})
//public class MovieRepositoryTest {
//
//    @Autowired
//    private MovieRepository movieRepository;
//
//    private Movie movie1;
//    private Movie movie2;
//
//    @BeforeEach
//    void setUp() {
//        // Create and save movie 1
//        movie1 = new Movie();
//        movie1.setTitle("Inception");
//        movieRepository.save(movie1);
//
//        // Create and save movie 2
//        movie2 = new Movie();
//        movie2.setTitle("Interstellar");
//        movieRepository.save(movie2);
//    }
//
//    @Test
//    void testFindAllByTitleContaining() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Movie> movies = movieRepository.findAll("Incep", pageable);
//
//        assertNotNull(movies);
//        assertFalse(movies.isEmpty());
//        assertEquals(1, movies.getContent().size());
//        assertEquals("Inception", movies.getContent().get(0).getTitle());
//    }
//
//    @Test
//    void testGetByTitle() {
//        Movie foundMovie = movieRepository.getByTitle("Interstellar");
//
//        assertNotNull(foundMovie);
//        assertEquals("Interstellar", foundMovie.getTitle());
//    }
//
//
//}
