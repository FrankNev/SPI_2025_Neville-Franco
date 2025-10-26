package Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.io.IOException;

import Modelo.Dao.FuncionDAO;
import Modelo.Dao.FuncionHorarioDAO;
import Modelo.Entidades.*;
import Modelo.Servicios.AlertaUtil;

public class ControladorFunciones {

    @FXML
    private ImageView imagenPelicula, iconoCinemotion;
    @FXML
    private Label lblTitulo, lblGenero, lblDuracion, lblDirector, lblFechaEstreno, lblEstadoHorario, lblPrecioEntrada;
    @FXML
    private ComboBox<Funcion> comboFechas;
    @FXML
    private ComboBox<Formato> comboFormatos;
    @FXML
    private ComboBox<Horario> comboHorarios;
    @FXML
    private Spinner<Integer> spinnerCantidad;

    private Pelicula peliculaSeleccionada;
    private Funcion funcionSeleccionada;
    private Formato formatoSeleccionado;
    private Horario horarioSeleccionado;
    private FuncionHorario funcionHorarioSeleccionado;
    private Usuario usuarioActual;
    private FuncionDAO funcionDAO = new FuncionDAO();
    private FuncionHorarioDAO funcionHorarioDAO = new FuncionHorarioDAO();

    @FXML
    public void initialize() {
        Image img = new Image(getClass().getResource("/Vista/Recursos/icono_cinemotion.png").toExternalForm());
        iconoCinemotion.setImage(img);
    }

    public void setPelicula(Pelicula pelicula) {
        this.peliculaSeleccionada = pelicula;
        cargarDatosPelicula();
        cargarFechas();
    }

    // Carga de datos
    private void cargarDatosPelicula() {
        lblTitulo.setText(peliculaSeleccionada.getTitulo());
        lblGenero.setText("Género: " + peliculaSeleccionada.getGenero());
        lblDuracion.setText("Duración: " + peliculaSeleccionada.getDuracion());
        lblDirector.setText("Director: " + peliculaSeleccionada.getDirector());
        lblFechaEstreno.setText("Estreno: " + peliculaSeleccionada.getFechaEstreno().toString());

        Image img = new Image(peliculaSeleccionada.getUrlImg());

        imagenPelicula.setImage(img);
    }

    @FXML
    private void cargarFechas() {
        List<Funcion> funciones = funcionDAO.obtenerFuncionesPorPelicula(peliculaSeleccionada.getId());
        comboFechas.getItems().setAll(funciones);
    }

    @FXML
    private void cargarFormatos() {
        funcionSeleccionada = comboFechas.getValue();
        if (funcionSeleccionada != null) {
            List<Formato> formatos = funcionHorarioDAO.obtenerFormatosPorFuncion(funcionSeleccionada.getId());
            comboFormatos.getItems().setAll(formatos);
        }
    }

    @FXML
    private void cargarHorarios() {
        formatoSeleccionado = comboFormatos.getValue();
        if (formatoSeleccionado != null && funcionSeleccionada != null) {
            List<Horario> horarios = funcionHorarioDAO.obtenerHorariosPorFuncionYFormato(
                    funcionSeleccionada.getId(), formatoSeleccionado.getId());
            comboHorarios.getItems().setAll(horarios);
            lblPrecioEntrada.setText("Precio por entrada: $" + formatoSeleccionado.getValorEntrada());
        }
    }

    //Metodos auxiliares
    @FXML
    private void actualizarEstadoHorario() {
        horarioSeleccionado = comboHorarios.getValue();
        if (horarioSeleccionado != null && formatoSeleccionado != null && funcionSeleccionada != null) {
            funcionHorarioSeleccionado = funcionHorarioDAO.obtenerFuncionHorario(
                    funcionSeleccionada.getId(), formatoSeleccionado.getId(), horarioSeleccionado.getId());

            int ocupacion = funcionHorarioSeleccionado.getOcupacion();
            lblEstadoHorario.setText(ocupacion >= 100 ? "● Lleno" : "● Disponible");
            lblEstadoHorario.setStyle(ocupacion >= 100 ? "-fx-text-fill: red; -fx-padding: 5;" : "-fx-text-fill: #40ff00; -fx-padding: 5;");
            spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    1, 100 - ocupacion, 1));
        }
    }

    @FXML
    private void irAResumenCompra() {
        if (peliculaSeleccionada == null || funcionHorarioSeleccionado == null) {
            AlertaUtil.mostrarAlerta("Compra de entradas", null, "Debe seleccionar fecha, formato y horario antes de continuar.", Alert.AlertType.WARNING);
            return;
        }

        int cantidad = spinnerCantidad.getValue();
        Compra compra = new Compra(peliculaSeleccionada, funcionHorarioSeleccionado, cantidad);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/ResumenCompra.fxml"));
            Parent root = loader.load();

            ControladorCompra controladorCompra = loader.getController();
            controladorCompra.setCompra(compra);
            controladorCompra.setUsuario(usuarioActual);

            Stage stage = (Stage) imagenPelicula.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Resumen de compra - Cinemotion");
            stage.setWidth(600);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    @FXML
    private void volverACartelera() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/Cartelera.fxml"));
            Parent root = loader.load();

            ControladorCartelera controlador = loader.getController();
            controlador.setUsuario(usuarioActual);

            Stage stage = (Stage) imagenPelicula.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cartelera - Cinemotion");
            stage.setWidth(1000);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) imagenPelicula.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inicio de sesión - Cinemotion");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
