<%-- 

SE HIZO TODO EN UN MODAL PARA NO SALIR DE LA PANTALLA

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<style>

/* Estilo para el asterisco al lado de la etiqueta solo si el campo es obligatorio */
label.required::after {
	content: "*"; /* Asterisco para campos obligatorios */
	color: red; /* Color rojo */
	margin-left: 5px;
	/* Espacio entre el texto de la etiqueta y el asterisco */
	font-weight: bold; /* Asterisco en negrita */
}

/* Opcional: estilo adicional cuando el campo obligatorio está en foco */
input[required]:focus, select[required]:focus, textarea[required]:focus
	{
	outline: none;
	border-color: darkred; /* Borde más oscuro en foco */
	/* background-color: #ffcccc;  */ /* Fondo más oscuro en foco */
}
</style>

<%@ page import="java.util.List"%>
<%@ page import="aledep.model.Categoria"%>
<%@ page import="aledep.model.Marca"%>
<%@ page import="aledep.model.Proveedor"%>
<%@ page import="aledep.model.Deposito"%>

<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
	<h1 class="h3 mb-2 text-gray-800">Nuevo Producto</h1>

	<form action="altaProducto" method="post">
		<div class="row">
			<!-- Primera columna -->
			<div class="col-md-6">
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
						class="form-control" id="idCategoria" name="idCategoria" required>
						<option value="">Seleccionar Categoría</option>
						<%
						List<Categoria> categorias = (List<Categoria>) request.getSession().getAttribute("listaCategorias");
						if (categorias != null) {
							for (Categoria categoria : categorias) {
						%>
						<option value="<%=categoria.getIdCategoria()%>"><%=categoria.getNombre()%></option>
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
						<option value="<%=marca.getIdMarca()%>"><%=marca.getNombre()%></option>
						<%
						}
						}
						%>
					</select>
				</div>
				<div class="form-group">
					<label for="talle">Talles Disponibles</label> <input type="text"
						class="form-control" id="talle" name="talle">
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
						<option value="<%=deposito.getIdDeposito()%>"><%=deposito.getNombre()%></option>
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
					<label for="precioVenta" class="required">Precio de Venta</label> <input
						type="number" class="form-control" id="precioVenta"
						name="precioVenta" step="0.01" required>
				</div>
				<div class="form-group">
					<label for="cantidad" class="required">Cantidad</label> <input
						type="number" class="form-control" id="cantidad" name="cantidad"
						required>
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
						<option value="<%=proveedor.getIdProveedor()%>"><%=proveedor.getNombre()%></option>
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

		<!-- Fila para el botón de guardar -->
		<div class="row">
			<div class="col-md-6"></div>
			<div class="col-md-6 d-flex align-items-end">
				<button type="submit" class="btn btn-primary ml-auto">Guardar
					Producto</button>
			</div>
		</div>
	</form>

</div>

<%@ include file="components/footer.jsp"%>
 --%>