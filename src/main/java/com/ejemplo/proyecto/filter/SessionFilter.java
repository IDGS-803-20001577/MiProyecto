package com.ejemplo.proyecto.filter;

import com.ejemplo.proyecto.model.Usuario;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filtro que protege páginas para que solo usuarios con sesión puedan acceder.
 */
@WebFilter("/*") // Aplica a todas las URLs
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Páginas públicas que no requieren login
        if (path.startsWith("/login") || path.equals("/login")) {
            chain.doFilter(request, response); // Permite el acceso
            return;
        }

        // Verifica si hay un usuario en sesión
        HttpSession session = req.getSession(false);
        Usuario u = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (u == null) {
            // No hay usuario en sesión → redirige al login
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            // Usuario existe → continúa con la petición
            chain.doFilter(request, response);
        }
    }
}
