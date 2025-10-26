package Modelo.Entidades;

public class Compra {

    private Pelicula pelicula;
    private FuncionHorario funcionHorario;
    private int cantidadEntradas;
    private int precioUnitario;
    private int total;

    public Compra(Pelicula pelicula, FuncionHorario funcionHorario, int cantidadEntradas) {
        this.pelicula = pelicula;
        this.funcionHorario = funcionHorario;
        this.cantidadEntradas = cantidadEntradas;
        this.precioUnitario = funcionHorario.getFormato().getValorEntrada();
        this.total = precioUnitario * cantidadEntradas;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public FuncionHorario getFuncionHorario() {
        return funcionHorario;
    }

    public int getCantidadEntradas() {
        return cantidadEntradas;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public int getTotal() {
        return total;
    }
}
