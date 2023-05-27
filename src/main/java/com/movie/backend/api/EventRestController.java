package com.movie.backend.api;

import com.movie.backend.dto.EventDTO;
import com.movie.backend.dto.MovieDTO;
import com.movie.backend.dto.SubtitleTypeDTO;
import com.movie.backend.entity.Event;
import com.movie.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class EventRestController {

    @Autowired
    private EventService eventService ;


    @GetMapping("/admin/event/find/{roomId}/{date}")
    public List<EventDTO> findByRoomDate(@PathVariable("roomId")Long roomId,
                                         @PathVariable("date") LocalDate date) {
        return eventService.findRoomDate(roomId, date) ;
    }

    @GetMapping("/admin/event/subtype")
    public List<SubtitleTypeDTO> listAll() {
        return eventService.listAllSubType() ;
    }

    @DeleteMapping("/admin/event/delete/{eventId}")
    public void deleteById(@PathVariable("eventId")Long eventId) {
        eventService.deleteEvent(eventId);
    }

    @PostMapping(value = "/admin/event/save")
    public Event saveEvent(@RequestBody EventDTO event) {
       return eventService.saveEvent(event , null);
    }

    @PutMapping(value = "/admin/event/update/{eventId}")
    public Event updateEvent(@RequestBody EventDTO event , @PathVariable("eventId") Long eventId) {
        return eventService.saveEvent(event , eventId);
    }

    @GetMapping("/movies/events/{date}/{cinema}/{movie}/{subType}")
    public List<EventDTO> listByDateCinemaMovie(@PathVariable("date")LocalDate date ,
                                                @PathVariable("cinema") String cinemaName ,
                                                @PathVariable("movie")Long movieId ,
                                                @PathVariable("subType") String type ) {
        return eventService.findByDateMovieCinema(date, movieId , cinemaName, type) ;
    }

    @GetMapping("/movies/events/booking/{id}")
    public EventDTO get(@PathVariable("id") Long id)  {
            return eventService.getById(id) ;

    }


    @GetMapping("/movies/events/sub/{date}/{city}/{movie}")
    public List<SubtitleTypeDTO> findByDateCityMovie(
            @PathVariable("date") LocalDate date ,
            @PathVariable("city") String cityName ,
            @PathVariable("movie") Long movieId
    ) {
        return eventService.findByMovieDateCity(movieId, date, cityName);
    }

    @GetMapping("/movies/events/cinemas/{cinemaId}/{date}")
    public Map<MovieDTO, Map<SubtitleTypeDTO , List<EventDTO>>> findByCinemaDate(
            @PathVariable("cinemaId") Long cinemaId,
            @PathVariable("date") LocalDate date
    ) {
        return eventService.findByCinemaAndDate(cinemaId , date);
    }

}
