package Modelo.Entidades;

import java.time.LocalTime;

public class Horario {

    private int id;
    private LocalTime horaFuncion;
    
    public Horario() {}

    public Horario(int id, LocalTime horaFuncion) {
        this.id = id;
        this.horaFuncion = horaFuncion;
    }

    public int getId() {
        return id;
    }

    public LocalTime getHoraFuncion() {
        return horaFuncion;
    }

    @Override
    public String toString() {
        return horaFuncion.toString();
    }
}
