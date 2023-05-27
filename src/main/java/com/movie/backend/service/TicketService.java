package com.movie.backend.service;

import com.movie.backend.dto.*;
import com.movie.backend.entity.*;
import com.movie.backend.repository.BookingComboRepository;
import com.movie.backend.repository.BookingRepository;
import com.movie.backend.repository.TicketRepository;
import com.movie.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TicketService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CinemaService cinemaService ;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookingComboRepository bookingComboRepository;
    public List<TicketDTO> findByUserId(Long userId) {
        List<Ticket> tickets = ticketRepository.findByUser(userId);
        List<TicketDTO> ticketDTOS = tickets.stream().map(ticket -> {
            TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
            Long bookingId = ticket.getBooking().getId();
            ticketDTO.setBookingId(bookingId);
            ticketDTO.setUserId(userId);
            List<BookingComboDTO> comboDTOS = bookingComboRepository.findByBooking(bookingId)
                    .stream()
                    .map(bookingCombo ->  modelMapper.map(bookingCombo, BookingComboDTO.class))
                    .collect(Collectors.toList());;
            ticketDTO.setCombos(comboDTOS);
            return ticketDTO ;
        }).collect(Collectors.toList());
        return ticketDTOS;
    }

    public void saveTicket(TicketDTO ticketDTO) {
        Long bookingId = ticketDTO.getBookingId();
        Long userId = ticketDTO.getUserId();
        String qrCode = ticketDTO.getQrCode();
        User user = userRepository.findById(userId).orElseThrow() ;
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Ticket ticket = Ticket.builder()
                .booking(booking)
                .user(user)
                .qrCode(qrCode)
                .build();
        ticketRepository.save(ticket);
    }

    public TicketDTO findById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).get();
        TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
        Long bookingId = ticket.getBooking().getId();
        List<BookingComboDTO> comboDTOS = bookingComboRepository.findByBooking(bookingId)
                .stream()
                .map(bookingCombo ->  modelMapper.map(bookingCombo, BookingComboDTO.class))
                .collect(Collectors.toList());;
        ticketDTO.setCombos(comboDTOS);
        return ticketDTO ;
    }
    public List<SalesByCinema> report(LocalDate startDate, LocalDate endDate, Long cinemaId) {
        // convert localDate to localDateTime. Because the field `createdTime` in Ticket(Table) is localDateTime
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atStartOfDay();

        // get Cinema by id
        CinemaDTO cinema = cinemaService.get(cinemaId);

        // get list ticket from startDate to endDate
        List<Ticket> tickets = ticketRepository.findByDateMovie(startDateTime, endDateTime)
                .stream()
                .filter(ticket -> ticket.getBooking().getEvent().getRoom().getCinema().getId().equals(cinemaId))
                .collect(Collectors.toList());

        // Because you want to get report by `day`, but in one day, the cinema can have many tickets
        // So you need to map to a day with sum of price per ticket
        Map<LocalDate, Long> revenueByDate = tickets.stream()
                .filter(ticket -> ticket.getCreatedTime().isAfter(startDateTime.minusDays(1))
                        && ticket.getCreatedTime().isBefore(endDateTime.plusDays(1)))
                .collect(Collectors
                        .groupingBy(ticket -> ticket.getCreatedTime().toLocalDate(),
                                Collectors.summingLong(ticket -> ticket.getBooking().getTotal_amount())));
        List<SalesByCinema> sales = new ArrayList<>();

        // when you use googleChart, in case of the request with `startDate and endDate`
        // but in one day of these request and ticket was not have this day so the chat will  not have one of day
        // in request
        // So you need to add all day in range of request in response
        while (!startDate.isAfter(endDate)) {
            // Do something with the current date, such as print it
            sales.add(new SalesByCinema(startDate));
            // Increment the current date by one day
            startDate = startDate.plusDays(1);
        }
        // get the revenue in range of request `startDate and endDate`
        for (Map.Entry<LocalDate, Long> entry : revenueByDate.entrySet()) {
            LocalDate date = entry.getKey();
            Long total = entry.getValue();
            SalesByCinema sale = new SalesByCinema(date);
            int itemIndex = sales.indexOf(sale);
            if(itemIndex >= 0) {
                SalesByCinema salesByCinema = sales.get(itemIndex) ;
                salesByCinema.setCinemaId(cinema.getId());
                salesByCinema.setCinemaName(cinema.getName());
                salesByCinema.setDate(date);
                salesByCinema.setTotalAmount(total);
            }
        }
        return sales;
    }

}
