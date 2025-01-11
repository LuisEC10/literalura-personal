package com.luis.literalura.principal;

import com.luis.literalura.model.DataLibro;
import com.luis.literalura.model.Libro;
import com.luis.literalura.service.ConsumoAPI;
import com.luis.literalura.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumeAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private List<Libro> libros = new ArrayList<>();

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
                    getDataLibro();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
            }
        }
    }

    public DataLibro getDataLibro(){
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        var nameBook = sc.nextLine();
        var json = consumeAPI.obtenerDatos(URL_BASE+nameBook.replace(" ","%20"));
        System.out.println(json);
        DataLibro data = conversor.getData(json,DataLibro.class);
        Libro libro = new Libro(data);
        libros.add(libro);
        return data;
    }

    public void showBooks(){
        for(Libro libro : libros){
            System.out.println(libro.toString());;
        }
    }

}
