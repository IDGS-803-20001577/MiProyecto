package com.ejemplo.proyecto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ejemplo.proyecto.config.Conexion;
import com.ejemplo.proyecto.model.Usuario;

public class UsuarioDAO {
    /**
     * Valida el usuario y contraseña contra la base de datos.
     * 
     * @param usuario    nombre de usuario
     * @param contrasena contraseña
     * @return Usuario si existe, null si no existe o credenciales incorrectas
     */

    public Usuario validarUsuario(String correo, String contrasena) {
        Usuario u = null;

        String sql = "SELECT idUsuario, nombre, correo, contrasena, idRol, estatus " +
                "FROM usuarios " +
                "WHERE correo = ? AND contrasena = ?";

        try (Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasena);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setCorreo(rs.getString("correo"));
                    u.setContrasena(rs.getString("contrasena"));
                    u.setIdRol(rs.getInt("idRol"));
                    u.setEstatus(rs.getInt("estatus"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }
}
