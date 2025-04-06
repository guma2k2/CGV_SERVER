package com.movie.backend.repository;

import com.movie.backend.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CinemaRepository  extends JpaRepository<Cinema, Long> {

    public Cinema findByName(String name);

    @Query("SELECT c FROM Cinema c " +
            "INNER JOIN c.city cc " +
            "WHERE cc.id = :cityId")
    public List<Cinema> findByCity(@Param("cityId") Integer id);

}
