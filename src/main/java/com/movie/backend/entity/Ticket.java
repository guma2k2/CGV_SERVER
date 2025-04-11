package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String qrCode ;

    private String bank;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Booking booking ;

    @Column(name = "create_time")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdTime;


    @Transient
    public Event getEvent() {
        return booking.getEvent();
    }

    @Transient
    public String getSeats() {
        return booking.getSeats();
    }

    @Transient
    public Long getTotalAmount() {
        return booking.getTotal_amount();
    }

    @PrePersist
    public void prePersist() {
        createdTime = LocalDateTime.now();
    }




}
