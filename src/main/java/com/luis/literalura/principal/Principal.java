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
}
