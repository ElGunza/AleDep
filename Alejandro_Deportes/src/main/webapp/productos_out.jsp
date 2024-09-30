<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aledep.model.Producto"%>
<%@ page import="aledep.model.Categoria"%>
<%@ page import="aledep.model.Marca"%>
<%@ page import="aledep.model.Proveedor"%>
<%@ page import="aledep.model.Deposito"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>
<div class="container-fluid">
	<!-- <h1 class="h3 mb-2 text-gray-800">Inventario</h1> -->
	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Productos de
				Alejandro Deportes</h6>
		</div>
		<div class="card-body">
			<div class="filter-options mb-3" style="float: left">
				<a href="productos?filtro=activos" class="btn btn-primary">Mostrar
					Activos</a> <a href="productos?filtro=todos" class="btn btn-secondary">Mostrar
					Todos</a>
			</div>
			<div style="float: right">
				<!-- Botón para abrir el modal de alta de producto -->
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnAltaProducto" data-toggle="modal"
					data-target="#altaProductoModal"> <i class="fas fa-plus"></i>
				</a>
				<!-- Botón para abrir el modal de edición de producto -->
				<a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarProducto"> <i class="fas fa-edit"></i>
				</a>
				<!-- Botón para eliminar producto -->
				<a href="#" class="btn btn-danger btn-circle"
					id="btnEliminarProducto"> <i class="fas fa-trash-alt"></i>
				</a>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTableProduct" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th style="display: none;">ID</th>
							<th>Código</th>
							<th>Categoría</th>
							<th>Nombre</th>
							<th>Marca</th>
							<th>Talles disponibles</th>
							<th>Cantidad</th>
							<th>Precio de venta</th>
							<th>Activo</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<Producto> listaProductos = (List<Producto>) request.getSession().getAttribute("listaProductos");
						if (listaProductos == null) {
							listaProductos = new ArrayList<>(); // Inicializa la lista para evitar null
						}

						if (!listaProductos.isEmpty()) {
							for (Producto producto : listaProductos) {
						%>
						<tr data-id="<%=producto.getIdProducto()%>">
							<td style="display: none;"><%=producto.getIdProducto()%></td>
							<td><%=producto.getCodigo()%></td>
							<td><%=producto.getCategoria() != null ? producto.getCategoria().getNombre() : "Sin categoría"%>
							</td>
							<td><%=producto.getNombre()%></td>
							<td><%=producto.getMarca() != null ? producto.getMarca().getNombre() : "Sin Marca"%>
							</td>
							<td><%=producto.getTalle()%></td>
							<td><%=producto.getCantidad()%></td>
							<td>$ <%=String.format("%.2f", producto.getPrecioVenta())%>
							<td><%=producto.getActivo() ? "Sí" : "No"%></td>
							</td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="7">No hay productos disponibles</td>
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
	<!-- Modal para Alta de Producto -->
	<div class="modal fade" id="altaProductoModal" tabindex="-1"
		role="dialog" aria-labelledby="altaProductoModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="altaProductoModalLabel">Nuevo
						Producto</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formAltaProducto">
					<div class="modal-body">
						<!-- Contenido del formulario de alta de producto -->
						<div class="row">
							<!-- Primera columna -->
							<div class="col-md-6">
								<div style="display: none;" class="form-group">
									<label for="productoId">ID</label> <input type="text"
										class="form-control" id="productoId" name="productoId">
								</div>
								<div class="form-group">
									<label for="codigo" class="required">Código</label> <input
										type="text" class="form-control" id="codigo" name="codigo"
										required>
								</div>
								<div class="form-group">
									<label for="nombre" class="required">Nombre</label> <input
										type="text" class="form-control" id="nombre" name="nombre"
										required>
								</div>
								<div class="form-group">
									<label for="idCategoria" class="required">Categoría</label> <select
										class="form-control" id="idCategoria" name="idCategoria"
										required>
										<option value="">Seleccionar Categoría</option>
										<%
										List<Categoria> categorias = (List<Categoria>) request.getSession().getAttribute("listaCategorias");
										if (categorias != null) {
											for (Categoria categoria : categorias) {
										%>
										<option value="<%=categoria.getIdCategoria()%>">
											<%=categoria.getNombre()%>
										</option>
										<%
										}
										}
										%>
									</select>
								</div>
								<div class="form-group">
									<label for="idMarca" class="required">Marca</label> <select
										class="form-control" id="idMarca" name="idMarca" required>
										<option value="">Seleccionar Marca</option>
										<%
										List<Marca> marcas = (List<Marca>) request.getSession().getAttribute("listaMarcas");
										if (marcas != null) {
											for (Marca marca : marcas) {
										%>
										<option value="<%=marca.getIdMarca()%>">
											<%=marca.getNombre()%>
										</option>
										<%
										}
										}
										%>
									</select>
								</div>
								<div class="form-group">
									<label for="talle">Talles Disponibles</label> <input
										type="text" class="form-control" id="talle" name="talle">
								</div>
								<div class="form-group">
									<label for="cantidadMax">Cantidad Máxima</label> <input
										type="number" class="form-control" id="cantidadMax"
										name="cantidadMax">
								</div>
								<div class="form-group">
									<label for="cantidadMin">Cantidad Mínima</label> <input
										type="number" class="form-control" id="cantidadMin"
										name="cantidadMin">
								</div>
								<div class="form-group">
									<label for="idDeposito">Depósito</label> <select
										class="form-control" id="idDeposito" name="idDeposito">
										<option value="">Seleccionar Depósito</option>
										<%
										List<Deposito> depositos = (List<Deposito>) request.getSession().getAttribute("listaDepositos");
										if (depositos != null) {
											for (Deposito deposito : depositos) {
										%>
										<option value="<%=deposito.getIdDeposito()%>">
											<%=deposito.getNombre()%>
										</option>
										<%
										}
										}
										%>
									</select>
								</div>
								<div class="form-group">
									<label for="fechaCreacion">Fecha de Creación</label> <input
										type="date" class="form-control" id="fechaCreacion"
										name="fechaCreacion">
								</div>
							</div>
							<!-- Segunda columna -->
							<div class="col-md-6">
								<div class="form-group">
									<label for="precioVenta" class="required">Precio de
										Venta</label> <input type="number" class="form-control"
										id="precioVenta" name="precioVenta" step="0.01" required>
								</div>
								<div class="form-group">
									<label for="cantidad" class="required">Cantidad</label> <input
										type="number" class="form-control" id="cantidad"
										name="cantidad" required>
								</div>
								<div class="form-group">
									<label for="precioCompra">Precio de Compra</label> <input
										type="number" class="form-control" id="precioCompra"
										name="precioCompra" step="0.01">
								</div>
								<div class="form-group">
									<label for="colores">Colores</label> <input type="text"
										class="form-control" id="colores" name="colores">
								</div>
								<div class="form-group">
									<label for="imageProduct">Imagen del Producto</label> <input
										type="text" class="form-control" id="imageProduct"
										name="imageProduct">
								</div>
								<div class="form-group">
									<label for="codigoBarras">Código de Barras</label> <input
										type="text" class="form-control" id="codigoBarras"
										name="codigoBarras">
								</div>
								<div class="form-group">
									<label for="enlaceProducto">Enlace del Producto</label> <input
										type="text" class="form-control" id="enlaceProducto"
										name="enlaceProducto">
								</div>
								<div class="form-group">
									<label for="idProveedor">Proveedor</label> <select
										class="form-control" id="idProveedor" name="idProveedor">
										<option value="">Seleccionar Proveedor</option>
										<%
										List<Proveedor> proveedores = (List<Proveedor>) request.getSession().getAttribute("listaProveedores");
										if (proveedores != null) {
											for (Proveedor proveedor : proveedores) {
										%>
										<option value="<%=proveedor.getIdProveedor()%>">
											<%=proveedor.getNombre()%>
										</option>
										<%
										}
										}
										%>
									</select>
								</div>
								<div class="form-group">
									<label for="activo">Activo</label> <select class="form-control"
										id="activo" name="activo">
										<option value="true">Sí</option>
										<option value="false">No</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Guardar
							Producto</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		setupDataTableAndModal({
			dataTableId : "#dataTableProduct",
			btnAltaId : "#btnAltaProducto",
			btnEditarId : "#btnEditarProducto",
			btnEliminarId : "#btnEliminarProducto",
			formId : "#formAltaProducto",
			modalId : "#altaProductoModal",
			modalTitleId : "#altaProductoModalLabel",
			altaUrl : "altaProducto",
			editarUrl : "editarProducto",
			eliminarUrl : "eliminarProducto",
			entityName : "Producto",
			limpiarFormulario : limpiarFormularioProducto,
			llenarFormulario : llenarFormularioProducto
		});

		$('#altaProductoModal').on('show.bs.modal', function(event) {
			var modal = $(this);

			$.ajax({
				url : 'altaProducto',
				method : 'GET',
				success : function(data) {
					if (data.codigo) {
						modal.find('#codigo').val(data.codigo);
					}
				},
				error : function() {
					alert('Error al obtener el código del producto');
				}
			});
		});

		/* FUNCIONES */

		// Función para limpiar el formulario de producto
		function limpiarFormularioProducto() {
			$('#productoId').val('');
			$('#codigo').val('');
			$('#nombre').val('');
			$('#idCategoria').val('');
			$('#idMarca').val('');
			$('#talle').val('');
			$('#cantidad').val('');
			$('#precioVenta').val('');
			$('#precioCompra').val('');
			$('#colores').val('');
			$('#imageProduct').val('');
			$('#codigoBarras').val('');
			$('#enlaceProducto').val('');
			$('#idProveedor').val('');
			$('#idDeposito').val('');
			$('#activo').val('true');
		}

		// Función para llenar el formulario de producto con datos para editar
		function llenarFormularioProducto(data) {

			const producto = data.data;

			$('#productoId').val(producto.idProducto);
			$('#codigo').val(producto.codigo);
			$('#nombre').val(producto.nombre);

			$('#talle').val(producto.talle || '');
			$('#cantidad').val(producto.cantidad || '');
			$('#precioVenta').val(producto.precioVenta || '');
			$('#precioCompra').val(producto.precioCompra || '');
			$('#colores').val(producto.colores || '');
			$('#imageProduct').val(producto.imageProduct || '');
			$('#codigoBarras').val(producto.codigoBarras || '');
			$('#enlaceProducto').val(producto.enlaceProducto || '');

			$('#activo').val(producto.activo ? 'true' : 'false');

			setSelectOption('#idCategoria', producto.categoria,
					producto.idCategoria);
			setSelectOption('#idMarca', producto.marca, producto.idMarca);
			setSelectOption('#idProveedor', producto.proveedor,
					producto.idProveedor);
			setSelectOption('#idDeposito', producto.deposito,
					producto.idDeposito);
		}

		// Utility function to set select options
		function setSelectOption(selector, text, value) {
			let select = $(selector);
			select.find('option').filter(function() {
				return $(this).val() == value || $(this).text() == text;
			}).prop('selected', true);
		}
	</script>
</div>