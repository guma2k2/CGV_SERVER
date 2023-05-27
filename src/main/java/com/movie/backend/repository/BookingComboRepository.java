package com.movie.backend.repository;

import com.movie.backend.entity.BookingCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingComboRepository extends JpaRepository<BookingCombo , Long> {


    @Query("SELECT bc FROM BookingCombo bc " +
            "INNER JOIN bc.booking b " +
            "WHERE b.id = :id")
    public List<BookingCombo> findByBooking(@Param("id")Long bookingId);


    @Query("DELETE FROM BookingCombo bc WHERE bc.booking.id = :id")
    @Modifying
    public void deleteByBooking(@Param("id")Long id);
}
