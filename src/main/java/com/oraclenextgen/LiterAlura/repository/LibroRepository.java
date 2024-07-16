package com.oraclenextgen.LiterAlura.repository;

import com.oraclenextgen.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
