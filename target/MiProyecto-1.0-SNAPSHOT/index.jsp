<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ejemplo.proyecto.model.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Panel Principal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            display: flex;
        }
        .sidebar {
            width: 220px;
            background-color: #2c3e50;
            color: #fff;
            min-height: 100vh;
            padding: 20px;
        }
        .sidebar h2 {
            text-align: center;
            font-size: 18px;
            margin-bottom: 20px;
        }
        .sidebar a {
            display: block;
            color: #fff;
            text-decoration: none;
            padding: 10px 5px;
            margin: 5px 0;
            border-radius: 4px;
        }
        .sidebar a:hover {
            background-color: #34495e;
        }
        .content {
            flex: 1;
            padding: 20px;
            background-color: #ecf0f1;
        }
    </style>
</head>
<body>

<div class="sidebar">
    <h2>Bienvenido, <%= usuario.getNombre() %></h2>
    <hr>
    <h3>Módulos</h3>
    <%
        int rol = usuario.getIdRol();
        if (rol == 1) { // Administrador
    %>
        <a href="productos.jsp">Ver Inventario</a>
        <a href="historico.jsp">Histórico de Movimientos</a>
    <%
        } else if (rol == 2) { // Almacenista
    %>
        <a href="productos.jsp">Ver Inventario</a>
        <a href="movimientos.jsp">Salida de Productos</a>
    <%
        }
    %>
    <hr>
    <a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a>
</div>

<div class="content">
    <h1>Panel Principal</h1>
    <p>Selecciona un módulo del menú lateral para comenzar.</p>
</div>

</body>
</html>
