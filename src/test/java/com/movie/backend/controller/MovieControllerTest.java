package com.movie.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.api.MovieRestController;
import com.movie.backend.api.UserRestController;
import com.movie.backend.dto.*;
import com.movie.backend.entity.User;
import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.MovieService;
import com.movie.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = MovieRestController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
@Slf4j
public class MovieControllerTest {


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
    private MovieService movieService;
    private MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setTitle("Inception");
    }

    @Test
    void testFindAllMovies() throws Exception {
        List<MovieDTO> movies = Collections.singletonList(movieDTO);
        when(movieService.listAll()).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Inception"));

        verify(movieService, times(1)).listAll();
    }

    @Test
    void testFindMovieById() throws Exception {
        when(movieService.findById(1L)).thenReturn(movieDTO);

        mockMvc.perform(get("/api/v1/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"));

        verify(movieService, times(1)).findById(1L);
    }

    @Test
    void testFindBeforeDate() throws Exception {
        List<MovieDTO> movies = Collections.singletonList(movieDTO);
        when(movieService.findBeforeDate()).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies/is/showing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Inception"));

        verify(movieService, times(1)).findBeforeDate();
    }

    @Test
    void testFindAfterDate() throws Exception {
        List<MovieDTO> movies = Collections.singletonList(movieDTO);
        when(movieService.findAfterDate()).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies/will/showing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Inception"));

        verify(movieService, times(1)).findAfterDate();
    }




}
