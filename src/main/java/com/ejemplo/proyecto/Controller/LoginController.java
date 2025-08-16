package com.ejemplo.proyecto.Controller;

import java.io.IOException;

import com.ejemplo.proyecto.dao.UsuarioDAO;
import com.ejemplo.proyecto.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login") // Ruta que maneja este servlet
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        // Validar que no estén vacíos
        if (correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            request.setAttribute("error", "Usuario y contraseña son obligatorios");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Validar usuario
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.validarUsuario(correo, contrasena);

        if (u != null) {
            // Crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", u); // Guarda todo el objeto usuario

            // Redirigir a index (puede ser un JSP o un servlet)
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            // Usuario inválido, volver a login
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Si alguien accede a /login por GET, lo enviamos al login.jsp
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
