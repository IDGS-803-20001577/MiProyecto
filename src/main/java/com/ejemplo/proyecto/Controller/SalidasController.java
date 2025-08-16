package com.ejemplo.proyecto.Controller;

import com.ejemplo.proyecto.config.Conexion;
import com.ejemplo.proyecto.model.HistoricoInventario;
import com.ejemplo.proyecto.dao.HistoricoInventarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

@WebServlet("/salidas")
public class SalidasController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidadSalida = Integer.parseInt(request.getParameter("cantidad"));

        // Recuperar usuario de sesión
        HttpSession session = request.getSession();
        int idUsuario = 0;

        if (session.getAttribute("usuario") != null) {
            idUsuario = ((com.ejemplo.proyecto.model.Usuario) session.getAttribute("usuario")).getIdUsuario();
        }
        // 1. Registrar la salida en la tabla de inventario (actualizar cantidad)
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement("UPDATE productos SET cantidad = cantidad - ? WHERE idProducto = ?")) {
            ps.setInt(1, cantidadSalida);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Insertar registro en Historico_Inventario
        HistoricoInventarioDAO historicoDAO = new HistoricoInventarioDAO();
        HistoricoInventario h = new HistoricoInventario();
        h.setIdProducto(idProducto);
        h.setCantidad(cantidadSalida);
        h.setAccion("Salida");
        h.setFechaMovimiento(new Date());
        h.setIdUsuario(idUsuario);

        historicoDAO.insertarHistorico(h);

        // Redirigir de nuevo a la página de inventario o mostrar mensaje
        response.sendRedirect("productos.jsp");
    }
}
