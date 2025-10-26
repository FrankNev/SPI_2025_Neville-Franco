package Modelo.Servicios;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertaUtil {

    public static void mostrarAlerta(String titulo, String encabezado, String contenido, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
