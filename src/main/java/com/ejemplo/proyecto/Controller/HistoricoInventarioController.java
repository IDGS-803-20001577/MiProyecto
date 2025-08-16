/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ejemplo.proyecto.Controller;

import com.ejemplo.proyecto.dao.HistoricoInventarioDAO;
import com.ejemplo.proyecto.model.HistoricoInventario;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author gayta
 */
@WebServlet("/inventario")
public class HistoricoInventarioController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Listar productos
        HistoricoInventarioDAO dao = new HistoricoInventarioDAO();
        List<HistoricoInventario> lista = dao.listaHistorico();
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("historico.jsp").forward(request, response);
    }
}
