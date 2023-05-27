package com.movie.backend.api;

import com.movie.backend.dto.CityDTO;
import com.movie.backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies/cities")
@CrossOrigin("*")
public class CityRestController {

    @Autowired
    private CityService cityService ;

    @GetMapping("/{id}/{date}")
    public List<CityDTO> findByMovieAndDate(@PathVariable("id") Long id , @PathVariable("date")LocalDate date ) {
        return cityService.listByMovieAndDate(id, date ) ;
    }

    @GetMapping
    public List<CityDTO> listAll() {
        return cityService.listAll();
    }
}
