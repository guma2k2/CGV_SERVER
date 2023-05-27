package com.movie.backend.api;

import com.movie.backend.dto.GenreDTO;
import com.movie.backend.dto.MovieDTO;
import com.movie.backend.entity.Movie;
import com.movie.backend.dto.DataContent;
import com.movie.backend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class MovieRestController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public List<MovieDTO> findAll() {
        return movieService.listAll();
    }

    @GetMapping("/movies/{id}")
    public MovieDTO findById(@PathVariable("id") Long id ) {
        return movieService.findById(id) ;
    }

    @GetMapping("/movies/is/showing")
    public List<MovieDTO> findBeforeDate() {
        return movieService.findBeforeDate();
    }

    @GetMapping("/movies/will/showing")
    public List<MovieDTO> findAfterDate() {
        return movieService.findAfterDate();
    }

    @GetMapping("/admin/movie/find/{title}")
    public List<MovieDTO> findByTitle(@PathVariable("title")String title) {
        return movieService.findByTitle(title) ;
    }
    @GetMapping("/admin/movie/paginate")
    public DataContent findMoviePaginate(@RequestParam("pageNum")int pageNum,
                                         @RequestParam("sortDir")String sortDir ,
                                         @RequestParam("sortField") String sortField,
                                         @RequestParam("keyword") String keyword ) {
        return movieService.findAll(pageNum, sortDir, sortField, keyword) ;
    }

    @GetMapping("/admin/movie/paginate/firstPage")
    public DataContent firstPage() {
        return movieService.listFirstPage() ;
    }

    @GetMapping("/admin/movie/genres")
    public List<GenreDTO> listAllGenre() {
        return movieService.listAllGenre();
    }

    @PostMapping("/admin/movie/save")
    public Movie saveMovie(@RequestBody MovieDTO movieDTO) {
        return movieService.saveMovie(movieDTO , null) ;
    }
    @PostMapping("/admin/movie/save/poster/{movieId}")
    public String savePhoto(@RequestParam("image") MultipartFile file,
                            @PathVariable("movieId") Long movieId ) {
        try {
            movieService.savePoster(movieId, file);
            return "Save poster success" ;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    @PutMapping("/admin/movie/update/{movieId}")
    public Movie updateMovie(@RequestBody MovieDTO movieDTO, @PathVariable("movieId")Long movieId) {
        return movieService.saveMovie(movieDTO , movieId) ;
    }
    @DeleteMapping("/admin/movie/delete/{movieId}")
    public void deleteMovie(@PathVariable("movieId")Long movieId) {
        movieService.deleteMovie(movieId);
    }
    @PutMapping("/admin/movie/update/status/{movieId}/{status}")
    public void updateStatusOfUser(@PathVariable("movieId")Long movieId,
                                   @PathVariable("status")boolean status) {
        movieService.updateStatus(movieId, status);
    }
}
