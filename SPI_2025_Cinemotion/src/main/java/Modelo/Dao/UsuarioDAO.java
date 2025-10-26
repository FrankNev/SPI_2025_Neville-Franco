package Modelo.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modelo.Entidades.Usuario;

public class UsuarioDAO {

    public Usuario obtenerUsuarioPorCredenciales(String nombre, String contraseña) {
        String query = "SELECT * FROM Usuario WHERE nombre_usuario = ? AND contrasena = ?";

        try (Connection conn = ConexionABaseDeDatos.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombre);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombreUsuario(rs.getString("nombre_usuario"));
                u.setTipoUsuario(rs.getString("tipo_usuario"));
                return u;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }

    public boolean existeUsuario(String usuario) {
        String query = "SELECT COUNT(*) FROM Usuario WHERE nombre_usuario = ?";
        try (Connection conn = ConexionABaseDeDatos.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registrarUsuario(String nombre, String contraseña) {
        String query = "INSERT INTO Usuario (nombre_usuario, contrasena) VALUES (?, ?)";

        try (Connection conn = ConexionABaseDeDatos.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombre);
            stmt.setString(2, contraseña);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
}
