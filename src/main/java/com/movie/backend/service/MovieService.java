package com.movie.backend.service;


import com.cloudinary.Cloudinary;
import com.movie.backend.dto.GenreDTO;
import com.movie.backend.dto.MovieDTO;

import com.movie.backend.entity.*;
import com.movie.backend.exception.MovieException;
import com.movie.backend.exception.UserException;
import com.movie.backend.repository.GenreRepository;
import com.movie.backend.repository.MovieRepository;

import com.movie.backend.dto.DataContent;
import com.movie.backend.dto.Paginate;
import com.movie.backend.ultity.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository ;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${application.service.movie.moviePerPage}")
    private int moviePerPage ;

    @Autowired
    private Cloudinary cloudinary;

    public List<GenreDTO> listAllGenre() {
        return genreRepository.findAll()
                .stream()
                .map(genre -> modelMapper.map(genre, GenreDTO.class))
                .collect(Collectors.toList());
    }

    public Movie saveMovie(MovieDTO movieDTO , Long movieId) {
        boolean update = movieId != null ;
        String requestTitle = movieDTO.getTitle() ;
        Movie checkMovieExit = movieRepository.getByTitle(requestTitle) ;
        if(update) {
            if(checkMovieExit != null ) {
                if (checkMovieExit.getId() != movieId) {
                    System.out.println("fuck you ");
                    throw new MovieException("Title not valid") ;
                }
            }
        } else {
            if (checkMovieExit != null) {
                throw new MovieException("Title not valid") ;
            }
        }

        String title = movieDTO.getTitle() ;
        String description = movieDTO.getDescription();
        int duration_minutes = movieDTO.getDuration_minutes() ;
        LocalDate release_date = movieDTO.getRelease_date() ;
        Language language = movieDTO.getLanguage();
        Rating rating = movieDTO.getRating();
        String director = movieDTO.getDirector() ;
        String cast = movieDTO.getCast() ;
        Boolean isShowing = movieDTO.isShowing() ;
        String trailer = movieDTO.getTrailer() ;
        Set<Genre> genres = movieDTO.getGenres().stream().map(genreDTO -> modelMapper.map(genreDTO , Genre.class)).collect(Collectors.toSet());


        if(update) {
            Movie oldMovie =   movieRepository.findById(movieId).get() ;
            oldMovie.setTitle(title);
            oldMovie.setDescription(description);
            oldMovie.setDuration_minutes(duration_minutes);
            oldMovie.setRelease_date(release_date);
            oldMovie.setLanguage(language);
            oldMovie.setRating(rating);
            oldMovie.setDirector(director);
            oldMovie.setCast(cast);
            oldMovie.setShowing(isShowing);
            oldMovie.setTrailer(trailer);
            oldMovie.setGenres(genres);
            return movieRepository.save(oldMovie) ;
        }
        Movie newMovie = Movie.builder()
                .title(title)
                .description(description)
                .duration_minutes(duration_minutes)
                .release_date(release_date)
                .language(language)
                .rating(rating)
                .director(director)
                .cast(cast)
                .isShowing(isShowing)
                .trailer(trailer)
                .genres(genres)
                .build();
       return movieRepository.save(newMovie) ;
    }
    public void savePoster(Long movieId, MultipartFile multipartFile) throws IOException {
        if (movieId == null) {
            throw new MovieException("The id of movie cannot null");
        }
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new UserException("Movie not found")) ;
        if (!multipartFile.isEmpty()) {
            HashMap<String, String> map = new HashMap<>();
            String fileId = UUID.randomUUID().toString();
            map.put("public_id", fileId);
            map.put("resource_type", "auto");
            Map uploadResult = null;
            try {
                uploadResult = cloudinary.uploader()
                        .upload(multipartFile.getBytes(), map);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            String url = uploadResult
                    .get("url")
                    .toString();
            movie.setPoster_url(url);
        } else {
            if (movie.getPoster_url().isEmpty()){
                movie.setPoster_url(null);
            }
        }

        movieRepository.save(movie);
    }

    public List<MovieDTO> listAll() {
        log.info("called this api");
        return movieRepository.findAll()
                .stream()
                .map(movie -> modelMapper.map(movie,MovieDTO.class))
                .collect(Collectors.toList());
    }

    public DataContent listFirstPage() {
        return findAll(1 , "desc" , "id" , null) ;
    }

    public DataContent findAll(Integer pageNum , String sortDir , String sortField, String keyword) {
        if (pageNum == null || sortDir == null || sortField == null) {
            throw new MovieException("The pageNum or sortDir or sortField cannot null");
        }
        Sort sort = Sort.by(sortField) ;
        sort =  sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1 , moviePerPage , sort );
        Page<Movie> pages ;
        if (keyword!= null ) {
            pages = movieRepository.findAll(keyword, pageable);
        }else {
            pages = movieRepository.findAll(pageable);
        }
        int total = pages.getTotalPages();
        int totalElements = (int) pages.getTotalElements();


        List<MovieDTO> movies = pages.getContent()
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
        Paginate paginate = Paginate.builder()
                .keyword(keyword)
                .currentPage(pageNum)
                .sortDir(sortDir)
                .sortField(sortField)
                .totalPage(total)
                .totalElements(totalElements)
                .sizePage(moviePerPage)
                .build();
        return new DataContent(paginate , movies);
    }

    public MovieDTO findById(Long movieId) {
        if (movieId == null) {
            throw  new MovieException("The movieId cannot null");
        }
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        return modelMapper.map(movie,MovieDTO.class);
    }

    public List<MovieDTO> findByTitle(String title) {
        return movieRepository.findByTitle(title)
                .stream()
                .map(movie -> modelMapper.map(movie,MovieDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieException("movie not found")) ;
        movieRepository.delete(movie);
    }
    public List<MovieDTO> findBeforeDate() {
        return movieRepository.findBeforeDate()
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }

    public List<MovieDTO> findAfterDate() {
        return movieRepository.findAfterDate()
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDTO.class))
                .collect(Collectors.toList());
    }



    @Transactional
    public void updateStatus(Long movieId, boolean status) {
        movieRepository.findById(movieId).orElseThrow(() -> new MovieException("Movie not found")) ;
        movieRepository.updateStatus(movieId, status);
    }
}
