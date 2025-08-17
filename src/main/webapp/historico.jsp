<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ejemplo.proyecto.dao.HistoricoInventarioDAO" %>
<%@ page import="com.ejemplo.proyecto.model.HistoricoInventario" %>
<%@ page import="java.util.List" %>

<%
    String productoFiltro = request.getParameter("producto") != null ? request.getParameter("producto") : "";
    String fechaFiltro = request.getParameter("fecha") != null ? request.getParameter("fecha") : "";

    HistoricoInventarioDAO dao = new HistoricoInventarioDAO();
    List<HistoricoInventario> lista;
    if (!productoFiltro.isEmpty() || !fechaFiltro.isEmpty()) {
        lista = dao.listaHistoricoFiltrado(productoFiltro, fechaFiltro);
    } else {
        lista = dao.listaHistorico();
    }
%>

<!-- Bootstrap CSS y JS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<h2 class="text-center my-4">Histórico de Inventario</h2>

<div class="text-center mb-3">
    <a href="index.jsp" class="btn btn-secondary">Regresar al inicio</a>
</div>

<!-- Formulario de filtros -->
<div class="container mb-4">
    <form method="get" class="row g-3 justify-content-center">
        <div class="col-auto">
            <input type="text" class="form-control" name="producto" placeholder="Nombre del producto"
                   value="<%= productoFiltro%>">
        </div>
        <div class="col-auto">
            <input type="date" class="form-control" name="fecha" value="<%= fechaFiltro%>">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary">Filtrar</button>
        </div>
        <div class="col-auto">
            <a href="historico.jsp" class="btn btn-secondary">Limpiar</a>
        </div>
    </form>
</div>

<div class="table-responsive">
    <table class="table table-bordered table-striped text-center">
        <thead class="table-primary">
            <tr>
                <th>ID</th>
                <th>Producto</th>
                <th>Acción</th>
                <th>Cantidad</th>
                <th>Fecha</th>
                <th>Usuario</th>
            </tr>
        </thead>
        <tbody>
            <% for (HistoricoInventario h : lista) {%>
            <tr>
                <td><%= h.getIdMovimiento()%></td>
                <td><%= h.getNombreProducto()%></td>
                <td><%= h.getAccion()%></td>
                <td><%= h.getCantidad()%></td>
                <td><%= h.getFechaMovimiento()%></td>
                <td><%= h.getNombreUsuario()%></td>
            </tr>
            <% } %>
            <% if (lista.isEmpty()) { %>
            <tr>
                <td colspan="6">No se encontraron registros</td>
            </tr>
            <% }%>
        </tbody>
    </table>
</div>
