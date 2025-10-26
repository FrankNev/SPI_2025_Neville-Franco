package Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import Modelo.Entidades.Usuario;
import Modelo.Dao.UsuarioDAO;
import Modelo.Servicios.AlertaUtil;

public class ControladorLogin {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private ImageView iconoCinemotion;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        Image img = new Image(getClass().getResource("/Vista/Recursos/icono_cinemotion.png").toExternalForm());
        iconoCinemotion.setImage(img);
    }

    @FXML
    private void iniciarSesion() {

        String usuarioIngresado = txtUsuario.getText();
        String contraseñaIngresada = txtContraseña.getText();

        Usuario usuario = usuarioDAO.obtenerUsuarioPorCredenciales(usuarioIngresado, contraseñaIngresada);

        if (usuario != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/Cartelera.fxml"));
                Parent root = loader.load();

                ControladorCartelera controladorCartelera = loader.getController();
                controladorCartelera.setUsuario(usuario);

                Stage stage = (Stage) txtUsuario.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setWidth(1000);
                stage.setTitle("Cartelera - Cinemotion");
                stage.show();
                stage.centerOnScreen();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AlertaUtil.mostrarAlerta("Error de inicio de sesión", "Credenciales inválidas", "Verifique su nombre de usuario y contraseña.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void abrirRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/fxml/Registro.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de Usuario");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
