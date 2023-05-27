package com.movie.backend.service;

import com.movie.backend.dto.*;
import com.movie.backend.entity.Event;
import com.movie.backend.entity.Movie;
import com.movie.backend.entity.Room;
import com.movie.backend.entity.SubtitleType;
import com.movie.backend.exception.EventValidException;
import com.movie.backend.repository.EventRepository;
import com.movie.backend.repository.MovieRepository;
import com.movie.backend.repository.RoomRepository;
import com.movie.backend.repository.SubTitleTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeatService seatService ;

    @Autowired
    private   MovieRepository movieRepository ;
    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private RoomRepository roomRepository ;
    @Autowired
    private SubTitleTypeRepository subTitleTypeRepository;

    public List<SubtitleTypeDTO> listAllSubType() {
        return subTitleTypeRepository
                .findAll()
                .stream()
                .map(subtitleType -> modelMapper.map(subtitleType , SubtitleTypeDTO.class))
                .collect(Collectors.toList());
    }

    public Event saveEvent(EventDTO event , Long eventId) {
        Long roomId = event.getRoom().getId() ;
        Movie movie = movieRepository.findById(event.getMovie().getId()).get();
        LocalDate startDate = event.getStart_date() ;
        List<Event> events = eventRepository.listByDateRoom(roomId , startDate);
        Room room = roomRepository.findById(event.getRoom().getId()).get();
        SubtitleType subtitleType = subTitleTypeRepository.findById(event.getSubtitleType().getId()).get() ;

        LocalDateTime newStartTime = convertStringToLocalDateTime(event.getStart_time() , event.getStart_date());

        int duration = movie.getDuration_minutes();
        LocalDateTime newEndTime = newStartTime.plusMinutes(duration) ;

        boolean updateEvent = eventId != null ;

        for(Event e : events) {
            LocalDateTime oldStartTime = convertStringToLocalDateTime(e.getStart_time() , e.getStart_date()) ;
            int oldDuration = e.getMovie().getDuration_minutes();
            LocalDateTime oldEndTime = oldStartTime.plusMinutes(oldDuration) ;
            if (updateEvent) {
                if((eventId != e.getId()) && !(newStartTime.isBefore(oldStartTime) && newEndTime.isBefore(oldStartTime)
                        || (newStartTime.isAfter(oldEndTime) && newEndTime.isAfter(oldEndTime))) ) {
                    log.error("err1");
                    throw  new EventValidException("Thời gian bắt đầu đã bị trùng với event với id :" + e.getId()) ;
                }
            }
            else if (!(newStartTime.isBefore(oldStartTime) && newEndTime.isBefore(oldStartTime)
                        || (newStartTime.isAfter(oldEndTime) && newEndTime.isAfter(oldEndTime)))) {
                log.error("err");
                throw  new EventValidException("Thời gian bắt đầu đã bị trùng với event với id :" + e.getId()) ;
            }
        }
        if(updateEvent) {
            Event oldEvent = eventRepository.findById(eventId).orElseThrow(() -> new EventValidException("Event not found with id :" + eventId)) ;
            oldEvent.setMovie(movie);
            oldEvent.setSubtitleType(subtitleType);
            oldEvent.setRoom(room);
            oldEvent.setStart_date(startDate);
            oldEvent.setStart_time(event.getStart_time());
            oldEvent.setPrice(event.getPrice());
            return eventRepository.save(oldEvent);
        }
        Event newEvent = Event.builder()
                .room(room)
                .movie(movie)
                .start_date(startDate)
                .start_time(event.getStart_time())
                .price(event.getPrice())
                .subtitleType(subtitleType)
                .build();
        return eventRepository.save(newEvent) ;

    }
    public LocalDateTime convertStringToLocalDateTime(String time , LocalDate date) {
        String[] hourSecond = time.split(":");
        int hour = Integer.parseInt(hourSecond[0]);
        int minute = Integer.parseInt(hourSecond[1]);
        LocalTime end_time = LocalTime.of(hour , minute);
        LocalDateTime dateTime = date.atTime(end_time);
        return dateTime ;
    }

    public List<EventDTO> findByDateMovieCinema(LocalDate date ,
                                                Long movieId ,
                                                String cinemaName,
                                                String type) {
        List<Event> events = eventRepository.listByDateCinemaMovie(date, cinemaName , movieId , type) ;

        return events
                .stream()
                .map(event -> {
                    EventDTO eventDTO =  modelMapper.map(event , EventDTO.class) ;
                    MovieDTO movieDTO = new MovieDTO() ;
                    movieDTO.setId(event.getMovie().getId());
                    movieDTO.setTitle(event.getMovie().getTitle());
                    movieDTO.setPoster_url(event.getMovie().getPoster_url());
                    eventDTO.setMovie(movieDTO);
                    List<SeatDTO> seatDTOS = seatService.findByEvent(event.getId());
                    eventDTO.getRoom().setSeats(seatDTOS);
                    return eventDTO ;
                })
                .sorted(Comparator.comparing(EventDTO::getStart_time))
                .collect(Collectors.toList());
    }

    public EventDTO getById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class) ;
        List<SeatDTO> seatDTOS = seatService.findByEvent(event.getId());
        MovieDTO movieDTO = new MovieDTO() ;
        movieDTO.setPhotosImagePath(event.getMovie().getPhotosImagePath());
        movieDTO.setId(event.getMovie().getId());
        movieDTO.setTitle(event.getMovie().getTitle());
        movieDTO.setPoster_url(event.getMovie().getPoster_url());
        eventDTO.getRoom().setSeats(seatDTOS);
        eventDTO.setMovie(movieDTO);
        return eventDTO ;
    }

    public List<SubtitleTypeDTO> findByMovieDateCity(Long movieId , LocalDate date , String cityName) {
        return eventRepository.listSubByDateMovieCity(date, cityName , movieId)
                .stream()
                .map(event -> {
                    SubtitleTypeDTO subDto = modelMapper.map(event.getSubtitleType() , SubtitleTypeDTO.class);
                    return subDto;
                })
                .distinct()
                .collect(Collectors.toList());
    }

    public Map<MovieDTO , Map<SubtitleTypeDTO , List<EventDTO>>> findByCinemaAndDate(Long cinemaId ,
                                                                                     LocalDate date) {
        List<EventDTO> eventDTOS = eventRepository.listByDateCinema(date, cinemaId)
                .stream()
                .map(event ->
                {
                   EventDTO eventDTO =  modelMapper.map(event , EventDTO.class);
                   eventDTO.getMovie().setGenres(null);
                   eventDTO.setRoom(null);
                   return eventDTO;
                })
                .collect(Collectors.toList());
        Map<MovieDTO , Map<SubtitleTypeDTO , List<EventDTO>>> events =
                eventDTOS.stream()
                        .collect(Collectors.groupingBy(
                                EventDTO::getMovie,
                                Collectors.groupingBy(
                                        EventDTO::getSubtitleType,
                                        Collectors.toList()
                                )
                        ));
        return events;
    }

    public List<EventDTO> findRoomDate(Long roomId , LocalDate date) {
        List<EventDTO> events = eventRepository.listByDateRoom(roomId, date)
                .stream()
                .map(event -> {
                    EventDTO eventDTO = modelMapper.map(event, EventDTO.class) ;
                    eventDTO.setRoom(null);
                    return  eventDTO ;
                })
                .sorted(Comparator.comparing(EventDTO::getStart_time))
                .collect(Collectors.toList());
        return events ;
    }

    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(()-> new EventValidException("event not found"));
        eventRepository.delete(event);
    }

}
