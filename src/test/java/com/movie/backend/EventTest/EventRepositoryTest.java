package com.movie.backend.EventTest;


import com.movie.backend.entity.*;
import com.movie.backend.repository.EventRepository;
import com.movie.backend.repository.MovieRepository;
import com.movie.backend.repository.SubtitleRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class EventRepositoryTest {

    @Autowired
    private SubtitleRepository subtitleRepository;
    @Autowired
    private EventRepository eventRepository ;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EntityManager entityManager ;


    @Test
    public void TestCreateEvent() {
        LocalDate date10 = LocalDate.of(2023 , 04  , 10 );
        LocalDate date11 = LocalDate.of(2023 , 04  , 11 );
        LocalDate date1 = LocalDate.of(2023 , 04  , 12 );
        LocalDate date2 = LocalDate.of(2023 , 04  , 13 );
        LocalDate date3 = LocalDate.of(2023 , 04  , 14 );
        LocalDate date4 = LocalDate.of(2023 , 04  , 15 );
        LocalDate date5 = LocalDate.of(2023 , 04  , 16 );
        LocalDate date6 = LocalDate.of(2023 , 04  , 17 );
        LocalDate date7 = LocalDate.of(2023 , 04  , 18 );
        LocalDate date8 = LocalDate.of(2023 , 04  , 19 );
        Room room1 = entityManager.find(Room.class ,12L );
        Room room2 = entityManager.find(Room.class ,13L );
        Room room3 = entityManager.find(Room.class ,14L );
        Room room4 = entityManager.find(Room.class ,15L );
        Room room5 = entityManager.find(Room.class ,5L );
        Room room6 = entityManager.find(Room.class ,6L );
        Room room7 = entityManager.find(Room.class ,7L );
        Room room8 = entityManager.find(Room.class ,8L );
        Room room9 = entityManager.find(Room.class ,1L );
        Room room10 = entityManager.find(Room.class ,2L );
        Room room11 = entityManager.find(Room.class ,3L );

        Movie movie = movieRepository.findById(24L).get();
        List<Event> listOfCinema1 = List.of(
                Event
                        .builder()
                        .price(120000)
                        .room(room1)
                        .movie(movie)
                        .start_date(date1)
                        .start_time("11:30")
                        .build() ,
                Event
                        .builder()
                        .price(120000)
                        .room(room2)
                        .movie(movie)
                        .start_date(date1)
                        .start_time("14:30")
                        .build(),
                Event
                        .builder()
                        .price(120000)
                        .room(room3)
                        .movie(movie)
                        .start_date(date1)
                        .start_time("11:30")
                        .build(),
                Event
                        .builder()
                        .price(120000)
                        .room(room4)
                        .movie(movie)
                        .start_date(date1)
                        .start_time("12:30")
                        .build(),
                Event
                        .builder()
                        .price(120000)
                        .room(room2)
                        .movie(movie)
                        .start_date(date1)
                        .start_time("17:30")
                        .build(),
                Event
                        .builder()
                        .price(120000)
                        .room(room1)
                        .movie(movie)
                        .start_date(date1)
                        .start_time("11:30")
                        .build(),
                Event
                        .builder()
                        .price(120000)
                        .room(room4)
                        .movie(movie)
                        .start_date(date1)
                        .start_time("22:30")
                        .build()
        );
//        eventRepository.saveAll(listOfCinema1);
        List<Event> listOfCinema2 = List.of(
                Event
                        .builder()
                        .price(120000)
                        .room(room9)
                        .movie(movie)
                        .start_date(date10)
                        .start_time("10:10")
                        .build() ,
                Event
                        .builder()
                        .price(120000)
                        .room(room10)
                        .movie(movie)
                        .start_date(date10)
                        .start_time("11:10")
                        .build(),
                Event
                        .builder()
                        .price(120000)
                        .room(room11)
                        .movie(movie)
                        .start_date(date10)
                        .start_time("12:45")
                        .build()

        );
        eventRepository.saveAll(listOfCinema2);
                ;
        // room 7 8 9 < // city
        List<Event> listOfCinema3 = List.of(
                Event
                        .builder()
                        .price(120000)
                        .room(room7)
                        .movie(movie)
                        .start_date(LocalDate.now().plusDays(1))
                        .start_time("10:30")
                        .build() ,
                Event
                        .builder()
                        .price(120000)
                        .room(room8)
                        .movie(movie)
                        .start_date(LocalDate.now().plusDays(1))
                        .start_time("12:30")
                        .build(),
                Event
                        .builder()
                        .price(120000)
                        .room(room9)
                        .movie(movie)
                        .start_date(LocalDate.now().plusDays(1))
                        .start_time("14:30")
                        .build()
        )
                ;

//        eventRepository.saveAll(listOfCinema2);
    }


    @Test
    public void findCinema() {
        Long movieId = 16L ;
        String cityName = "Hà Nội";
        LocalDate date = LocalDate.of(2023 , 04 , 10) ;
        String subType = "2D Phụ Đề Việt";
        List<Event> events = eventRepository.findByDateAndCity(date, cityName , movieId , subType) ;
        List<Cinema> cinemas = events.stream().map(event -> event.getRoom().getCinema()).collect(Collectors.toList());
        assert cinemas.size() > 0 ;
        assert(events.size() > 0) ;
    }
    @Test
    public void findById() {
        Event event = eventRepository.findById(4L).get();

    }

    @Test
    public void listByDateCinemaMovie() {
        LocalDate now = LocalDate.of(2023 , 04 , 10) ;
        Long movieId = 16L ;
        String cinemaName = "CGV Vincom Bà Triệu" ;
        String subType = "2D Phụ Đề Việt";
        List<Event> events = eventRepository.listByDateCinemaMovie(now, cinemaName , movieId, subType) ;
        List<String> startTime = events.stream().map(event -> event.getStart_time()).collect(Collectors.toList());
        startTime.forEach(s -> System.out.println(s));
        assert startTime.size() > 0 ;
    }


    @Test
    public void saveSubtitle() {
        List<SubtitleType> types = List.of(
                SubtitleType
                        .builder()
                        .name("2D Phụ Đề Việt")
                        .build(),
                SubtitleType
                        .builder()
                        .name("2D Eng&Viet Sub")
                        .build(),
                SubtitleType
                        .builder()
                        .name("IMAX2D Phụ Đề Việt")
                        .build(),
                SubtitleType
                        .builder()
                        .name("2D Lồng Tiếng Việt")
                        .build()
        );
        subtitleRepository.saveAll(types);
    }

    @Test
    public void setSubtitleForEvent() {
        Event event = eventRepository.findById(4L).get();
        SubtitleType sub1 = subtitleRepository.findById(1).get();
        event.setSubtitleType(sub1);
        eventRepository.save(event);
    }

    @Test
    public  void findSubtitle() {
        LocalDate localDate = LocalDate.of(2023 , 04 , 10);
        Long movieId = 16L ;
        String cityName = "Hà Nội" ;
        List<Event> types = eventRepository.listSubByDateMovieCity(localDate , cityName ,movieId) ;
        assert types.size() > 0 ;
    }


}
