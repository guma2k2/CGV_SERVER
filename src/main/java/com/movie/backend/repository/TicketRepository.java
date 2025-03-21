package com.movie.backend.repository;

import com.movie.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query("""
    SELECT t 
    FROM Ticket t 
    INNER JOIN t.booking b 
    INNER JOIN b.user u 
    WHERE u.id = :id
    """)
    List<Ticket> findByUser(@Param("id") Long id);

    @Query("SELECT t FROM Ticket t " +
            "INNER JOIN t.booking b " +
            "INNER JOIN b.event e " +
            "WHERE e.id = :id")
    List<Ticket> findByEvent(@Param("id")Long eventId);

    @Query("""
        SELECT t 
        FROM Ticket t 
        join fetch t.booking b
        join fetch b.event e 
        join fetch e.movie m
        WHERE t.createdTime BETWEEN :startDate AND :endDate
    """)
    List<Ticket> findByDateMovie(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate) ;
}
