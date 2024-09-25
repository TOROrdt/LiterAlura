package com.oraclenextgen.LiterAlura.repository;

import com.oraclenextgen.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Consultas derivadas
    Libro findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByLenguaje(String lenguaje);

    // Consultas nativas
    @Query(value = "SELECT * FROM libros", nativeQuery = true)
    List<Libro> listarLibrosRegistrados();

    @Query(value = "SELECT * FROM libros WHERE lenguaje = ?1", nativeQuery = true)
    List<Libro> listarLibrosPorLenguaje(String lenguaje);
}
