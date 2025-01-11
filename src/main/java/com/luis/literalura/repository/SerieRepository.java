package com.luis.literalura.repository;

import com.luis.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SerieRepository extends JpaRepository<Libro, Long> {
    @Query("select e from Libro e where e.yearAuthor >= :year and e.yearAuthorBirth <= :year")
    List<Libro> librosPorYearDeAutor(int year);

    @Query("select e from Libro e  where e.languages LIKE :language")
    List<Libro> librosByLanguage(String language);
}
