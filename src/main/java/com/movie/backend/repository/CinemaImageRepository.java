package com.movie.backend.repository;

import com.movie.backend.entity.Cinema;
import com.movie.backend.entity.CinemaImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaImageRepository extends JpaRepository<CinemaImage, Long> {

    @Query("SELECT i FROM CinemaImage i " +
            "INNER JOIN i.cinema c " +
            "WHERE i.name = :name AND c = :cinema")
    public CinemaImage findByNameAndCinema(@Param("name")String name , @Param("cinema")Cinema cinema);
}
