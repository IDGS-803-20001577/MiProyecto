package com.ejemplo.proyecto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.proyecto.config.Conexion;
import com.ejemplo.proyecto.model.Producto;

public class ProductoDAO {

    public List<Producto> listaProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT idProducto, nombre, cantidad, estatus FROM productos";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setEstatus(rs.getInt("estatus"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Producto> listaProductosActivos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT idProducto, nombre, cantidad, estatus FROM productos WHERE estatus = 1";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("idProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setEstatus(rs.getInt("estatus"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Agregar producto (cantidad inicial = 0)
    public boolean agregarProducto(String nombre) {
        String sql = "INSERT INTO productos (nombre, cantidad, estatus) VALUES (?, 0, 1)";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // Aumentar inventario
    public boolean aumentarInventario(int idProducto, int cantidad) {
        String sql = "UPDATE productos SET cantidad = cantidad + ? WHERE idProducto=?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cambiarEstatus(int idProducto, int estatus) {
        String sql = "UPDATE productos SET estatus = ? WHERE idProducto = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estatus);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Disminuir inventario (salida)
    public boolean disminuirInventario(int idProducto, int cantidad) {
        String sql = "UPDATE productos SET cantidad = cantidad - ? WHERE idProducto=? AND cantidad >= ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener producto por id
    public Producto obtenerProducto(int idProducto) {
        Producto p = null;
        String sql = "SELECT idProducto, nombre, cantidad, estatus FROM productos WHERE idProducto=?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Producto();
                    p.setIdProducto(rs.getInt("idProducto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setCantidad(rs.getInt("cantidad"));
                    p.setEstatus(rs.getInt("estatus"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public int obtenerIdPorNombre(String nombre) {
        String sql = "SELECT idProducto FROM productos WHERE nombre = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("idProducto");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}
