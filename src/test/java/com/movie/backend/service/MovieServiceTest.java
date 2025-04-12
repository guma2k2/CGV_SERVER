package com.movie.backend.service;


import com.cloudinary.Cloudinary;
import com.movie.backend.dto.BookingComboDTO;
import com.movie.backend.dto.MovieDTO;
import com.movie.backend.dto.TicketDTO;
import com.movie.backend.entity.Booking;
import com.movie.backend.entity.BookingCombo;
import com.movie.backend.entity.Movie;
import com.movie.backend.entity.Ticket;
import com.movie.backend.exception.MovieException;
import com.movie.backend.repository.*;
import com.movie.backend.ultity.VNPayConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository ;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ModelMapper modelMapper;

    @Value("${application.service.movie.moviePerPage}")
    private int moviePerPage ;

    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;
    private MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setDescription("A mind-bending thriller");
        movie.setDuration_minutes(148);
        movie.setRelease_date(LocalDate.of(2010, 7, 16));

        movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setTitle("Inception");
        movieDTO.setDescription("A mind-bending thriller");
        movieDTO.setDuration_minutes(148);
        movieDTO.setRelease_date(LocalDate.of(2010, 7, 16));
    }

    @Test
    void testFindById_ExistingMovie() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(modelMapper.map(movie, MovieDTO.class)).thenReturn(movieDTO);

        MovieDTO foundMovie = movieService.findById(1L);

        assertNotNull(foundMovie);
        assertEquals("Inception", foundMovie.getTitle());
    }

    @Test
    void testFindById_NullId_ShouldThrowException() {
        MovieException exception = assertThrows(MovieException.class, () -> movieService.findById(null));
        assertEquals("The movieId cannot null", exception.getMessage());
    }

    @Test
    void testFindById_NotFound_ShouldThrowException() {
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> movieService.findById(1L));
    }

    @Test
    void testFindByTitle() {
        when(movieRepository.findByTitle("Inception")).thenReturn(Arrays.asList(movie));
        when(modelMapper.map(movie, MovieDTO.class)).thenReturn(movieDTO);

        List<MovieDTO> movies = movieService.findByTitle("Inception");

        assertEquals(1, movies.size());
        assertEquals("Inception", movies.get(0).getTitle());
    }

    @Test
    void testDeleteMovie() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(eventRepository.findByMovie(1L)).thenReturn(Collections.emptyList());
        doNothing().when(movieRepository).deleteById(1L);

        movieService.deleteMovie(1L);

        verify(movieRepository, times(1)).deleteById(1l);
    }
}
