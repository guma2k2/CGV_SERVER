package com.movie.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subtitle_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubtitleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    private String name ;

    @JsonIgnore
    @OneToMany(mappedBy = "subtitleType" , fetch = FetchType.LAZY)
    @Builder.Default
    private List<Event> events = new ArrayList<>();

    public SubtitleType(Integer id) {
    }

    @PreRemove
    public void removeSubType() {
        events.forEach(event -> event.setSubtitleType(null));
        events = null;
    }
}
