package com.movie.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.api.BookingRestController;
import com.movie.backend.api.EventRestController;
import com.movie.backend.dto.*;
import com.movie.backend.entity.Event;
import com.movie.backend.exception.BookingException;
import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.BookingService;
import com.movie.backend.service.EventService;
import com.movie.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = EventRestController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
@Slf4j
public class EventControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private EventService eventService;


    private EventDTO eventDTO;

    @BeforeEach
    void setUp() {
        eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setStart_date(LocalDate.of(2024, 3, 10));
        eventDTO.setCinemaName("CinemaX");
        eventDTO.setPrice(200);
        eventDTO.setStartTime("18:00");
        eventDTO.setEndTime("20:00");

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        eventDTO.setRoom(roomDTO);

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        eventDTO.setMovie(movieDTO);

        SubtitleTypeDTO subtitleTypeDTO = new SubtitleTypeDTO();
        subtitleTypeDTO.setId(1);
        eventDTO.setSubtitleType(subtitleTypeDTO);
    }


    @Test
    void testDeleteEvent_Success() throws Exception {
        doNothing().when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/api/v1/admin/event/delete/{eventId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testListByDateCinemaMovie_Success() throws Exception {
        List<EventDTO> events = List.of(eventDTO);
        when(eventService.findByDateMovieCinema(any(LocalDate.class), anyLong(), anyString(), anyString()))
                .thenReturn(events);

        mockMvc.perform(get("/api/v1/movies/events/{date}/{cinema}/{movie}/{subType}", "2024-03-10", "CinemaX", 1L, "English")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }
}
