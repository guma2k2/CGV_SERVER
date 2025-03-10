package com.movie.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.backend.api.RoomRestController;
import com.movie.backend.api.UserRestController;
import com.movie.backend.dto.*;
import com.movie.backend.entity.Room;
import com.movie.backend.entity.User;
import com.movie.backend.security.config.JwtService;
import com.movie.backend.service.RoomService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = RoomRestController.class,
        excludeAutoConfiguration = {
                UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class
        }
)
@Slf4j
public class RoomControllerTest {


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
    private RoomService roomService;

    private RoomDTO roomDTO;
    private Room room;

    @BeforeEach
    void setUp() {
        roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setName("Room A");

        room = new Room();
        room.setId(1L);
        room.setName("Room A");
    }

    @Test
    void testGetRoomById() throws Exception {
        when(roomService.get(1L)).thenReturn(roomDTO);

        mockMvc.perform(get("/api/v1/admin/room/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Room A"));

        verify(roomService, times(1)).get(1L);
    }

    @Test
    void testFindByCinema() throws Exception {
        List<RoomDTO> roomDTOList = Collections.singletonList(roomDTO);
        when(roomService.findByCinemaName("CinemaX")).thenReturn(roomDTOList);

        mockMvc.perform(get("/api/v1/admin/room/cinema/CinemaX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Room A"));

        verify(roomService, times(1)).findByCinemaName("CinemaX");
    }

    @Test
    void testSaveRoom() throws Exception {
        when(roomService.saveRoom(any(RoomDTO.class), isNull())).thenReturn(room);

        mockMvc.perform(post("/api/v1/admin/room/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Room A"));

        verify(roomService, times(1)).saveRoom(any(RoomDTO.class), isNull());
    }

    @Test
    void testUpdateRoom() throws Exception {
        when(roomService.saveRoom(any(RoomDTO.class), eq(1L))).thenReturn(room);

        mockMvc.perform(put("/api/v1/admin/room/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Room A"));

        verify(roomService, times(1)).saveRoom(any(RoomDTO.class), eq(1L));
    }



}
