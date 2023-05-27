package com.movie.backend.api;

import com.movie.backend.dto.CinemaDTO;
import com.movie.backend.entity.Cinema;
import com.movie.backend.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class CinemaRestController {

    @Autowired
    private CinemaService cinemaService ;

    @GetMapping("/movies/cinemas/{startDate}/{cityName}/{movieId}/{subType}")
    public List<CinemaDTO> findByDateAndCity( @PathVariable("startDate")LocalDate date ,
                                              @PathVariable("cityName") String cityName ,
                                              @PathVariable("movieId") Long movieId ,
                                              @PathVariable("subType") String subType) {
       return cinemaService.findByDateAndCity(date, cityName , movieId , subType) ;
    }

    @GetMapping("/movies/cinemas/find/city/{cityId}")
    public List<CinemaDTO> findByCity(@PathVariable("cityId")Integer cityId) {
        return cinemaService.findByCity(cityId);
    }




    @GetMapping("/movies/cinemas/{cinemaId}")
    public CinemaDTO getById(@PathVariable("cinemaId") Long id) {
        return cinemaService.get(id) ;
    }

    @GetMapping("/admin/cinema")
    public List<CinemaDTO> listAll() {
        return cinemaService.findAll();
    }
    @PostMapping("/admin/cinema/save")
    public Cinema saveCinema(@RequestBody CinemaDTO cinemaDTO) {
        return cinemaService.saveCinema(cinemaDTO, null) ;
    }
    @PostMapping("/admin/cinema/save/image/{cinemaId}")
    public String saveImages(@RequestParam("fileImage") MultipartFile mainImageMultipart,
                           @RequestParam("extraImage") MultipartFile[] extraImageMultiparts,
                           @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
                           @RequestParam(name = "imageNames", required = false) String[] imageNames,
                           @PathVariable("cinemaId") Long cinemaId) {
        try {
            cinemaService.saveImages(mainImageMultipart, extraImageMultiparts, imageIDs, imageNames , cinemaId ) ;
            return "Save images success";
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    @PutMapping("/admin/cinema/update/{cinemaId}")
    public Cinema updateCinema(@RequestBody CinemaDTO cinemaDTO , @PathVariable("cinemaId") Long cinemaId) {
        return cinemaService.saveCinema(cinemaDTO, cinemaId) ;
    }

    @GetMapping("/admin/cinema/{cinemaId}")
    public CinemaDTO get(@PathVariable("cinemaId") Long cinemaId) {
        return cinemaService.get(cinemaId) ;
    }

}
