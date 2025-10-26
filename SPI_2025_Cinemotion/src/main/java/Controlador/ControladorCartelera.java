package Controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import Modelo.Dao.PeliculaDAO;
import Modelo.Entidades.Pelicula;
import Modelo.Entidades.Usuario;
import Modelo.Servicios.AlertaUtil;

public class ControladorCartelera {

    @FXML
    private ImageView imagenCarrusel, iconoUsuario, iconoCinemotion;
    @FXML
    private TilePane peliculasGrid;
    @FXML
    private VBox formularioPanel;
    @FXML
    private Label formularioTitulo, lblBienvenido;
    @FXML
    private TextField txtTitulo, txtDuracion, txtDirector, txtGenero, txtUrlImg;
    @FXML
    private DatePicker fechaEstreno;
    @FXML
    private Button btnAgregar, btnEliminar;

    private List<String> imagenesCarrusel = new ArrayList<>();
    private int indiceActual = 0;
    private Pelicula peliculaSeleccionada;
    private PeliculaDAO peliculaDAO = new PeliculaDAO();
    private Integer idPeliculaEditando = null;
    private boolean esAdmin = false;
    private Usuario usuarioActual;

    // Metodos de carga de contenido
    @FXML
    public void initialize() {
        Image img = new Image(getClass().getResource("/Vista/Recursos/icono_cinemotion.png").toExternalForm());
        iconoCinemotion.setImage(img);

        cargarImagenesCarrusel();
        if (!imagenesCarrusel.isEmpty()) {
            mostrarImagenCarrusel();
            iniciarCarrusel();
        }

        cargarPeliculas();
    }

    private void cargarImagenesCarrusel() {
        imagenesCarrusel = List.of(
                getClass().getResource("/Vista/Recursos/ImagenesCarrusel/Banner_Conjuro 4.jpg").toExternalForm(),
                getClass().getResource("/Vista/Recursos/ImagenesCarrusel/Banner_Los 4 Fantasticos.jpg").toExternalForm(),
                getClass().getResource("/Vista/Recursos/ImagenesCarrusel/Banner_Telefono Negro.jpg").toExternalForm(),
                getClass().getResource("/Vista/Recursos/ImagenesCarrusel/Banner_Tron-Ares.jpg").toExternalForm()
        );
    }

