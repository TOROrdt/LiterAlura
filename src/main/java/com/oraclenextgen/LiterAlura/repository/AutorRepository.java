package com.oraclenextgen.LiterAlura.repository;

import com.oraclenextgen.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Consultas derivadas
    Autor findFirstByNombre(String nombre);

    List<Autor> findByNacimientoBetween(int startYear, int endYear);

    // Consultas nativas
    @Query(value = "SELECT * FROM autores WHERE ?1 BETWEEN nacimiento AND COALESCE(muerte, ?1) AND nacimiento > 0", nativeQuery = true)
    List<Autor> buscarEntreRangoDeAños(int year);
}
