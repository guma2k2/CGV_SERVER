package com.movie.backend.service;


import com.movie.backend.dto.CinemaDTO;
import com.movie.backend.dto.RoomDTO;
import com.movie.backend.entity.Cinema;
import com.movie.backend.entity.Room;
import com.movie.backend.exception.RoomException;
import com.movie.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {


    @Mock
    private RoomRepository roomRepository;

    @Value("${application.service.room.roomPerPage}")
    public int roomPerPage ;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoomService roomService;


    private Room room;
    private RoomDTO roomDTO;
    private Cinema cinema;
    private CinemaDTO cinemaDTO;

    @BeforeEach
    void setUp() {
        cinema = new Cinema();
        cinema.setId(1L);
        cinema.setName("CinemaX");

        cinemaDTO = new CinemaDTO();
        cinemaDTO.setName("CinemaX");

        room = new Room();
        room.setId(1L);
        room.setName("Room1");
        room.setCapacity("10x10");
        room.setCinema(cinema);

        roomDTO = new RoomDTO();
        roomDTO.setId(1L);
        roomDTO.setName("Room1");
        roomDTO.setCapacity("10x10");
        roomDTO.setCinema(cinemaDTO);
    }

    @Test
    void testSaveRoom_NewRoom() {
        when(roomRepository.findByCinemaAndName(anyString(), anyString())).thenReturn(null);
        when(modelMapper.map(any(), eq(Cinema.class))).thenReturn(cinema);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room savedRoom = roomService.saveRoom(roomDTO, null);

        assertNotNull(savedRoom);
        assertEquals("Room1", savedRoom.getName());
    }

    @Test
    void testSaveRoom_ExistingRoom() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.findByCinemaAndName(anyString(), anyString())).thenReturn(null);
        when(modelMapper.map(any(), eq(Cinema.class))).thenReturn(cinema);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        Room updatedRoom = roomService.saveRoom(roomDTO, 1L);

        assertNotNull(updatedRoom);
        assertEquals("Room1", updatedRoom.getName());
    }

    @Test
    void testSaveRoom_NameExists_ShouldThrowException() {
        when(roomRepository.findByCinemaAndName(anyString(), anyString())).thenReturn(room);

        RoomException exception = assertThrows(RoomException.class, () -> roomService.saveRoom(roomDTO, null));
        assertEquals("Name not valid", exception.getMessage());
    }

    @Test
    void testFindByCinemaName() {
        when(roomRepository.findByCinema("CinemaX")).thenReturn(Arrays.asList(room));
        when(modelMapper.map(room, RoomDTO.class)).thenReturn(roomDTO);

        List<RoomDTO> rooms = roomService.findByCinemaName("CinemaX");

        assertEquals(1, rooms.size());
        assertEquals("Room1", rooms.get(0).getName());
    }

    @Test
    void testGetRoom_RoomExists() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(modelMapper.map(room, RoomDTO.class)).thenReturn(roomDTO);

        RoomDTO foundRoom = roomService.get(1L);

        assertNotNull(foundRoom);
        assertEquals("Room1", foundRoom.getName());
    }

    @Test
    void testGetRoom_RoomNotFound_ShouldThrowException() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        RoomException exception = assertThrows(RoomException.class, () -> roomService.get(1L));
        assertEquals("Room not found", exception.getMessage());
    }
}
