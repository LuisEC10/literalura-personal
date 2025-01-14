package com.luis.literalura.principal;

import com.luis.literalura.model.DataLibro;
import com.luis.literalura.model.Libro;
import com.luis.literalura.repository.SerieRepository;
import com.luis.literalura.service.ConsumoAPI;
import com.luis.literalura.service.ConvierteDatos;
import jakarta.persistence.Index;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumeAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private SerieRepository repository;

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void showMenu(){
        System.out.println("Elija la opción a través de su número:");
        var option = -1;
        while(option != 0){
            var menu = """
                    1 - Buscar libro por título
                    2 - listar libros registrados
                    3 - listar autores registrados
                    4 - listar autorres vivos en un determinado año
                    5 - listar libros por idioma
                    0 - salir
                    
                    """;
            System.out.print(menu);
            option = sc.nextInt();
            sc.nextLine();
            switch (option){
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    showBooks();
                    break;
                case 3:
                    showAuthors();
                    break;
                case 4:
                    showAuthorsByYear();
                    break;
                case 5:
                    showBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
            }
        }
    }

    private void buscarLibroTitulo() {
        try {
            DataLibro data = getDataLibro();
            Libro libro = new Libro(data);
            try {
                repository.save(libro);
                System.out.println(libro.toString());
            } catch (DataIntegrityViolationException e){

                System.out.println("""
                    \t################################################
                    \t# El libro ya se encuentra en la base de datos #
                    \t################################################
                    """);
            }
        } catch (NullPointerException e){
            System.out.println("""
                    \t################################################
                    \t# El libro no existe o no se ha encontrado     #
                    \t################################################
                    """);
        }

    }

    public DataLibro getDataLibro(){
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        var nameBook = sc.nextLine();
        try {
            var json = consumeAPI.obtenerDatos(URL_BASE+nameBook.replace(" ","%20"));
            DataLibro data = conversor.getData(json,DataLibro.class);
            return data;
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    private void showBooks(){
        List<Libro> libros = repository.findAll();
        System.out.println("#####################################");
        System.out.println("Libros registrados");
        libros.forEach(l -> System.out.println("Libro: " + l.getTitle() + ", Autor: " + l.getAuthors() + ", Descargas: " + l.getDownload_count()));
        System.out.println("#####################################\n");
    }

    private void showAuthors(){
        List<Libro> libros = repository.findAll();
        System.out.println("###########################################");
        System.out.println("Autores registrados");
        libros.forEach(l -> System.out.println("Autor: " + l.getAuthors() + " (" + l.getTitle() + ")"));
        System.out.println("###########################################\n");
    }

    private void showAuthorsByYear(){
        int year;
        System.out.println("Ingrese el año que desea para buscar al autor: ");
        year = sc.nextInt();
        List<Libro> autoresByYear = repository.librosPorYearDeAutor(year);
        if(autoresByYear.isEmpty()){
            System.out.println("""
                        #####################################################################
                        # No se encontraron autores que hayan vivido hasta el año ingresado #
                        #####################################################################
                    """);
        }else {
            System.out.println("###########################################");
            System.out.println("Autores que vivieron hasta el año: " + year);
            autoresByYear.forEach(e -> System.out.println("Autor: " + e.getAuthors() + " (Año de fallecimiento: " + e.getYearAuthor() + ")"));
            System.out.println("###########################################");
        }
    }

    private void showBooksByLanguage(){
        String opcion;
        System.out.println("""
                    en : inglés
                    es : español
                """);
        System.out.print("Escriba su opción: ");
        opcion = sc.nextLine();
        List<Libro> librosPorLenguaje = repository.librosByLanguage(opcion);
        System.out.println("###########################################");
        System.out.println("Libros en el lenguaje: " + opcion);
        librosPorLenguaje.forEach(l -> System.out.println("Libro: " + l.getTitle()));
        System.out.println("###########################################");
    }
}
