package com.oraclenextgen.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table (name = "libros")
public class Libro {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String lenguaje;
    private int descargas;

    public Libro () {}

    public Libro(DatosLibro libro, Autor autor) {
//        this.id = libro.id();
        this.titulo = libro.titulo();
        this.autor = new Autor(autor.getNombre(), autor.getNacimiento(), autor.getMuerte());
        this.lenguaje = libro.lenguaje().getFirst();
        this.descargas = libro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public int getDescargas () {
        return descargas;
    }

    public void setDescargas (int descargas) {
        this.descargas = descargas;
    }

    public String getLenguaje () {
        return lenguaje;
    }

    public void setLenguaje (String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Autor getAutor () {
        return autor;
    }

    public void setAutor (Autor autor) {
        this.autor = autor;
    }

    public String getTitulo () {
        return titulo;
    }

    public void setTitulo (String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", lenguaje='" + lenguaje + '\'' +
                ", descargas=" + descargas +
                '}';
    }
}
