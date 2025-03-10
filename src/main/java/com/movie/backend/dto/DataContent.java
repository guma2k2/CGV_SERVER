package com.movie.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataContent<T> {
    private Paginate paginate ;
    private List<T> results;
}
