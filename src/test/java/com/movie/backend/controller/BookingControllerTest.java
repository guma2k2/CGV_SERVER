package com.movie.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.api.BookingRestController;
import com.movie.backend.api.UserRestController;
import com.movie.backend.dto.*;
import com.movie.backend.entity.User;
import com.movie.backend.exception.BookingException;
import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.BookingService;
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



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = BookingRestController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
@Slf4j
public class BookingControllerTest {


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
    private BookingService bookingService;

    private BookingDTO bookingDTO;

    @BeforeEach
    void setUp() {
        bookingDTO = new BookingDTO();
        bookingDTO.setId(1L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        bookingDTO.setUser(userDTO);
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);
        bookingDTO.setEvent(eventDTO);
        bookingDTO.setSeats("A1, A2");
        bookingDTO.setTotal_amount(400L);
    }

    @Test
    void testCreateBooking_Success() throws Exception {
        when(bookingService.createBooking(any(BookingDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/api/v1/create/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testCreateBooking_Failure() throws Exception {
        when(bookingService.createBooking(any(BookingDTO.class)))
                .thenThrow(new BookingException("Booking failed: No available seats"));

        mockMvc.perform(post("/api/v1/create/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Booking failed: No available seats"));
    }

    @Test
    void testDeleteBooking_Success() throws Exception {
        doNothing().when(bookingService).deleteByBookingId(1L);

        mockMvc.perform(delete("/api/v1/delete/booking/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
