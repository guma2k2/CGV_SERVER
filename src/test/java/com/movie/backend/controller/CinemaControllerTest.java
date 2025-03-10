package com.movie.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.api.CinemaRestController;
import com.movie.backend.api.UserRestController;
import com.movie.backend.dto.*;
import com.movie.backend.entity.User;
import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.CinemaService;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = CinemaRestController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
@Slf4j
public class CinemaControllerTest {


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
    private CinemaService cinemaService;

    private CinemaDTO cinemaDTO;

    @BeforeEach
    void setUp() {
        cinemaDTO = new CinemaDTO();
        cinemaDTO.setId(1L);
        cinemaDTO.setName("CinemaX");
        cinemaDTO.setAddress("123 Street, City");

    }

    @Test
    void testFindByDateAndCity() throws Exception {
        LocalDate date = LocalDate.of(2024, 3, 10);
        String cityName = "New York";
        Long movieId = 1L;
        String subType = "English";

        when(cinemaService.findByDateAndCity(date, cityName, movieId, subType)).thenReturn(List.of(cinemaDTO));

        mockMvc.perform(get("/api/v1/movies/cinemas/{startDate}/{cityName}/{movieId}/{subType}", date, cityName, movieId, subType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("CinemaX"));
    }

    @Test
    void testFindByCity() throws Exception {
        Integer cityId = 1;
        when(cinemaService.findByCity(cityId)).thenReturn(List.of(cinemaDTO));

        mockMvc.perform(get("/api/v1/movies/cinemas/find/city/{cityId}", cityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("CinemaX"));
    }

    @Test
    void testGetById() throws Exception {
        Long cinemaId = 1L;
        when(cinemaService.get(cinemaId)).thenReturn(cinemaDTO);

        mockMvc.perform(get("/api/v1/movies/cinemas/{cinemaId}", cinemaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("CinemaX"));
    }



}
