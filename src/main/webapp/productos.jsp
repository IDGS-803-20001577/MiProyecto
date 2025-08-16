<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.ejemplo.proyecto.dao.ProductoDAO" %>
<%@ page import="com.ejemplo.proyecto.model.Producto" %>
<%@ page import="java.util.List" %>

<%
    ProductoDAO dao = new ProductoDAO();
    List<Producto> lista = dao.listaProductos();

    String mensaje = request.getAttribute("mensaje") != null ? (String) request.getAttribute("mensaje") : "";
    String tipo = request.getAttribute("tipo") != null ? (String) request.getAttribute("tipo") : "";

    com.ejemplo.proyecto.model.Usuario usuario
            = (com.ejemplo.proyecto.model.Usuario) session.getAttribute("usuario");
    int rol = 0;
    if (usuario != null) {
        rol = usuario.getIdRol();
    }
%>

<!-- Bootstrap CSS y JS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<h2 class="text-center my-4">Inventario de Productos</h2>

<!-- Mensaje -->
<% if (!mensaje.isEmpty()) {%>
<div class="alert <%= "success".equals(tipo) ? "alert-success" : "alert-danger"%> text-center">
    <%= mensaje%>
</div>
<% } %>

<!-- Solo administradores pueden agregar productos -->
<% if (rol == 1) { %>
<div class="text-center mb-3">
    <form action="productos" method="post" class="d-inline-flex align-items-center justify-content-center gap-2">
        <input type="hidden" name="accion" value="agregar">
        <input type="text" name="nombre" class="form-control" placeholder="Nombre del producto" required style="max-width:250px;">
        <button type="submit" class="btn btn-primary">Agregar Producto</button>
    </form>
</div>
<% } %>
<div style="text-align:center; margin-top:20px;">
    <a href="index.jsp" class="btn btn-secondary">Regresar al inicio</a>
</div>
<br>
<div class="table-responsive">
    <table class="table table-bordered table-striped text-center">
        <thead class="table-primary">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Cantidad</th>
                <th>Estatus</th>
                <% if (rol == 1) { %><th>Acciones</th><% } %>
            </tr>
        </thead>
        <tbody>
            <% for (Producto p : lista) {%>
            <tr>
                <td><%= p.getIdProducto()%></td>
                <td><%= p.getNombre()%></td>
                <td><%= p.getCantidad()%></td>
                <td><%= (p.getEstatus() == 1 ? "Activo" : "Inactivo")%></td>

                <% if (rol == 1) {%>
                <td>
                    <!-- Cambiar Estatus -->
                    <form action="productos" method="post" class="d-inline">
                        <input type="hidden" name="accion" value="cambiarEstatus">
                        <input type="hidden" name="id" value="<%= p.getIdProducto()%>">
                        <input type="hidden" name="estatus" value="<%= (p.getEstatus() == 1 ? 0 : 1)%>">

                        <button type="submit" class="btn <%= (p.getEstatus() == 1 ? "btn-danger" : "btn-success")%>">
                            <%= (p.getEstatus() == 1 ? "Dar de baja" : "Reactivar")%>
                        </button>
                    </form>

                    <!-- Botón aumentar inventario -->
                    <button type="button" class="btn btn-warning" onclick="abrirModalAumentar(<%= p.getIdProducto()%>)">
                        Aumentar
                    </button>
                </td>
                <% } %>
            </tr>
            <% }%>
        </tbody>
    </table>
</div>

<!-- Modal para aumentar inventario -->
<div class="modal fade" id="modalAumentar" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form method="post" action="productos">
                <div class="modal-header">
                    <h5 class="modal-title">Aumentar Inventario</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="accion" value="aumentar">
                    <input type="hidden" name="id" id="idProducto">

                    <label for="cantidad" class="form-label">Cantidad a aumentar:</label>
                    <input type="number" class="form-control" name="cantidad" id="cantidad" required min="1">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function abrirModalAumentar(id) {
        document.getElementById("idProducto").value = id;
        let modal = new bootstrap.Modal(document.getElementById("modalAumentar"));
        modal.show();
    }
</script>
