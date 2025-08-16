package com.ejemplo.proyecto.dao;

import com.ejemplo.proyecto.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import com.ejemplo.proyecto.model.HistoricoInventario;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author gayta
 */
public class HistoricoInventarioDAO {

    public List<HistoricoInventario> listaHistorico() {
        List<HistoricoInventario> lista = new ArrayList<>();
        String sql = "SELECT h.idMovimiento, h.idProducto, p.nombre as nombreProducto, h.accion, "
                + "h.cantidad, h.fecha_Movimiento, h.idUsuario, u.nombre as nombreUsuario "
                + "FROM Historico_Inventario h "
                + "INNER JOIN productos p ON h.idProducto = p.idProducto "
                + "INNER JOIN usuarios u ON u.idUsuario = h.idUsuario "
                + "ORDER BY h.fecha_Movimiento DESC";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HistoricoInventario h = new HistoricoInventario();
                h.setIdMovimiento(rs.getInt("idMovimiento"));
                h.setIdProducto(rs.getInt("idProducto"));
                h.setNombreProducto(rs.getString("nombreProducto"));
                h.setAccion(rs.getString("accion"));
                h.setCantidad(rs.getInt("cantidad"));
                h.setFechaMovimiento(rs.getTimestamp("fecha_Movimiento"));
                h.setIdUsuario(rs.getInt("idUsuario"));
                h.setNombreUsuario(rs.getString("nombreUsuario"));

                lista.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarHistorico(HistoricoInventario h) {
        String sql = "INSERT INTO Historico_Inventario(idProducto, accion, cantidad, fecha_Movimiento, idUsuario) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, h.getIdProducto());
            ps.setString(2, h.getAccion());
            ps.setInt(3, h.getCantidad());
            ps.setTimestamp(4, new java.sql.Timestamp(h.getFechaMovimiento().getTime()));
            ps.setInt(5, h.getIdUsuario());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