    private void cargarPeliculas() {
        peliculasGrid.getChildren().clear();
        List<Pelicula> peliculas = peliculaDAO.obtenerPeliculas();

        for (Pelicula p : peliculas) {
            VBox tarjeta = new VBox(5);
            tarjeta.setStyle("-fx-padding: 5; -fx-alignment: center; -fx-background-color: #284062;");
            ImageView img = new ImageView(new Image(p.getUrlImg(), 120, 180, true, true));
            Label titulo = new Label(p.getTitulo());
            titulo.setStyle("-fx-text-fill: white;");

            tarjeta.getChildren().addAll(img, titulo);

            tarjeta.setCursor(Cursor.HAND);
            tarjeta.setOnMouseEntered(e -> tarjeta.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-background-color: #345480;"));
            tarjeta.setOnMouseExited(e -> tarjeta.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-background-color: #284062;"));

            tarjeta.setOnMouseClicked(e -> {
                peliculaSeleccionada = p;
                if (esAdmin) {
                    seleccionarPelicula(p);
                } else {
                    iniciarCompraEntradas(p);
                }
            });

            peliculasGrid.getChildren().add(tarjeta);
        }
    }

    // Métodos de administración
    @FXML
    private void guardarPelicula() {
        if (txtTitulo.getText().isEmpty() || txtDuracion.getText().isEmpty() || txtDirector.getText().isEmpty() || txtGenero.getText().isEmpty()
                || fechaEstreno.getValue() == null
                || txtUrlImg.getText().isEmpty()) {

            AlertaUtil.mostrarAlerta("Formulario incompleto", null, "Por favor, complete todos los campos antes de guardar la película.", 
                    Alert.AlertType.WARNING);
            return;
        }

        Pelicula p = new Pelicula();
        p.setTitulo(txtTitulo.getText());
        p.setDuracion(txtDuracion.getText());
        p.setDirector(txtDirector.getText());
        p.setGenero(txtGenero.getText());
        p.setFechaEstreno(fechaEstreno.getValue());
        p.setUrlImg(txtUrlImg.getText());

        boolean exito;
        if (idPeliculaEditando == null) {
            exito = peliculaDAO.agregarPelicula(p);
        } else {
            p.setId(idPeliculaEditando);
            exito = peliculaDAO.actualizarPelicula(p);
        }

        if (exito) {
            AlertaUtil.mostrarAlerta("Gestión de Películas", null, "Película guardada correctamente.", Alert.AlertType.INFORMATION);
            formularioPanel.setVisible(false);
            formularioPanel.setManaged(false);
            cargarPeliculas();
        } else {
            AlertaUtil.mostrarAlerta("Gestión de Películas", null,
                    "Error al guardar película.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void eliminarPelicula(ActionEvent event) {
        if (peliculaSeleccionada == null) {
            AlertaUtil.mostrarAlerta("Gestión de Películas", null, "Seleccione una película primero.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Eliminar Película");
        confirmacion.setHeaderText("¿Eliminar '" + peliculaSeleccionada.getTitulo() + "'?");
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                boolean exito = peliculaDAO.eliminarPelicula(peliculaSeleccionada.getId());
                if (exito) {
                    AlertaUtil.mostrarAlerta("Gestión de Películas", null, "Película eliminada exitosamente", Alert.AlertType.INFORMATION);
                    cargarPeliculas();
                    peliculaSeleccionada = null;
                } else {
                    AlertaUtil.mostrarAlerta("Gestión de Películas", null, "Error al eliminar la película.", Alert.AlertType.ERROR);
                }
            }
        });
    }

    // Métodos auxiliares
    private void mostrarImagenCarrusel() {
        String url = imagenesCarrusel.get(indiceActual);
        imagenCarrusel.setImage(new Image(url, true));
    }

    private void iniciarCarrusel() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            indiceActual = (indiceActual + 1) % imagenesCarrusel.size();
            mostrarImagenCarrusel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void seleccionarPelicula(Pelicula p) {
        formularioTitulo.setText("Editar Película");
        idPeliculaEditando = p.getId();

        txtTitulo.setText(p.getTitulo());
        txtDuracion.setText(String.valueOf(p.getDuracion()));
        txtDirector.setText(p.getDirector());
        txtGenero.setText(p.getGenero());
        fechaEstreno.setValue(p.getFechaEstreno());
        txtUrlImg.setText(p.getUrlImg());

        formularioPanel.setVisible(true);
        formularioPanel.setManaged(true);

        Stage stage = (Stage) peliculasGrid.getScene().getWindow();
        stage.setWidth(1300);
        stage.centerOnScreen();
    }

    private void iniciarCompraEntradas(Pelicula p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/Funciones.fxml"));
            Parent root = loader.load();

            ControladorFunciones controladorFunciones = loader.getController();
            controladorFunciones.setPelicula(p);
            controladorFunciones.setUsuario(usuarioActual);

            Stage stage = (Stage) peliculasGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Funciones disponibles - Cinemotion");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarFormularioAgregar() {
        formularioTitulo.setText("Agregar Película");
        limpiarFormulario();
        idPeliculaEditando = null;
        formularioPanel.setVisible(true);
        formularioPanel.setManaged(true);

        Stage stage = (Stage) peliculasGrid.getScene().getWindow();
        stage.setWidth(1300);
        stage.centerOnScreen();
    }

    @FXML
    private void cancelarFormulario() {
        formularioPanel.setVisible(false);
        formularioPanel.setManaged(false);

        Stage stage = (Stage) peliculasGrid.getScene().getWindow();
        stage.setWidth(1000);
    }

    private void limpiarFormulario() {
        txtTitulo.clear();
        txtDuracion.clear();
        txtDirector.clear();
        txtGenero.clear();
        txtUrlImg.clear();
        fechaEstreno.setValue(null);
        idPeliculaEditando = null;
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) peliculasGrid.getScene().getWindow();
            stage.setWidth(600);
            stage.setScene(new Scene(root));
            stage.setTitle("Login - Cinemotion");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        String nombreUsuario = usuario.getNombreUsuario();
        this.esAdmin = usuario.getTipoUsuario().equalsIgnoreCase("admin");

        lblBienvenido.setText("Bienvenido \n" + nombreUsuario);
        btnAgregar.setVisible(esAdmin);
        btnAgregar.setManaged(esAdmin);
        btnEliminar.setVisible(esAdmin);
        btnEliminar.setManaged(esAdmin);

        Image img = new Image(getClass().getResource("/Vista/Recursos/user.png").toExternalForm());
        iconoUsuario.setImage(img);
    }
}
