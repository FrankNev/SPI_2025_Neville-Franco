package Modelo.Dao;

import Modelo.Entidades.Pelicula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    // Obtener todas las películas de la BD
    public List<Pelicula> obtenerPeliculas() {
        List<Pelicula> lista = new ArrayList<>();
        String query = "SELECT id_pelicula, titulo, duracion, director, genero, fecha_estreno, url_img FROM Pelicula";

        try (Connection conn = ConexionABaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pelicula p = new Pelicula();
                p.setId(rs.getInt("id_pelicula"));
                p.setTitulo(rs.getString("titulo"));
                p.setDuracion(rs.getString("duracion"));
                p.setDirector(rs.getString("director"));
                p.setGenero(rs.getString("genero"));
                p.setFechaEstreno(rs.getDate("fecha_estreno").toLocalDate());
                p.setUrlImg(rs.getString("url_img"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener películas: " + e.getMessage());
        }
        return lista;
    }

    // Insertar nueva película
    public boolean agregarPelicula(Pelicula pelicula) {
        String query = "INSERT INTO Pelicula (titulo, duracion, director, genero, fecha_estreno, url_img) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionABaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getDuracion());
            stmt.setString(3, pelicula.getDirector());
            stmt.setString(4, pelicula.getGenero());
            stmt.setDate(5, Date.valueOf(pelicula.getFechaEstreno()));
            stmt.setString(6, pelicula.getUrlImg());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar película: " + e.getMessage());
            return false;
        }
    }

    // Actualizar película existente
    public boolean actualizarPelicula(Pelicula pelicula) {
        String query = "UPDATE Pelicula SET titulo=?, duracion=?, director=?, genero=?, fecha_estreno=?, url_img=? WHERE id_pelicula=?";

        try (Connection conn = ConexionABaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, pelicula.getTitulo());
            stmt.setString(2, pelicula.getDuracion());
            stmt.setString(3, pelicula.getDirector());
            stmt.setString(4, pelicula.getGenero());
            stmt.setDate(5, Date.valueOf(pelicula.getFechaEstreno()));
            stmt.setString(6, pelicula.getUrlImg());
            stmt.setInt(7, pelicula.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar película: " + e.getMessage());
            return false;
        }
    }

    // Eliminar película
    public boolean eliminarPelicula(int id) {
        String query = "DELETE FROM Pelicula WHERE id_pelicula=?";

        try (Connection conn = ConexionABaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar película: " + e.getMessage());
            return false;
        }
    }
}
