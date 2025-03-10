package com.movie.backend.service;


import com.movie.backend.dto.*;
import com.movie.backend.entity.*;
import com.movie.backend.exception.EventValidException;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SeatService seatService ;

    @Mock
    private   MovieRepository movieRepository ;
    @Mock
    private ModelMapper modelMapper ;

    @Mock
    private RoomRepository roomRepository ;
    @Mock
    private SubTitleTypeRepository subTitleTypeRepository;

    private Event event;
    private EventDTO eventDTO;
    private Movie movie;
    private MovieDTO movieDTO;
    private Room room;
    private RoomDTO roomDTO;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setPoster_url("poster_url");

        movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setTitle("Inception");
        movieDTO.setPoster_url("poster_url");

        room = new Room();
        room.setId(1L);
        room.setName("Room A");

        roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setName("Room A");

        event = new Event();
        event.setId(1L);
        event.setStart_date(LocalDate.of(2025, 3, 10));
        event.setMovie(movie);
        event.setRoom(room);

        eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setStart_date(LocalDate.of(2025, 3, 10));
        eventDTO.setMovie(movieDTO);
        eventDTO.setRoom(roomDTO);
    }

    @Test
    void testConvertStringToLocalDateTime() {
        LocalDate date = LocalDate.of(2025, 3, 10);
        LocalDateTime expectedDateTime = LocalDateTime.of(date, LocalTime.of(14, 30));

        LocalDateTime result = eventService.convertStringToLocalDateTime("14:30", date);

        assertEquals(expectedDateTime, result);
    }

    @Test
    void testFindByDateMovieCinema_ValidParams() {
        when(eventRepository.listByDateCinemaMovie(any(), any(), any(), any())).thenReturn(Arrays.asList(event));
        when(modelMapper.map(event, EventDTO.class)).thenReturn(eventDTO);
        when(seatService.findByEvent(anyLong())).thenReturn(Arrays.asList(new SeatDTO()));

        List<EventDTO> events = eventService.findByDateMovieCinema(LocalDate.of(2025, 3, 10), 1L, "CinemaX", "2D");

        assertEquals(1, events.size());
        assertEquals("Inception", events.get(0).getMovie().getTitle());
    }

    @Test
    void testFindByDateMovieCinema_NullParams_ShouldThrowException() {
        EventValidException exception = assertThrows(EventValidException.class, () -> eventService.findByDateMovieCinema(null, 1L, "CinemaX", "2D"));
        assertEquals("The date or movieId or cinemaName or type cannot be null", exception.getMessage());
    }

    @Test
    void testGetById_ValidEvent() {
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(modelMapper.map(event, EventDTO.class)).thenReturn(eventDTO);
        when(seatService.findByEvent(anyLong())).thenReturn(Arrays.asList(new SeatDTO()));

        EventDTO foundEvent = eventService.getById(1L);

        assertNotNull(foundEvent);
        assertEquals("Inception", foundEvent.getMovie().getTitle());
    }

    @Test
    void testGetById_NullId_ShouldThrowException() {
        EventValidException exception = assertThrows(EventValidException.class, () -> eventService.getById(null));
        assertEquals("The id of event cannot found", exception.getMessage());
    }

}
