package Modelo.Dao;

import Modelo.Entidades.Funcion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionDAO {

    public List<Funcion> obtenerFuncionesPorPelicula(int idPelicula) {
        List<Funcion> funciones = new ArrayList<>();
        String sql = "SELECT * FROM Funcion WHERE id_pelicula = ? AND esta_disponible = true";

        try (Connection conn = ConexionABaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPelicula);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcion f = new Funcion(
                    rs.getInt("id_funcion"),
                    rs.getInt("id_pelicula"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getBoolean("esta_disponible")
                );
                funciones.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funciones;
    }
}
