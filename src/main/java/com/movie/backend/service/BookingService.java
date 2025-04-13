package com.movie.backend.service;

import com.movie.backend.dto.*;
import com.movie.backend.entity.*;
import com.movie.backend.exception.BookingException;
import com.movie.backend.exception.EventValidException;
import com.movie.backend.exception.MovieException;
import com.movie.backend.exception.UserException;
import com.movie.backend.repository.*;
import com.movie.backend.ultity.DateTimeUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class BookingService {

    @Autowired
    private SeatService seatService ;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;
    @Autowired
    private BookingComboRepository bookingComboRepository;

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ModelMapper modelMapper ;
    public Long createBooking(BookingDTO bookingDTO) throws BookingException {

        Long eventId = bookingDTO.getEvent().getId() ;
        String seats = bookingDTO.getSeats() ;
        String[] seatNames = seats.split(", ");

        // check booking with all the name of seat from client which sent the request
        for (String seatName : seatNames) {
            if(seatService.checkPaidBooking(eventId , seatName)) {
                throw new BookingException("Đã có người thanh toan , vui long đặt chỗ khác");
            }
            // handle quick booking than current user
            if(!seatService.checkValidBooking(eventId , seatName)) {
                throw new BookingException("Đã có người nhanh tay hơn bạn, bạn có thể đợi 5p hoặc đặt chỗ khác");
            }
            // handle quick booking than current user
        }
        // find all the entity was reference with booking include: user , event

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventValidException("Id of event not found"));

        User user = userRepository.findById(bookingDTO.getUser().getId()).orElseThrow(() -> new UserException("Id of user not found"));

        // save booking
        Booking booking = Booking.builder()
                .user(user)
                .seats(seats)
                .total_amount(bookingDTO.getTotal_amount())
                .event(event)
                .created_time(LocalDateTime.now())
                .build();

        // handle  seat with  list seat from bookingDTO request
        List<Seat> seatList = seatRepository.findByNameRoom(seatNames,
                event.getRoom().getId() ) ;
        List<BookingSeat> bookingSeats = new ArrayList<>() ;
        seatList.forEach(seat -> {
            BookingSeat bookingSeat = BookingSeat
                    .builder()
                    .booking(booking)
                    .seat(seat)
                    .build();
            bookingSeats.add(bookingSeat);
        });

        // handle all combo with  list combo of bookingDTO from request
        List<ComboDTO> comboOnBooking = bookingDTO.getCombos();

        List<BookingCombo> bookingCombos = new ArrayList<>() ;
        comboOnBooking.forEach(comboDTO -> {
            Combo combo = modelMapper.map(comboDTO, Combo.class);
            BookingCombo bookingCombo = BookingCombo.builder()
                    .booking(booking)
                    .combo(combo)
                    .quantity(comboDTO.getQuantity())
                    .build();
            bookingCombos.add(bookingCombo);
        });

        // save to db
        Booking savedBooking = bookingRepository.save(booking);
        bookingSeatRepository.saveAll(bookingSeats);
        bookingComboRepository.saveAll(bookingCombos);
        return savedBooking.getId();
    }

    // Clean up all booking request which was not click cancel booking (out of page)
    // This schedule run every 0.1s
    @Scheduled(fixedDelay = 100)
    public void scheduleFixedBookingRequest() {
        List<Booking> bookings = bookingRepository.findAllBookingByDay();
        LocalDateTime now = LocalDateTime.now();
        bookings.forEach(booking -> {
            LocalDateTime createdTimeOfBooking = booking.getCreated_time();
            Duration duration = Duration.between(createdTimeOfBooking, now);
            Long minutes = duration.toMinutes();
            if (minutes >= 5) {
                log.info("delete booking with id : {}" , booking.getId());
                deleteByBookingId(booking.getId());
            }
        });
    }

    public void deleteByBookingId(Long bookingId) {
        if(bookingId == null) {
            throw new BookingException("Cant get the id of booking");
        }
        bookingSeatRepository.deleteByBooking(bookingId);
        bookingComboRepository.deleteByBooking(bookingId);
        bookingRepository.deleteBookingById(bookingId);
    }

    public DataContent findAll(Integer pageNum) {
        Sort sort = Sort.by("id").descending() ;
        Pageable pageable = PageRequest.of(pageNum - 1 , 5  , sort );
        Page<Booking> pages = bookingRepository.findAll(pageable) ;
        int total = pages.getTotalPages();
        int totalElements = (int) pages.getTotalElements();

        List<BookingDTO> bookingDTOS = pages.getContent()
                .stream()
                .map(booking -> {
                    BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
                    EventDTO eventDTO = modelMapper.map(booking.getEvent(), EventDTO.class);
                    List<ComboDTO> comboDTOS = bookingComboRepository.findByBooking(booking.getId()).stream().map(bookingCombo -> modelMapper.map(bookingCombo.getCombo(), ComboDTO.class)).toList();
                    bookingDTO.setEvent(eventDTO);
                    bookingDTO.setCombos(comboDTOS);
                    bookingDTO.setCreatedTimeFormatted(DateTimeUtil.formatLocalDateTimeBooking(booking.getCreated_time()));
                    return bookingDTO;
                })
                .collect(Collectors.toList());
        Paginate paginate = Paginate.builder()
                .currentPage(pageNum)
                .sortDir("desc")
                .sortField("id")
                .totalPage(total)
                .totalElements(totalElements)
                .sizePage(5)
                .build();
        return new DataContent(paginate , bookingDTOS);
    }

    public BookingDTO get(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
        EventDTO eventDTO = modelMapper.map(booking.getEvent(), EventDTO.class);
        List<ComboDTO> comboDTOS = bookingComboRepository.findByBooking(booking.getId()).stream().map(bookingCombo -> modelMapper.map(bookingCombo.getCombo(), ComboDTO.class)).toList();
        bookingDTO.setEvent(eventDTO);
        bookingDTO.setCombos(comboDTOS);
        bookingDTO.setCreatedTimeFormatted(DateTimeUtil.formatLocalDateTimeBooking(booking.getCreated_time()));
        return bookingDTO;
    }
}
