package com.movie.backend.repository;

import com.movie.backend.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT s FROM Seat s WHERE s.room.id = :id")
    public List<Seat> findByRoom(@Param("id") Long id);


    @Query("SELECT s FROM Seat s WHERE s.room.id = ( SELECT e.room.id FROM Event e WHERE e.id = :id ) ")
    public List<Seat> findByEvent(@Param("id")Long id);


    @Query("SELECT s " +
            "FROM Seat s " +
            "INNER JOIN s.room r " +
            "WHERE s.seat_name IN :seats AND r.id = :roomId")
    public List<Seat> findByNameRoom(@Param("seats")String[] seats ,
                                   @Param("roomId")Long roomId);

    @Query("SELECT s FROM Seat s " +
            "INNER JOIN s.room r " +
            "WHERE s.seat_name = :seatName AND r.id = :roomId ")
    public Seat findByRoomName(@Param("seatName")String seatName ,
                               @Param("roomId")Long roomId) ;
}
