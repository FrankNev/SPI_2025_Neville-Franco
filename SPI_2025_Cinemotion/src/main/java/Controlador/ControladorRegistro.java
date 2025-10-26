package Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import Modelo.Dao.UsuarioDAO;
import Modelo.Servicios.AlertaUtil;

public class ControladorRegistro {

    @FXML
    private TextField txtUsuario;
    @FXML
    private ImageView iconoCinemotion;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private PasswordField txtConfirmarContraseña;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        Image img = new Image(getClass().getResource("/Vista/Recursos/icono_cinemotion.png").toExternalForm());
        iconoCinemotion.setImage(img);
    }

    @FXML
    private void registrarUsuario() {
        String usuario = txtUsuario.getText();
        String contraseña = txtContraseña.getText();
        String confirmarContraseña = txtConfirmarContraseña.getText();

        //Validaciones
        if (usuario.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
            AlertaUtil.mostrarAlerta("AVISO:", null, "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        if (usuario.length() < 4) {
            AlertaUtil.mostrarAlerta("AVISO:", null, "El nombre de usuario debe tener al menos 4 caracteres.", Alert.AlertType.WARNING);
            return;
        }

        if (contraseña.length() < 6) {
            AlertaUtil.mostrarAlerta("AVISO:", null, "La contraseña debe tener al menos 6 caracteres.", Alert.AlertType.WARNING);
            return;
        }

        if (usuarioDAO.existeUsuario(usuario)) {
            AlertaUtil.mostrarAlerta("AVISO:", null, "El nombre de usuario ya está en uso.", Alert.AlertType.WARNING);
            return;
        }

        if (!contraseña.equals(confirmarContraseña)) {
            AlertaUtil.mostrarAlerta("AVISO:", null, "Las contraseñas no coinciden.", Alert.AlertType.WARNING);
            return;
        }

        boolean exito = usuarioDAO.registrarUsuario(usuario, contraseña);

        if (exito) {
            AlertaUtil.mostrarAlerta("Registro exitoso!", null, "Tu cuenta fue creada correctamente.", Alert.AlertType.CONFIRMATION);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/Login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) txtUsuario.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Iniciar Sesión - Cinemotion");
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
    private void irALogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/fxml/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar Sesión - Cinemotion");
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
