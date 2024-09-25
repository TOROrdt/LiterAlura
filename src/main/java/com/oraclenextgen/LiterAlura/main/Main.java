package com.oraclenextgen.LiterAlura.main;

import com.oraclenextgen.LiterAlura.model.*;
import com.oraclenextgen.LiterAlura.repository.AutorRepository;
import com.oraclenextgen.LiterAlura.repository.LibroRepository;
import com.oraclenextgen.LiterAlura.service.ConsultaLibros;
import com.oraclenextgen.LiterAlura.service.ConvierteDatos;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    private List<Libro> libros;
    private List<Autor> autores;

    public Main(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.repositorioLibro = libroRepository;
        this.repositorioAutor = autorRepository;
    }

    public void mostrarMenu () throws IOException, InterruptedException {

        boolean ciclo = true;

        while (ciclo) {

            int opcion = obtenerOpcionValida();

            switch (opcion) {
                case 1 -> buscarYRegistrarLibro();
                case 2 -> listarLibros();
                case 3 -> listarLibrosPorIdioma();
                case 4 -> listarAutores();
                case 5 -> listarAutoresVivos();
                case 0 -> {
                    System.out.println("Cerrando aplicación\n");
                    ciclo = false;
                }
                default -> System.out.println("Opción no válida. Por favor, ingrese una opción válida del menú.\n");
            }
        }
    }

    private int obtenerOpcionValida () {

        int opcion = 0;
        boolean entradaValida = false;

        while (!entradaValida) {

            System.out.println("""
                    
                    ============================= MENU =============================
                    
                     1) Buscar libro por título y registrarlo en la base de datos
                     2) Listar los libros buscados
                     3) Listar libros por idioma de los libros buscados
                     4) Listar autores de los libros buscados
                     5) Listar autores vivos en determinado año
                    
                     0) Salir
                    ================================================================""");
            System.out.print("\nSelecciona una opción: ");
            String entrada = scanner.nextLine();

            try {
                opcion = Integer.parseInt(entrada);
                entradaValida = true;

            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduce un número entero.\n");
            }
        }
        return opcion;
    }

    private void buscarYRegistrarLibro () throws IOException, InterruptedException {

        System.out.println ("\nOpción 1 seleccionada: Buscar libro por título y registrarlo en la base de datos");
        System.out.print("Escribe el libro a buscar: ");
        String titulo = scanner.nextLine();

        ConsultaLibros consultaLibros = new ConsultaLibros();
        String datosJson = consultaLibros.consumirAPI (titulo);
        System.out.println();

        ConvierteDatos convierteDatos = new ConvierteDatos();
        DatosResultado datosResultado = convierteDatos.obtenerDatos (datosJson, DatosResultado.class);

        if (datosResultado.contador() != 0) {

            DatosLibro datosLibro = datosResultado.libros().getFirst();
            DatosAutor datosAutor = datosLibro.autor().getFirst();

            String nombreAutor = String.valueOf(datosAutor.nombre());
            String tituloLibro = String.valueOf(datosLibro.titulo());

            Autor autor;
            Libro libro;

            Optional<Autor> autorExistente = Optional.ofNullable(repositorioAutor.findFirstByNombre(nombreAutor));

            if (autorExistente.isPresent()) {

                Optional<Libro> libroExistente = Optional.ofNullable(repositorioLibro.findByTituloContainingIgnoreCase(tituloLibro));

                if (libroExistente.isPresent()) {
                    System.out.println("Ese libro ya se encuentra en la base de datos, intenta con otro");

                } else {
                    libro = new Libro(datosLibro, autorExistente.get());
                    repositorioLibro.save(libro);
                    System.out.println("=== Libro guardado ===");
                    System.out.print(libro.toString());
                }

            } else {
                autor = new Autor(datosAutor);
                libro = new Libro(datosLibro, autor);
                repositorioAutor.save(autor);
                repositorioLibro.save(libro);
                System.out.println("=== Libro guardado ===");
                System.out.print(libro.toString());
            }

        } else {
            System.out.println("No se encontro el libro, intenta con otro o verifica el título");
        }
    }

    private void listarLibros () {

        System.out.println ("\nOpción 2 seleccionada: Lista de los libros buscados\n");
        libros = repositorioLibro.listarLibrosRegistrados();

        libros.stream().forEach(libro -> System.out.println(libro.toString()));
    }

    private void listarLibrosPorIdioma () {
        System.out.println("\nOpción 3 seleccionada: Listar libros por idioma de los libros buscados");

        int opcion = 0;
        boolean ciclo = true;
        String lenguaje = "";

        while (ciclo) {
            System.out.print("""
                          
                    1) Inglés - en
                    2) Español - es
                    3) Francés - fr
                    4) Portugués - pt
                    """);
            System.out.print("\nSelecciona una opción: ");
            String entrada = scanner.nextLine();

            try {
                opcion = Integer.parseInt(entrada);
                ciclo = false;

            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduce un número entero.\n");
            }

            switch (opcion) {
                case 1 -> lenguaje = "en";
                case 2 -> lenguaje = "es";
                case 3 -> lenguaje = "fr";
                case 4 -> lenguaje = "pt";
                default -> System.out.println("Opción no válida. Por favor, ingrese una opción válida del menú.\n");
            }
        }

        libros = repositorioLibro.listarLibrosPorLenguaje(lenguaje);

        if (libros == null || libros.isEmpty()) {
            System.out.println("\nNo hay libros de ese idioma");
        } else {
            libros.forEach(libro -> System.out.println(libro.toString()));
        }
    }

    private void listarAutores() {
        System.out.println("\nOpción 4 seleccionada: Listar autores de los libros buscados\n");

        autores = repositorioAutor.findAll();

        if (autores == null || autores.isEmpty()) {
            System.out.println("\nNo hay autores aun guardados, registra primero un libro");
        } else {
            autores.forEach(autor -> System.out.println(autor.toString()));
        }
    }

    private void listarAutoresVivos() {
        System.out.println("\nOpción 5 seleccionada: Listar autores vivos en determinado año");

        System.out.print("\nDime el año a buscar: ");
        String entrada = scanner.nextLine();
        System.out.print("\n");

        try {
            int year  = Integer.parseInt(entrada);
            autores = repositorioAutor.buscarEntreRangoDeAños(year);

            if (autores == null || autores.isEmpty()) {
                System.out.println("No hay autores encontrados en ese año");
            } else {
                autores.forEach(autor -> System.out.println(autor.toString()));
            }

        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida. Por favor, introduce un número entero.\n");
        }

    }
}
