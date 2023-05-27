package com.movie.backend.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CinemaDTO {
    private Long id ;
    private String name ;
    private String address ;
    private String image_url ;
    private String phone_number ;
    private String cinema_type ;
    private CityDTO city;
    private Set<CinemaImageDTO> images;
    private String photosImagePath;

}
