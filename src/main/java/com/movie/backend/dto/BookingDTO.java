package com.movie.backend.dto;

import com.movie.backend.entity.Event;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    private Long id;
    private EventDTO event;

    private UserDTO user;
    private LocalDateTime created_time;
    private Long total_amount;

    private String seats;

    List<ComboDTO> combos;

}
