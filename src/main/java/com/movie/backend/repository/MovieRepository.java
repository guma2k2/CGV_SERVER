package com.movie.backend.repository;

import com.movie.backend.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:title% AND m.isShowing = true")
    public List<Movie> findByTitle(@Param("title") String title);

    @Query("SELECT m FROM Movie m WHERE m.release_date <= '2023-04-21' AND m.isShowing = true")
    public List<Movie> findBeforeDate() ; // movie is showing now

    @Query("SELECT m FROM Movie m WHERE m.release_date > '2023-04-21' AND m.isShowing = true")
    public List<Movie> findAfterDate(); // movie will show soon


    @Query("SELECT m FROM Movie m WHERE m.title LIKE %:title% ")
    public Page<Movie> findAll(@Param("title")String title, Pageable pageable) ;

    @Query("SELECT m FROM Movie m WHERE m.title = :title")
    public Movie getByTitle(@Param("title")String title) ;
    @Modifying
    @Query("UPDATE Movie m SET m.isShowing = :status WHERE m.id = :movieId")
    public void updateStatus(@Param("movieId") Long movieId , @Param("status") boolean status) ;
}
