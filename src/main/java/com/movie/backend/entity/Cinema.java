package com.movie.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.movie.backend.ultity.FileUploadUtil;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "cinema")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;

    @Column(length = 1000)
    private String address ;

    @Column(name = "main_image" )
    private String image_url ;

    private String phone_number ;

    private String cinema_type ;

    @ManyToOne(fetch = FetchType.EAGER)
    private City city;


    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true, mappedBy = "cinema")
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true, mappedBy = "cinema")
    @Builder.Default
    @JsonManagedReference
    private Set<CinemaImage> images = new HashSet<>();

    @Transient
    public String getPhotosImagePath() {
        String baseUrl = FileUploadUtil.baseUrl ;
        if (id == null || image_url == null) return "https://www.cgv.vn/media/imax/goldclass-2.png";
        return baseUrl + "/cinema-images/" + this.id + "/" + this.image_url;
    }
    public boolean containsImageName(String imageName) {
        Iterator<CinemaImage> iterator = images.iterator();

        while (iterator.hasNext()) {
            CinemaImage image = iterator.next();
            if (image.getName().equals(imageName)) {
                return true;
            }
        }

        return false;
    }
    public void addExtraImage(String imageName) {
        CinemaImage cinemaImage = new CinemaImage(imageName);
        cinemaImage.setCinema(this);
        this.images.add(cinemaImage);
    }

    public Cinema(City city) {
        this.city = city;
    }
    public Cinema(Long id, String name, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.rooms = rooms;
    }

    public void setImages(Set<CinemaImage> images) {
        this.images.clear();
        if(images != null) {
            this.images.addAll(images);
        }
    }
}
