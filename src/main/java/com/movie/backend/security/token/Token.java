package com.movie.backend.security.token;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.movie.backend.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String token ;
    public boolean expired ;
    public boolean revoked;
    @Enumerated(EnumType.STRING)
    public TokenType type = TokenType.BEARER;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    public User user;

}
