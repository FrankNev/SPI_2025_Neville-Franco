package Modelo.Entidades;

import java.time.LocalDate;

public class Funcion {
    private int id;
    private int idPelicula;
    private LocalDate fecha;
    private boolean estaDisponible;

    public Funcion(int id, int idPelicula, LocalDate fecha, boolean estaDisponible) {
        this.id = id;
        this.idPelicula = idPelicula;
        this.fecha = fecha;
        this.estaDisponible = estaDisponible;
    }

    public int getId() {
        return id;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean isEstaDisponible() {
        return estaDisponible;
    }

    @Override
    public String toString() {
        return fecha.toString();
    }
}
