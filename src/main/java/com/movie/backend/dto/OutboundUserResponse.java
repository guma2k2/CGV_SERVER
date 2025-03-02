package com.movie.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OutboundUserResponse(
        @JsonProperty("email") String email,
        @JsonProperty("given_name") String givenName,
        @JsonProperty("family_name") String familyName,
        @JsonProperty("picture") String picture
) {}
