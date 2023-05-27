package com.movie.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Paginate {
    private int sizePage ; // 5 element per page
    private int currentPage ; // 1 - totalPage
    private int totalPage ; // totalPage
    private int totalElements; // total Elements
    private String sortDir ;
    private String sortField;
    private String keyword ;
}
