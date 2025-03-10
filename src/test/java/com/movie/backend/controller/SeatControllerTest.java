package com.movie.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.api.SeatRestController;
import com.movie.backend.api.UserRestController;
import com.movie.backend.dto.*;
import com.movie.backend.entity.Seat;
import com.movie.backend.entity.User;
import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.SeatService;
import com.movie.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = SeatRestController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
@Slf4j
public class SeatControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SeatService seatService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private ModelMapper modelMapper;


    private SeatDTO seatDTO;
    private Seat seat;

    @BeforeEach
    void setUp() {
        seatDTO = new SeatDTO();
        seatDTO.setId(1L);
        seatDTO.setSeat_name("A1");

        seat = new Seat();
        seat.setId(1L);
        seat.setSeat_name("A1");
    }

    @Test
    void testSaveSeat() throws Exception {
        when(seatService.save(any(SeatDTO.class), isNull())).thenReturn(seat);

        mockMvc.perform(post("/api/v1/admin/seat/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seatDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.seat_name").value("A1"));

        verify(seatService, times(1)).save(any(SeatDTO.class), isNull());
    }

    @Test
    void testUpdateSeat() throws Exception {
        when(seatService.save(any(SeatDTO.class), eq(1L))).thenReturn(seat);

        mockMvc.perform(put("/api/v1/admin/seat/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seatDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.seat_name").value("A1"));

        verify(seatService, times(1)).save(any(SeatDTO.class), eq(1L));
    }

    @Test
    void testDeleteSeat() throws Exception {
        doNothing().when(seatService).delete(1L);

        mockMvc.perform(delete("/api/v1/admin/seat/delete/1"))
                .andExpect(status().isOk());

        verify(seatService, times(1)).delete(1L);
    }



}
