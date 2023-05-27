package com.movie.backend.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movie.backend.dto.MovieDTO;
import com.movie.backend.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;


    @JsonProperty("refresh_token")
    private String refreshToken ;

    private UserDTO user ;

}