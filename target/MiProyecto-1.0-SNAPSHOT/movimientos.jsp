<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ejemplo.proyecto.model.Producto" %>
<%@ page import="com.ejemplo.proyecto.model.HistoricoInventario" %>
<%@ page import="com.ejemplo.proyecto.dao.ProductoDAO" %>
<%@ page import="com.ejemplo.proyecto.dao.HistoricoInventarioDAO" %>

<%
    // Obtener lista de productos
    ProductoDAO productoDAO = new ProductoDAO();
    List<Producto> listaProductos = productoDAO.listaProductosActivos();

    // Obtener historial de movimientos
    HistoricoInventarioDAO historicoDAO = new HistoricoInventarioDAO();
    List<HistoricoInventario> listaHistorico = historicoDAO.listaHistorico();

    // Recuperar usuario
    com.ejemplo.proyecto.model.Usuario usuario
            = (com.ejemplo.proyecto.model.Usuario) session.getAttribute("usuario");
%>

<!-- Bootstrap CSS y JS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<h2 class="text-center my-4">Movimientos de Inventario</h2>

<!-- Tabla de productos -->
<h3 class="text-center mb-3">Registrar Salida</h3>
<div style="text-align:center; margin-top:20px;">
    <a href="index.jsp" class="btn btn-secondary">Regresar al inicio</a>
</div>
<br>
<div class="table-responsive">
    <table class="table table-bordered table-striped text-center" style="width:80%; margin:auto;">
        <thead class="table-primary">
            <tr>
                <th>ID</th>
                <th>Producto</th>
                <th>Cantidad Disponible</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
            <% for (Producto p : listaProductos) {%>
            <tr>
                <td><%= p.getIdProducto()%></td>
                <td><%= p.getNombre()%></td>
                <td><%= p.getCantidad()%></td>
                <td>
                    <button class="btn btn-danger" 
                            onclick="abrirModal(<%= p.getIdProducto()%>, '<%= p.getNombre()%>', <%= p.getCantidad()%>)">
                        Registrar Salida
                    </button>
                </td>
            </tr>
            <% }%>
        </tbody>
    </table>
</div>

<!-- Modal Bootstrap -->
<div class="modal fade" id="salidaModal" tabindex="-1" aria-labelledby="salidaModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="salidaModalLabel">Registrar Salida</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
            </div>
            <div class="modal-body">
                <p id="productoNombre"></p>
                <div class="mb-3">
                    <label for="cantidadSalida" class="form-label">Cantidad a retirar:</label>
                    <input type="number" id="cantidadSalida" class="form-control" min="1">
                    <div id="maxCantidad" class="form-text text-danger"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" onclick="enviarSalida()">Registrar</button>
            </div>
        </div>
    </div>
</div>

<script>
    let productoIdActual = 0;
    let cantidadMaxima = 0;

    function abrirModal(idProducto, nombreProducto, cantidadDisponible) {
        productoIdActual = idProducto;
        cantidadMaxima = cantidadDisponible;
        document.getElementById('productoNombre').innerText = "Producto: " + nombreProducto;
        document.getElementById('cantidadSalida').value = '';
        document.getElementById('maxCantidad').innerText = "Máximo disponible: " + cantidadDisponible;

        // Mostrar modal con Bootstrap
        var modal = new bootstrap.Modal(document.getElementById('salidaModal'));
        modal.show();
    }

    function enviarSalida() {
        let cantidad = parseInt(document.getElementById('cantidadSalida').value);
        if (cantidad > 0 && cantidad <= cantidadMaxima) {
            const form = document.createElement('form');
            form.method = 'post';
            form.action = 'salidas'; // Tu servlet que procesa la salida

            const inputId = document.createElement('input');
            inputId.type = 'hidden';
            inputId.name = 'idProducto';
            inputId.value = productoIdActual;
            form.appendChild(inputId);

            const inputCantidad = document.createElement('input');
            inputCantidad.type = 'hidden';
            inputCantidad.name = 'cantidad';
            inputCantidad.value = cantidad;
            form.appendChild(inputCantidad);

            document.body.appendChild(form);
            form.submit();
        } else {
            alert("Cantidad inválida o mayor a la disponible.");
        }
    }
</script>
