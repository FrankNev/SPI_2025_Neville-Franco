package Modelo.Entidades;

import java.time.LocalDate;

public class Pelicula {

    private int id;
    private String titulo;
    private String duracion;
    private String director;
    private String genero;
    private LocalDate fechaEstreno;
    private String urlImg;
    
    public Pelicula() {}

    public Pelicula(int id, String titulo, String duracion, String director, String genero, LocalDate fechaEstreno, String urlImg) {
        this.id = id;
        this.titulo = titulo;
        this.duracion = duracion;
        this.director = director;
        this.genero = genero;
        this.fechaEstreno = fechaEstreno;
        this.urlImg = urlImg;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getDirector() {
        return director;
    }

    public String getGenero() {
        return genero;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
