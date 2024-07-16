package com.oraclenextgen.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true)
    private String nombre;
    private int nacimiento;
    private int muerte;
    @OneToMany (mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libros;

    public Autor () {}

    public Autor(String nombre, int nacimiento, int muerte) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.muerte = muerte;
    }

    public Autor(DatosAutor autor) {
        this.nombre = autor.nombre();
        this.nacimiento = autor.nacimiento();
        this.muerte = autor.muerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    public int getNacimiento () {
        return nacimiento;
    }

    public void setNacimiento (int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getMuerte () {
        return muerte;
    }

    public void setMuerte (int muerte) {
        this.muerte = muerte;
    }

    public List<Libro> getLibros () {
        return libros;
    }

    public void setLibros  (List<Libro> libro) {
        this.libros = libro;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", nacimiento=" + nacimiento +
                ", muerte=" + muerte +
                '}';
    }
}
