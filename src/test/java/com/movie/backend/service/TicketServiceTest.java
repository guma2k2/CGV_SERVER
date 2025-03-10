package com.movie.backend.service;


import com.movie.backend.dto.BookingComboDTO;
import com.movie.backend.dto.TicketDTO;
import com.movie.backend.entity.*;
import com.movie.backend.repository.*;
import com.movie.backend.ultity.VNPayConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private VNPayConfig vnPayConfig;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private CinemaService cinemaService ;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BookingComboRepository bookingComboRepository;

    @InjectMocks
    private TicketService ticketService;


    private Ticket ticket;
    private TicketDTO ticketDTO;
    private Booking booking;
    private BookingCombo bookingCombo;
    private BookingComboDTO bookingComboDTO;

    @BeforeEach
    void setUp() {
        // Create Booking
        booking = new Booking();
        booking.setId(1L);

        // Create TicketDTO
        ticketDTO = new TicketDTO();
        ticketDTO.setBookingId(1L);
        ticketDTO.setBank("Test Bank");

        // Create Ticket
        ticket = Ticket.builder()
                .id(1L)
                .bank("Test Bank")
                .qrCode("RANDOM_CODE_123")
                .booking(booking)
                .build();

        // Create BookingCombo
        bookingCombo = new BookingCombo();
        bookingCombo.setId(1L);

        // Create BookingComboDTO
        bookingComboDTO = new BookingComboDTO();
        bookingComboDTO.setId(1L);
    }

    @Test
    void saveTicket_ShouldSaveTicket_WhenValidDataIsProvided() {
        // Given
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // When
        ticketService.saveTicket(ticketDTO);

        // Then
        verify(bookingRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void saveTicket_ShouldThrowException_WhenBookingNotFound() {
        // Given
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> ticketService.saveTicket(ticketDTO));

        verify(bookingRepository, times(1)).findById(1L);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void findById_ShouldReturnTicketDTO_WhenTicketExists() {
        // Given
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(modelMapper.map(ticket, TicketDTO.class)).thenReturn(ticketDTO);
        when(bookingComboRepository.findByBooking(1L)).thenReturn(List.of(bookingCombo));
        when(modelMapper.map(bookingCombo, BookingComboDTO.class)).thenReturn(bookingComboDTO);

        // When
        TicketDTO result = ticketService.findById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getBookingId());
        assertEquals("Test Bank", result.getBank());
        assertEquals(1, result.getCombos().size());

        verify(ticketRepository, times(1)).findById(1L);
        verify(bookingComboRepository, times(1)).findByBooking(1L);
        verify(modelMapper, times(1)).map(ticket, TicketDTO.class);
        verify(modelMapper, times(1)).map(bookingCombo, BookingComboDTO.class);
    }

    @Test
    void findById_ShouldThrowException_WhenTicketNotFound() {
        // Given
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> ticketService.findById(1L));

        verify(ticketRepository, times(1)).findById(1L);
        verify(bookingComboRepository, never()).findByBooking(any());
        verify(modelMapper, never()).map(any(), any());
    }
}
