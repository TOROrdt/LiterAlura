package com.oraclenextgen.LiterAlura.repository;

import com.oraclenextgen.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre();
}
