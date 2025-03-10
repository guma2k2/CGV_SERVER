package com.movie.backend.service;


import com.cloudinary.Cloudinary;
import com.movie.backend.dto.CinemaDTO;
import com.movie.backend.entity.Cinema;
import com.movie.backend.exception.BookingException;
import com.movie.backend.exception.CinemaException;
import com.movie.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CinemaServiceTest {

    @Mock
    private EventRepository eventRepository ;

    @Mock
    private CinemaImageRepository cinemaImageRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CinemaRepository cinemaRepository;

    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private CinemaService cinemaService;


    private Cinema cinema;
    private CinemaDTO cinemaDTO;

    @BeforeEach
    void setUp() {
        cinema = new Cinema();
        cinema.setId(1L);
        cinema.setName("CinemaX");

        cinemaDTO = new CinemaDTO();
        cinemaDTO.setId(1L);
        cinemaDTO.setName("CinemaX");
    }


    @Test
    void testFindByDateAndCity_NullParams_ShouldThrowException() {
        CinemaException exception = assertThrows(CinemaException.class,
                () -> cinemaService.findByDateAndCity(null, "New York", 1L, "2D"));
        assertEquals("Date, cityName , subType cannot be null", exception.getMessage());
    }

    @Test
    void testFindByCity_ValidCityId() {
        when(cinemaRepository.findByCity(anyInt())).thenReturn(Arrays.asList(cinema));
        when(modelMapper.map(cinema, CinemaDTO.class)).thenReturn(cinemaDTO);

        List<CinemaDTO> cinemas = cinemaService.findByCity(1);

        assertEquals(1, cinemas.size());
        assertEquals("CinemaX", cinemas.get(0).getName());
    }

    @Test
    void testFindByCity_NullCityId_ShouldThrowException() {
        CinemaException exception = assertThrows(CinemaException.class, () -> cinemaService.findByCity(null));
        assertEquals("The id of city not found", exception.getMessage());
    }

    @Test
    void testFindAll() {
        when(cinemaRepository.findAll()).thenReturn(Arrays.asList(cinema));
        when(modelMapper.map(cinema, CinemaDTO.class)).thenReturn(cinemaDTO);

        List<CinemaDTO> cinemas = cinemaService.findAll();

        assertEquals(1, cinemas.size());
        assertEquals("CinemaX", cinemas.get(0).getName());
    }
}
