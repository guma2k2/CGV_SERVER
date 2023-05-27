package com.movie.backend.repository;

import com.movie.backend.entity.SubtitleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtitleRepository extends JpaRepository<SubtitleType , Integer> {
}
