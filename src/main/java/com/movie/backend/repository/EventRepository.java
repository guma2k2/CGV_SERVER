package com.movie.backend.repository;

import com.movie.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {


    @Query("SELECT e FROM Event e " +
            "INNER JOIN e.room r " +
            "INNER JOIN r.cinema c " +
            "WHERE e.start_date = :date AND c.id = :cinema")
    public List<Event> listByDateCinema(@Param("date") LocalDate date,
                                             @Param("cinema") Long cinemaId);

    @Query("SELECT e FROM Event e " +
            "INNER JOIN e.room r " +
            "WHERE r.id = :roomId AND e.start_date = :date ")
    public List<Event> listByDateRoom(@Param("roomId")Long roomId ,
                                      @Param("date") LocalDate date);


    @Query("SELECT e FROM Event e " +
            "INNER JOIN e.room r " +
            "INNER JOIN r.cinema c " +
            "INNER JOIN e.movie m " +
            "INNER JOIN e.subtitleType ee " +
            "WHERE e.start_date = :date " +
            "AND m.id = :movie " +
            "AND c.name = :cinema " +
            "AND ee.name = :type")
    public List<Event> listByDateCinemaMovie( @Param("date") LocalDate date,
                                              @Param("cinema") String cinema_name,
                                              @Param("movie")Long movieId,
                                              @Param("type")String type);




    @Query("SELECT e  FROM Event e " +
            "INNER JOIN e.room r " +
            "INNER JOIN r.cinema c " +
            "INNER JOIN c.city cc " +
            "WHERE cc.name = :city AND e.start_date = :date AND e.movie.id = :movie")
    public List<Event> listSubByDateMovieCity(@Param("date") LocalDate date ,
                                                  @Param("city") String city ,
                                                  @Param("movie") Long movieId );

    @Query("SELECT e " +
            "FROM Event e " +
            "INNER JOIN e.room r " +
            "INNER JOIN r.cinema c " +
            "INNER JOIN c.city cc " +
            "WHERE e.movie.id = :movieId AND e.start_date = :date")
    List<Event> findCitiesByMovieIdAndDate(@Param("movieId") Long movieId, @Param("date") LocalDate date);


    @Query("SELECT e FROM Event e " +
            "JOIN e.room r " +
            "JOIN r.cinema c " +
            "JOIN c.city cc " +
            "JOIN e.subtitleType es " +
            "WHERE e.start_date = :start_date " +
            "AND cc.name = :cityName " +
            "AND e.movie.id = :movieId " +
            "AND es.name = :type")
    List<Event> findByDateAndCity(@Param("start_date") LocalDate startDate ,
                                  @Param("cityName") String cityName ,
                                  @Param("movieId") Long movieId ,
                                  @Param("type")String type) ;



    @Query("""
        select e 
        from Event e 
        join e.movie m 
        where m.id = :movieId
    """)
    List<Event> findByMovie(@Param("movieId") Long movieId);

}
