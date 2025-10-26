package Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import Modelo.Dao.FuncionHorarioDAO;
import Modelo.Entidades.Compra;
import Modelo.Entidades.Usuario;
import Modelo.Servicios.AlertaUtil;

public class ControladorCompra {

    @FXML
    private Label lblTitulo, lblFecha, lblFormato, lblHorario, lblCantidad, lblSubtotal, lblTotal;
    private Compra compra;
    private Usuario usuarioActual;
    private FuncionHorarioDAO funcionHorarioDAO = new FuncionHorarioDAO();

    public void setCompra(Compra compra) {
        this.compra = compra;
        mostrarResumen();
    }

    private void mostrarResumen() {
        lblTitulo.setText(compra.getPelicula().getTitulo());
        lblFecha.setText(compra.getFuncionHorario().getFuncion().getFecha().toString());
        lblFormato.setText(compra.getFuncionHorario().getFormato().getTipoFormato());
        lblHorario.setText(compra.getFuncionHorario().getHorario().getHoraFuncion().toString());
        lblCantidad.setText(String.valueOf(compra.getCantidadEntradas()));
        lblSubtotal.setText("$" + compra.getPrecioUnitario());
        lblTotal.setText("$" + compra.getTotal());
    }

    @FXML
    private void confirmarCompra() {
        boolean exito = funcionHorarioDAO.actualizarOcupacion(compra.getFuncionHorario().getId(), compra.getCantidadEntradas());

        if (exito) {
            AlertaUtil.mostrarAlerta("Compra confirmada", null, "¡Compra realizada con éxito!", Alert.AlertType.INFORMATION);
        } else {
            AlertaUtil.mostrarAlerta("Error", null, "No se pudo registrar la compra.", Alert.AlertType.ERROR);
        }
        volverACartelera();
    }

    // Metodos  auxiliares 
    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    @FXML
    private void cancelarCompra() {
        volverACartelera();
    }

    private void volverACartelera() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/Cartelera.fxml"));
            Parent root = loader.load();

            ControladorCartelera controladorCartelera = loader.getController();
            controladorCartelera.setUsuario(usuarioActual);

            Stage stage = (Stage) lblTitulo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cartelera - Cinemotion");
            stage.setWidth(1000);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
