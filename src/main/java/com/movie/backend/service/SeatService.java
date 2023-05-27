package com.movie.backend.service;

import com.movie.backend.dto.SeatDTO;
import com.movie.backend.entity.Booking;
import com.movie.backend.entity.Seat;
import com.movie.backend.entity.Ticket;
import com.movie.backend.exception.SeatException;
import com.movie.backend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SeatService {

    @Autowired
    private BookingSeatRepository bookingSeatRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketRepository ticketRepository ;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper ;

    public List<SeatDTO> findByEvent(Long eventId) {
        // get List seat by event with seat reserved is false // default
        List<Seat> listSeatByEvent = seatRepository.findByEvent(eventId);

        // get list ticket by event
        List<Booking> listBookingByEvent = bookingRepository.listBookingByEvent(eventId);

        // get list name of seat in ticket
        List<Ticket> listTicketByEvent = ticketRepository.findByEvent(eventId) ;

        // get list name of seat in ticket
        List<String> listNameSeatIsPaid = listTicketByEvent
                .stream()
                .map(ticket -> ticket.getSeats())
                .collect(Collectors.toList());

        // get list name of seat in booking
        List<String> listNameSeatIsReserved = listBookingByEvent
                .stream()
                .map(booking -> booking.getSeats())
                .collect(Collectors.toList());


        // convert list seat name to string array and action with seatRepository
        String[] seatNameIsReserved = listNameSeatIsReserved.stream()
                .flatMap(s -> Arrays.stream(s.split(",\\s*")))
                .toArray(String[]::new);


        String[] seatNameIsPaid = listNameSeatIsPaid.stream()
                .flatMap(s -> Arrays.stream(s.split(",\\s*")))
                .toArray(String[]::new);

        Long roomId = eventRepository.findById(eventId).get().getRoom().getId();
//        log.info(String.valueOf(roomId));
        List<Seat> listSeatByEventIsReserved = seatRepository.findByNameRoom(seatNameIsReserved,roomId);
        List<Seat> listSeatByEventIsPaid = seatRepository.findByNameRoom(seatNameIsPaid,roomId);

//        listSeatByEventIsReserved.forEach(seat -> log.info(seat.getSeat_name()));

        // convert to list seatDTO
        List<SeatDTO> listSeatDTO = listSeatByEvent.stream().map(seat -> {
            SeatDTO seatDTO = modelMapper.map(seat,SeatDTO.class);
            if (listSeatByEventIsPaid.stream()
                    .map(seatDto -> seatDto.getId())
                    .collect(Collectors.toList())
                    .contains(seat.getId())){
//                log.info(seat.getSeat_name());
                seatDTO.setPaid(true);
                seatDTO.setReserved(false);

            } else if (listSeatByEventIsReserved
                    .stream()
                    .map(seatDto -> seatDto.getId())
                    .collect(Collectors.toList())
                    .contains(seat.getId())){
//                log.info(seat.getSeat_name());
                seatDTO.setReserved(true);
                seatDTO.setPaid(false);
            }

            return seatDTO;
        }).collect(Collectors.toList());
        return listSeatDTO ;
    }

    public boolean checkValidBooking(Long eventId , String seatName) {
        return bookingSeatRepository.findBySeatAndEvent(eventId, seatName).isEmpty();
    }


    public boolean checkPaidBooking(Long eventId, String seatName) {

        List<Ticket> tickets = ticketRepository.findByEvent(eventId) ;

        List<String> listNameSeatIsPaid = tickets
                .stream()
                .map(ticket -> ticket.getSeats())
                .collect(Collectors.toList());
        // convert List of String to String array
        String[] seatNameIsPaid = listNameSeatIsPaid.stream()
                .flatMap(s -> Arrays.stream(s.split(",\\s*")))
                .toArray(String[]::new);
        Long roomId = eventRepository.findById(eventId).get().getRoom().getId();

        List<Seat> listSeatByEventIsPaid = seatRepository.findByNameRoom(seatNameIsPaid,roomId);
        for(Seat seat : listSeatByEventIsPaid) {
           if(seat.getSeat_name().equals(seatName)) {
               return true;
           }
        }
       return false;
    }

    public List<SeatDTO> findAll() {
        return seatRepository.findAll().stream()
                .map(seat -> modelMapper.map(seat, SeatDTO.class))
                .collect(Collectors.toList());
    }

    public Seat save(Seat seat) {
        return seatRepository.save(seat) ;
    }
    public boolean checkExitNameInRoom(String name , Long roomId) {
        if(seatRepository.findByRoomName(name , roomId) != null) {
            throw new SeatException("The name of this seat was exited") ;
        }
        return true ;
    }

    public Seat get(Long id) {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new SeatException("Seat not found"));
        return seat;
    }
    public void delete(Long seatId) {
        Seat seat = get(seatId) ;
        seatRepository.delete(seat);
    }
}
