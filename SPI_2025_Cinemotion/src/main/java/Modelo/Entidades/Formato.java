package Modelo.Entidades;

public class Formato {

    private int id;
    private String tipoFormato;
    private int valorEntrada;

    public Formato(int id, String tipoFormato, int valorEntrada) {
        this.id = id;
        this.tipoFormato = tipoFormato;
        this.valorEntrada = valorEntrada;
    }

    public int getId() {
        return id;
    }

    public String getTipoFormato() {
        return tipoFormato;
    }

    public int getValorEntrada() {
        return valorEntrada;
    }

    @Override
    public String toString() {
        return tipoFormato;
    }
}
