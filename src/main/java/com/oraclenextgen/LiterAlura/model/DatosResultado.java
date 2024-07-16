package com.oraclenextgen.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record DatosResultado(
        @JsonAlias ("count") int contador,
        @JsonAlias ("results") List<DatosLibro> libros
) {
}
