package com.movie.backend.api;

import com.movie.backend.entity.Combo;
import com.movie.backend.repository.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies/combos")
@CrossOrigin("*")
public class ComboRestController {

    @Autowired
    private ComboRepository comboRepository ;

    @GetMapping
    public List<Combo> getAll() {
        return comboRepository.findAll() ;
    }
}
