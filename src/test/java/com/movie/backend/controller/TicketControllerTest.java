package com.movie.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.api.TicketRestController;
import com.movie.backend.api.UserRestController;
import com.movie.backend.dto.*;
import com.movie.backend.entity.User;
import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.TicketService;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = TicketRestController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
@Slf4j
public class TicketControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;



    private TicketDTO ticketDTO;

    @BeforeEach
    void setUp() {
        ticketDTO = new TicketDTO();
        ticketDTO.setId(1L);
        ticketDTO.setTotalAmount(100L);
        ticketDTO.setSeats("A1");
    }

    @Test
    void testCreateTicket() throws Exception {
        doNothing().when(ticketService).saveTicket(any(TicketDTO.class));

        mockMvc.perform(post("/api/v1/ticket/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(ticketService, times(1)).saveTicket(any(TicketDTO.class));
    }

    @Test
    void testFindByUserId() throws Exception {
        List<TicketDTO> tickets = Collections.singletonList(ticketDTO);
        when(ticketService.findByUserId(1L)).thenReturn(tickets);

        mockMvc.perform(get("/api/v1/ticket/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(ticketService, times(1)).findByUserId(1L);
    }

    @Test
    void testFindById() throws Exception {
        when(ticketService.findById(1L)).thenReturn(ticketDTO);

        mockMvc.perform(get("/api/v1/ticket/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalAmount").value(100))
                .andExpect(jsonPath("$.seats").value("A1"));

        verify(ticketService, times(1)).findById(1L);
    }



}
