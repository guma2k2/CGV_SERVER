package com.movie.backend.repository;

import com.movie.backend.entity.Booking;
import com.movie.backend.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository  extends JpaRepository<Booking,Long> {

    @Query("SELECT b FROM Booking b " +
            "INNER JOIN b.event e " +
            "WHERE e.id = :eventId")
    List<Booking> listBookingByEvent(@Param("eventId")Long eventId);

    @Modifying
    @Query("DELETE FROM Booking b WHERE b.id = :id")
    void deleteBookingById(@Param("id") Long bookingId);

    @Query("SELECT b FROM Booking b WHERE DAY(b.created_time) = DAY(CURRENT_DATE) " +
            "AND b.id NOT IN (SELECT t.booking.id FROM Ticket t)")
    List<Booking> findAllBookingByDay() ;

    @Modifying
    @Query("update Booking b set b.status = :status WHERE b.id = :id")
    void updateBookingStatus(@Param("id") Long bookingId, @Param("status")BookingStatus status);

//    @Query("""
//        select b
//        from Booking b
//    """)
//    Page<Booking> findAllCustom(Pageable pageable);
}
