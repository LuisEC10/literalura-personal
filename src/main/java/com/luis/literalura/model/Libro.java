package com.luis.literalura.model;

public record Libro(
    String title,
    String author,
    String language,
    Integer download_count
) {

}
