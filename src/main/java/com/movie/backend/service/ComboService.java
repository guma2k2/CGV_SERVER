package com.movie.backend.service;

import com.movie.backend.entity.Combo;
import com.movie.backend.repository.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComboService {

    @Autowired
    private ComboRepository comboRepository ;

    public List<Combo> getAll() {
        return comboRepository.findAll() ;
    }
}
