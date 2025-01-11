package com.luis.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Author(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer yearAuthorBirth,
        @JsonAlias("death_year") Integer yearAuthor

) {
}
