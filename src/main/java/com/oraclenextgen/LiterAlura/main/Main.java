package com.oraclenextgen.LiterAlura.main;

import com.oraclenextgen.LiterAlura.model.*;
import com.oraclenextgen.LiterAlura.repository.AutorRepository;
import com.oraclenextgen.LiterAlura.repository.LibroRepository;
import com.oraclenextgen.LiterAlura.service.ConsultaLibros;
import com.oraclenextgen.LiterAlura.service.ConvierteDatos;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    private List<Autor> autores;
    private List<Libro> libros;

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

        String menu = """
                    
                    ============================= MENU =============================
                    
                     1) Buscar libro por título y registrarlo en la base de datos
                     2) Listar los libros buscados
                     3) Listar libros por idioma de los libros buscados
                     4) Listar autores de los libros buscados
                     5) Listar autores vivos en determinado año
                     
                     0) Salir
                    ================================================================""";

        int opcion = 0;
        boolean entradaValida = false;

        while (!entradaValida) {

            System.out.println(menu);
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

        ConvierteDatos convierteDatos = new ConvierteDatos();
        DatosResultado datosResultado = convierteDatos.obtenerDatos (datosJson, DatosResultado.class);
        System.out.println (datosResultado);

        DatosLibro datosLibro = datosResultado.libros().getFirst();

        Autor autor = new Autor (datosLibro.autor().getFirst());
        Libro libro = new Libro (datosLibro, autor);

        repositorioAutor.save(autor);
        repositorioLibro.save(libro);

//        List<DatosLibro> libros = new ArrayList<>();
//        libros = datosResultado.libros();
//        System.out.println(libros);
//
//        List<DatosAutor> autores = new ArrayList<>();
//        autores = libros.getFirst().autor();
//        System.out.println(autores);
    }

    private void listarLibros () {

        System.out.println ("\nOpción 2 seleccionada: Lista de los libros buscados");
        libros = repositorioLibro.findAll();

        libros.stream().forEach(System.out::println);
    }

    private void listarLibrosPorIdioma () {
        System.out.println("Opción 3 seleccionada: Listar libros por idioma de los libros buscados");
        // Aquí puedes colocar la lógica para la opción 3
    }

    private void listarAutores() {
        System.out.println("Opción 4 seleccionada: Listar autores de los libros buscados");
        // Aquí puedes colocar la lógica para la opción 4
    }

    private void listarAutoresVivos() {
        System.out.println("Opción 5 seleccionada: Listar autores vivos en determinado año");
        // Aquí puedes colocar la lógica para la opción 5
    }
}
