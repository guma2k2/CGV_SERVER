package com.movie.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketDTO {
    private Long id ;
    private String qrCode ;
    private String banking;
    private String stk;
    private EventDTO event;
    private List<BookingComboDTO> combos;
    private Long bookingId;
    private Long userId;
    private String seats ;
    private String phoneNumber;
    private String bank;
    private Long totalAmount ;
    private String createdAt;

}
