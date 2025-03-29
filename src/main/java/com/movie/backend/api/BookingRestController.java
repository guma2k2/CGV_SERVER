package com.movie.backend.api;


import com.movie.backend.dto.BookingDTO;
import com.movie.backend.dto.DataContent;
import com.movie.backend.exception.BookingException;
import com.movie.backend.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@Slf4j
public class BookingRestController {

    @Autowired
    private BookingService bookingService ;
    @PostMapping("/create/booking")
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            Long bookingId = bookingService.createBooking(bookingDTO);
            return ResponseEntity.ok().body(bookingId);
        } catch (BookingException e) {
//            log.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/booking/{id}")
    public void deleteById(@PathVariable("id")Long id) {
        bookingService.deleteByBookingId(id);
    }
    @GetMapping("/admin/bookings/paginate")
    public DataContent get(@RequestParam(value = "pageNum", required = false, defaultValue = "1")Integer pageNum) {
        DataContent dataContent = bookingService.findAll(pageNum);
        return dataContent;
    }

    @GetMapping("/admin/bookings/{bookingId}")
    public BookingDTO get(@PathVariable("bookingId") Long bookingId) {
        BookingDTO bookingDTO = bookingService.get(bookingId);
        return bookingDTO;
    }
}
