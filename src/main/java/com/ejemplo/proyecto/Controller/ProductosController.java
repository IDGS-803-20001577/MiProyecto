package com.ejemplo.proyecto.Controller;

import com.ejemplo.proyecto.dao.HistoricoInventarioDAO;
import java.io.IOException;
import java.util.List;

import com.ejemplo.proyecto.dao.ProductoDAO;
import com.ejemplo.proyecto.model.HistoricoInventario;
import com.ejemplo.proyecto.model.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productos")
public class ProductosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Listar productos
        ProductoDAO dao = new ProductoDAO();
        List<Producto> lista = dao.listaProductos();
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductoDAO dao = new ProductoDAO();
        HistoricoInventarioDAO hdao = new HistoricoInventarioDAO();
        String accion = request.getParameter("accion");

        try {
            if ("agregar".equals(accion)) {
                String nombre = request.getParameter("nombre");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    boolean insertado = dao.agregarProducto(nombre.trim());
                    if (insertado) {
                        // Buscar el ID del producto recién insertado
                        int idProducto = dao.obtenerIdPorNombre(nombre.trim());

                        // Crear objeto historico
                        HistoricoInventario h = new HistoricoInventario();
                        h.setIdProducto(idProducto);
                        h.setAccion("Entrada");   // Puedes usar texto o enumeración
                        h.setCantidad(0);         // porque al inicio siempre es 0
                        h.setFechaMovimiento(new java.util.Date());
                        h.setIdUsuario(1);        // aquí debes meter el usuario logueado

                    }
                }
            } else if ("aumentar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                dao.aumentarInventario(id, cantidad);

                //Historico
                HistoricoInventario h = new HistoricoInventario();
                hdao.insertarHistorico(h);
                h.setIdProducto(id);
                h.setAccion("AUMENTAR");
                h.setCantidad(cantidad);
                h.setFechaMovimiento(new java.util.Date());
                h.setIdUsuario(1); // usuario de sesión
                hdao.insertarHistorico(h);

            } else if ("cambiarEstatus".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                int estatus = Integer.parseInt(request.getParameter("estatus"));
                dao.cambiarEstatus(id, estatus);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error: ID o cantidad no son números válidos.");
            request.setAttribute("tipo", "error");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error inesperado: " + e.getMessage());
            request.setAttribute("tipo", "error");
        }

        // Al final, recargar la lista
        List<Producto> lista = dao.listaProductos();
        request.setAttribute("lista", lista);
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

}
