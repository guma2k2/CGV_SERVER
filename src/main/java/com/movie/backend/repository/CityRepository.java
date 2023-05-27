package com.movie.backend.repository;

import com.movie.backend.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface CityRepository extends JpaRepository<City,Integer> {

    @Query("SELECT c FROM City c WHERE c.name = :name")
    public List<City> findByName(@Param("name") String name);


//    @Query("SELECT DISTINCT new com.movie.backend.entity.City(c.id , c.name) " +
//            "FROM City c " +
//            "JOIN c.events e " +
//            "WHERE e.movie.id = :movieId " +
//            "AND e.start_date = :startDate")
//    public List<City> findByMovieAndDate(@Param("movieId") Long movieId ,
//                                         @Param("startDate")LocalDate startDate);

}
