package com.luis.literalura.model;

import java.util.List;

public class Libro {
    private String title;
    private String authors;
    private String languages;

    public Libro(){

    }

    public Libro(DataLibro data){
        this.title = data.title();
        this.authors = tratamientoAuthor(data.authors());
        this.languages = tratamientoLanguage(data.languages());
        this.download_count = data.download_count();
    }

    @Override
    public String toString() {
        return
                "titulo='" + title + '\'' +
                        ", autores=" + authors +
                        ", lenguaje=" + languages +
                        ", Número de descargas=" + download_count;
    }

    private String tratamientoAuthor(List<Author> authors){
        String named;
        String lastname;
        String fullName;
        Author first = authors.getFirst();
        named = first.name().split(",")[1];
        lastname = first.name().split(",")[0];
        fullName = named + " " + lastname;
        return fullName;
    }

    private String tratamientoLanguage(List<String> languages){
        String lan;
        lan = languages.getFirst();
        return lan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    private Integer download_count;




}


