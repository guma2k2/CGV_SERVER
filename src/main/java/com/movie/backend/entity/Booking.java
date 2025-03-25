package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    @JsonFormat(pattern = "dd/mm/yyyy hh:mm")
    private LocalDateTime created_time = LocalDateTime.now();
    private Long total_amount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String seats;

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private List<BookingSeat> bookingSeats = new ArrayList<>();

    @OneToMany(mappedBy = "booking", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private List<BookingCombo> bookingCombos = new ArrayList<>();

}