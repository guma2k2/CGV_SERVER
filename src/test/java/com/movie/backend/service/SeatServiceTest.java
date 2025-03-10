package com.movie.backend.service;


import com.movie.backend.dto.BookingComboDTO;
import com.movie.backend.dto.SeatDTO;
import com.movie.backend.dto.TicketDTO;
import com.movie.backend.entity.*;
import com.movie.backend.exception.SeatException;
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

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private BookingSeatRepository bookingSeatRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private SeatRepository seatRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TicketRepository ticketRepository ;

    @Mock
    private EventRepository eventRepository;
    @Mock
    private ModelMapper modelMapper ;


    @InjectMocks
    private SeatService seatService;

    private Seat seat;
    private SeatDTO seatDTO;
    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setId(1L);

        seat = new Seat();
        seat.setId(1L);
        seat.setSeat_name("A1");
        seat.setRow_num(1);
        seat.setColumn_num(1);
        seat.setRoom(room);
        seat.setType(SeatType.NORMAL);

        seatDTO = new SeatDTO();
        seatDTO.setId(1L);
        seatDTO.setSeat_name("A1");
        seatDTO.setRow_num(1);
        seatDTO.setColumn_num(1);
        seatDTO.setRoomId(1L);
    }

    @Test
    void testFindAll() {
        when(seatRepository.findAll()).thenReturn(Arrays.asList(seat));
        when(modelMapper.map(seat, SeatDTO.class)).thenReturn(seatDTO);

        List<SeatDTO> result = seatService.findAll();

        assertEquals(1, result.size());
        assertEquals("A1", result.get(0).getSeat_name());
    }

    @Test
    void testSave_NewSeat() {
        when(seatRepository.findByRoomName(anyString(), anyLong())).thenReturn(null);
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        when(modelMapper.map(any(), eq(SeatType.class))).thenReturn(SeatType.NORMAL);
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);

        Seat savedSeat = seatService.save(seatDTO, null);

        assertNotNull(savedSeat);
        assertEquals("A1", savedSeat.getSeat_name());
    }

    @Test
    void testSave_ExistingSeat() {
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));
        when(seatRepository.findByRoomName(anyString(), anyLong())).thenReturn(null);
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        when(modelMapper.map(any(), eq(SeatType.class))).thenReturn(SeatType.NORMAL);
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);

        Seat updatedSeat = seatService.save(seatDTO, 1L);

        assertNotNull(updatedSeat);
        assertEquals("A1", updatedSeat.getSeat_name());
    }

    @Test
    void testSave_SeatNameExists_ShouldThrowException() {
        when(seatRepository.findByRoomName(anyString(), anyLong())).thenReturn(seat);

        SeatException exception = assertThrows(SeatException.class, () -> seatService.save(seatDTO, null));
        assertEquals("The name of this seat was exited", exception.getMessage());
    }

    @Test
    void testGet_SeatExists() {
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        Seat foundSeat = seatService.get(1L);

        assertNotNull(foundSeat);
        assertEquals("A1", foundSeat.getSeat_name());
    }

    @Test
    void testGet_SeatNotFound_ShouldThrowException() {
        when(seatRepository.findById(1L)).thenReturn(Optional.empty());

        SeatException exception = assertThrows(SeatException.class, () -> seatService.get(1L));
        assertEquals("Seat not found", exception.getMessage());
    }

    @Test
    void testDelete() {
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));
        doNothing().when(seatRepository).delete(seat);

        seatService.delete(1L);

        verify(seatRepository, times(1)).delete(seat);
    }
}
