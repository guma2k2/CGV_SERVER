package com.movie.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendCinemaApplication  {

	public static void main(String[] args) {
		SpringApplication.run(BackendCinemaApplication.class, args);
	}


}
