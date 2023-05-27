package com.movie.backend.repository;

import com.movie.backend.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat , Long> {



    @Query("SELECT b FROM BookingSeat b " +
            "INNER JOIN  b.booking bb " +
            "INNER JOIN bb.event e " +
            "WHERE e.id = :eventId AND b.seat.seat_name = :seatName")
    public List<BookingSeat> findBySeatAndEvent(@Param("eventId") Long eventId , @Param("seatName")String seatName) ;



    @Query("DELETE FROM BookingSeat b WHERE b.booking.id = :id")
    @Modifying
    public void deleteByBooking(@Param("id")Long id);

}
