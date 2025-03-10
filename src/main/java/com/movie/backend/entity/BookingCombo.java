package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "booking_combo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingCombo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "combo_id")
    private Combo combo;

    private int quantity ;

    @Transient
    public Integer getSubTotal() {
        return quantity*combo.getPrice();
    }
}
