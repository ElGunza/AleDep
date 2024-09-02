<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aledep.model.Venta"%>
<%@ page import="aledep.model.Cliente"%>
<%@ page import="aledep.model.Usuario"%>
<%@ page import="aledep.model.MetodoPago"%>
<%@ page import="aledep.model.Producto"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
	<!-- Mensajes de éxito y error -->
	<div id="successMessage" class="alert alert-success"
		style="display: none;"></div>
	<div id="errorMessage" class="alert alert-danger"
		style="display: none;"></div>

	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Ventas Registradas</h6>
			<a href="#" class="btn btn-primary btn-circle" id="btnRegistrarVenta"
				data-toggle="modal" data-target="#registrarVentaModal"> <i
				class="fas fa-plus"></i>
			</a>
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th>Cliente</th>
							<th>Usuario</th>
							<th>Método de Pago</th>
							<th>Fecha de Creación</th>
							<th>Precio Total</th>
						</tr>
					</thead>
					<tbody>
						<%
                        List<Venta> listaVentas = (List<Venta>) request.getSession().getAttribute("listaVentas");
                        if (listaVentas == null) {
                            listaVentas = new ArrayList<>(); // Inicializa la lista para evitar null
                        }

                        if (!listaVentas.isEmpty()) {
                            for (Venta venta : listaVentas) {
                        %>
						<tr data-id="<%=venta.getIdVenta()%>">
							<td><%=venta.getCliente().getNombre()%></td>
							<td><%=venta.getCliente().getNombre()%><td> 
							<td><%=venta.getMetodoPago().getNombre()%></td>
							<td><%=venta.getFechaCreacion()%></td>
							<td>$ <%=String.format("%.2f", venta.getPrecioTotal())%></td>
						</tr>
						<%
                        }
                        } else {
                        %>
						<tr>
							<td colspan="5">No hay ventas registradas</td>
						</tr>
						<%
                        }
                        %>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<%@ include file="components/footer.jsp"%>

	<!-- Modal para Registrar Venta -->

	<!-- tabindex="-1" -->

	<div class="modal fade" id="registrarVentaModal" role="dialog"
		aria-labelledby="registrarVentaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="registrarVentaModalLabel">Registrar
						Nueva Venta</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formRegistrarVenta" action="registrarVenta" method="post">
					<div class="modal-body">
						<div class="row">
							<!-- Primera columna -->
							<div class="col-md-6">
								<div class="form-group">
									<label for="clienteId" class="required">Cliente</label> <select
										class="form-control select2" id="clienteId" name="clienteId"
										required>
										<option value="">Seleccionar Cliente</option>
										<%
                                        List<Cliente> clientes = (List<Cliente>) request.getSession().getAttribute("listaClientes");
                                        if (clientes != null) {
                                            for (Cliente cliente : clientes) {
                                        %>
										<option value="<%=cliente.getIdCliente()%>">
											<%=cliente.getNombre()%>
										</option>
										<%
                                        }
                                        }
                                        %>
									</select>
								</div>
								<div class="form-group">
									<label for="metodoPagoId" class="required">Método de
										Pago</label> <select class="form-control select2" id="metodoPagoId"
										name="metodoPagoId" required>
										<option value="">Seleccionar Método de Pago</option>
										<%
                                        List<MetodoPago> metodosPago = (List<MetodoPago>) request.getSession().getAttribute("listaMetodosPago");
                                        if (metodosPago != null) {
                                            for (MetodoPago metodoPago : metodosPago) {
                                        %>
										<option value="<%=metodoPago.getIdMetPago()%>">
											<%=metodoPago.getNombre()%>
										</option>
										<%
                                        }
                                        }
                                        %>
									</select>
								</div>
							</div>
							<!-- Segunda columna -->
							<div class="col-md-6">
								<h5>Productos</h5>
								<div id="productsContainer">
									<!-- Aquí se agregarán las filas de productos -->
								</div>
								<button type="button" class="btn btn-secondary"
									onclick="addProductRow()">Agregar Producto</button>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Registrar
							Venta</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
        $(document).ready(function() {
            // Inicializar Select2 en todos los selects
            $('.select2').select2({
                width: '100%',
                placeholder: "Seleccionar una opción",
                allowClear: true
            });

            // Manejar el envío del formulario de registro de venta
            $('#formRegistrarVenta').on('submit', function(event) {
                event.preventDefault();

                $.ajax({
                    url: $(this).attr('action'), // URL del servlet
                    type: 'POST',
                    data: $(this).serialize(),
                    success: function(response) {
                        if (response.status === 'success') {
                            // Mostrar mensaje de éxito
                            $('#successMessage').text(response.message).fadeIn().delay(3000).fadeOut();

                            // Limpiar el formulario
                            $('#formRegistrarVenta')[0].reset();
                            $('.select2').val(null).trigger('change'); // Reset Select2

                            // Cerrar el modal
                            $('#registrarVentaModal').modal('hide');

                            // Recargar la tabla de ventas para reflejar los nuevos datos
                            location.reload(); // Si deseas recargar la tabla
                        } else {
                            // Mostrar mensaje de error si algo salió mal
                            $('#errorMessage').text('Hubo un problema al registrar la venta.').fadeIn().delay(3000).fadeOut();
                        }
                    },
                    error: function() {
                        // Manejar cualquier error inesperado
                        $('#errorMessage').text('Ocurrió un error inesperado.').fadeIn().delay(3000).fadeOut();
                    }
                });
            });
        });

        // Agregar productos dinámicamente
        function addProductRow() {
            var row = document.createElement("div");
            row.classList.add("form-group");

            row.innerHTML = `
                <label for="productoId" class="required">Producto</label>
                <select class="productselect form-control" name="productoId" required>
                    <%
                    List<Producto> productos = (List<Producto>) request.getSession().getAttribute("listaProductos");
                    if (productos != null) {
                        for (Producto producto : productos) {
                    %>
                    <option value="<%=producto.getIdProducto()%>">
                        <%=producto.getNombre()%>
                    </option>
                    <%}
}%>
                </select>
                <label for="cantidad" class="required">Cantidad</label>
                <input type="number" class="form-control" name="cantidad" value="1" min="1" required>
                <button type="button" class="btn btn-danger mt-2" onclick="removeProductRow(this)">Eliminar</button>
                <hr>`;

            document.getElementById("productsContainer").appendChild(row);

            // Re-inicializar Select2 en los nuevos selects añadidos
            $('.productselect').select2({
                width: '100%',
                placeholder: "Seleccionar una opción",
            });
        }

        // Remover producto
        function removeProductRow(button) {
            var row = button.parentNode;
            document.getElementById("productsContainer").removeChild(row);
        }
    </script>
</div>
<%@ include file="components/footer.jsp"%>
