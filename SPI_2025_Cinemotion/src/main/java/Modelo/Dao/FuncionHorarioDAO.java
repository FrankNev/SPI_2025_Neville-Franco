package Modelo.Dao;

import Modelo.Entidades.Formato;
import Modelo.Entidades.Funcion;
import Modelo.Entidades.FuncionHorario;
import Modelo.Entidades.Horario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionHorarioDAO {

    public List<Formato> obtenerFormatosPorFuncion(int idFuncion) {
        String query = "SELECT DISTINCT f.id_formato, fo.tipo_formato, fo.valor_entrada "
                + "FROM FuncionHorario f "
                + "JOIN Formato fo ON f.id_formato = fo.id_formato "
                + "WHERE f.id_funcion = ? AND f.esta_disponible = true";

        List<Formato> formatos = new ArrayList<>();
        try (Connection conn = ConexionABaseDeDatos.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idFuncion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                formatos.add(new Formato(
                        rs.getInt("id_formato"),
                        rs.getString("tipo_formato"),
                        rs.getInt("valor_entrada")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formatos;
    }

    public List<Horario> obtenerHorariosPorFuncionYFormato(int idFuncion, int idFormato) {
        String query = "SELECT h.id_horario, h.hora_funcion "
                + "FROM FuncionHorario fh "
                + "JOIN Horario h ON fh.id_horario = h.id_horario "
                + "WHERE fh.id_funcion = ? AND fh.id_formato = ? AND fh.esta_disponible = true";

        List<Horario> horarios = new ArrayList<>();
        try (Connection conn = ConexionABaseDeDatos.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idFuncion);
            stmt.setInt(2, idFormato);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                horarios.add(new Horario(
                        rs.getInt("id_horario"),
                        rs.getTime("hora_funcion").toLocalTime()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    public FuncionHorario obtenerFuncionHorario(int idFuncion, int idFormato, int idHorario) {
        String query = "SELECT fh.id_funcion_horario, fh.ocupacion, fh.esta_disponible, " +
                     "f.id_funcion, f.id_pelicula, f.fecha, f.esta_disponible AS funcion_disponible, " +
                     "fo.id_formato, fo.tipo_formato, fo.valor_entrada, " +
                     "h.id_horario, h.hora_funcion " +
                     "FROM FuncionHorario fh " +
                     "JOIN Funcion f ON fh.id_funcion = f.id_funcion " +
                     "JOIN Formato fo ON fh.id_formato = fo.id_formato " +
                     "JOIN Horario h ON fh.id_horario = h.id_horario " +
                     "WHERE fh.id_funcion = ? AND fh.id_formato = ? AND fh.id_horario = ?";

        try (Connection conn = ConexionABaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idFuncion);
            stmt.setInt(2, idFormato);
            stmt.setInt(3, idHorario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Funcion funcion = new Funcion(
                    rs.getInt("id_funcion"),
                    rs.getInt("id_pelicula"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getBoolean("funcion_disponible")
                );

                Formato formato = new Formato(
                    rs.getInt("id_formato"),
                    rs.getString("tipo_formato"),
                    rs.getInt("valor_entrada")
                );

                Horario horario = new Horario(
                    rs.getInt("id_horario"),
                    rs.getTime("hora_funcion").toLocalTime()
                );

                return new FuncionHorario(
                    rs.getInt("id_funcion_horario"),
                    funcion,
                    formato,
                    horario,
                    rs.getInt("ocupacion"),
                    rs.getBoolean("esta_disponible")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarOcupacion(int idFuncionHorario, int cantidad) {
        String query = "UPDATE FuncionHorario SET ocupacion = ocupacion + ? WHERE id_funcion_horario = ?";

        try (Connection conn = ConexionABaseDeDatos.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, cantidad);
            stmt.setInt(2, idFuncionHorario);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
