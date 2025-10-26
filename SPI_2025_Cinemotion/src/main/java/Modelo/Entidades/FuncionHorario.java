package Modelo.Entidades;

public class FuncionHorario {

    private int id;
    private Funcion funcion;
    private Formato formato;
    private Horario horario;
    private int ocupacion;
    private boolean estaDisponible;

    public FuncionHorario(int id, Funcion funcion, Formato formato, Horario horario, int ocupacion, boolean estaDisponible) {
        this.id = id;
        this.funcion = funcion;
        this.formato = formato;
        this.horario = horario;
        this.ocupacion = ocupacion;
        this.estaDisponible = estaDisponible;
    }

    public int getId() {
        return id;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public Formato getFormato() {
        return formato;
    }

    public Horario getHorario() {
        return horario;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public boolean isEstaDisponible() {
        return estaDisponible;
    }

    @Override
    public String toString() {
        return horario.getHoraFuncion().toString() + " - " + formato.getTipoFormato();
    }

}
