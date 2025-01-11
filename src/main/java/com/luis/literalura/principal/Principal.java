package com.luis.literalura.principal;

import com.luis.literalura.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumeAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";

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
                    Option: 
                    """;
            System.out.print(menu);
            option = sc.nextInt();
            sc.nextLine();
            switch (option){


            }
        }
    }

}
