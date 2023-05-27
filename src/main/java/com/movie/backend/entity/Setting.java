package com.movie.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "setting")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Setting {
    @Id
    @Column( name = "`key`",nullable = false , unique = true , length = 100)
    private String key ;

    @Column(nullable = false, length = 1000)
    private String value ;

    @Column(name = "type" ,nullable = false )
    @Enumerated(EnumType.STRING)
    private TypeSetting type;
}
