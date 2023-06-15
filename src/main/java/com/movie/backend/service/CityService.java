package com.movie.backend.service;

import com.movie.backend.dto.CityDTO;
import com.movie.backend.entity.City;
import com.movie.backend.entity.Event;
import com.movie.backend.exception.CinemaException;
import com.movie.backend.repository.CityRepository;
import com.movie.backend.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository ;

    @Autowired
    private EventRepository eventRepository ;

    @Autowired
    private ModelMapper modelMapper ;
    public List<CityDTO> listByMovieAndDate(Long movieId , LocalDate date) {
        if (movieId == null || date == null) {
            throw  new CinemaException("The movieId or the date is null");
        }
        // Get all event by these params
        List<Event> events = eventRepository.findCitiesByMovieIdAndDate(movieId , date) ;

        // get all unique city follow by List event above
        List<City> cities = events.stream().map(event -> event.getRoom().getCinema().getCity()).collect(Collectors.toList());
        return cities.stream()
               .map(city -> modelMapper.map(city,CityDTO.class))
               .distinct()
               .collect(Collectors.toList());

    }

    public List<CityDTO> listAll() {
        return cityRepository.findAll().stream()
                .map(city -> modelMapper.map(city, CityDTO.class))
                .collect(Collectors.toList());
    }

}
