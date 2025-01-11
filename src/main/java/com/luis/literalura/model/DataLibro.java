package com.luis.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.json.JSONObject;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataLibro(
    @JsonAlias("title") String title,
    @JsonAlias("authors") List<Author> authors,
    @JsonAlias("languages") List<String> languages,
    @JsonAlias("download_count") Integer download_count
) {

}
