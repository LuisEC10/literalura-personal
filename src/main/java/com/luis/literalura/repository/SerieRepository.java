package com.luis.literalura.repository;

import com.luis.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Libro, Long> {

}
