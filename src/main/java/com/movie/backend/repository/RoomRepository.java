package com.movie.backend.repository;

import com.movie.backend.entity.Cinema;
import com.movie.backend.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {


    @Query("SELECT r FROM Room r " +
            "INNER JOIN r.cinema c " +
            "WHERE c.name = :cinema")
    public List<Room> findByCinema(@Param("cinema") String cinema );

    @Query("SELECT r FROM Room r " +
            "INNER JOIN r.cinema c " +
            "WHERE c.name = :cinema AND r.name = :roomName")
    public Room findByCinemaAndName(@Param("cinema") String cinema,
                                    @Param("roomName")String roomName );

    public Page<Room> findAll(Pageable pageable) ;

    @Query("""
        delete 
        from Room c 
        where c.id = :roomId
    """)
    @Modifying
    void deleteById(@Param("roomId") Long roomId);

}
