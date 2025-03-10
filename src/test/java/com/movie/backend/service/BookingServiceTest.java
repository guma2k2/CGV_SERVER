package com.movie.backend.service;


import com.movie.backend.dto.BookingComboDTO;
import com.movie.backend.dto.TicketDTO;
import com.movie.backend.entity.Booking;
import com.movie.backend.entity.BookingCombo;
import com.movie.backend.entity.Ticket;
import com.movie.backend.exception.BookingException;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private SeatService seatService ;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private BookingSeatRepository bookingSeatRepository;
    @Mock
    private BookingComboRepository bookingComboRepository;

    @Mock
    private UserRepository userRepository ;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ModelMapper modelMapper ;


    @InjectMocks
    private BookingService bookingService;

    @Test
    void testDeleteByBookingId_ValidId() {
        Long bookingId = 1L;

        doNothing().when(bookingSeatRepository).deleteByBooking(bookingId);
        doNothing().when(bookingComboRepository).deleteByBooking(bookingId);
        doNothing().when(bookingRepository).deleteBookingById(bookingId);

        bookingService.deleteByBookingId(bookingId);

        verify(bookingSeatRepository, times(1)).deleteByBooking(bookingId);
        verify(bookingComboRepository, times(1)).deleteByBooking(bookingId);
        verify(bookingRepository, times(1)).deleteBookingById(bookingId);
    }

    @Test
    void testDeleteByBookingId_NullId_ShouldThrowException() {
        BookingException exception = assertThrows(BookingException.class, () -> bookingService.deleteByBookingId(null));
        assertEquals("Cant get the id of booking", exception.getMessage());
    }
}
