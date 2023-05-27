//package com.movie.backend.EventTest;
//
//import com.movie.backend.entity.City;
//import com.movie.backend.repository.CityRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@SpringBootTest
//public class CityRepositoryTest {
//    @Autowired
//    private CityRepository cityRepository;
//
//    @Test
//    public void testListCity() {
//        Long movieId = 16L ;
//        LocalDate date = LocalDate.now() ;
//        List<City> cities = cityRepository.findByMovieAndDate(movieId , date) ;
//        cities.forEach(city -> System.out.println(city));
//    }
//}
