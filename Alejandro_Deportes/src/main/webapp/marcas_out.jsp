<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<style>
tr.selected {
	background-color: #007bff !important; /* Color azul intenso */
	color: #ffffff; /* Texto blanco para contraste */
	font-weight: bold; /* Hacer el texto más grueso */
	transition: background-color 0.3s ease; /* Suavizar la transición */
}

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
}
</style>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aledep.model.Marca"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Marcas de
				Alejandro Deportes</h6>
		</div>
		<div class="card-body">

			<div class="filter-options mb-3" style="float: left">
				<a href="marcas?filtro=activos" class="btn btn-primary">Mostrar
					Activos</a> <a href="marcas?filtro=todos" class="btn btn-secondary">Mostrar
					Todos</a>
			</div>
			<div style="float: right">
				<!-- Botón para abrir el modal de alta de marca -->
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnAltaMarca" data-toggle="modal" data-target="#altaMarcaModal">
					<i class="fas fa-plus"></i>
				</a>
				<!-- Botón para abrir el modal de edición de marca -->
				<a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarMarca"> <i class="fas fa-edit"></i>
				</a>
				<!-- Botón para eliminar marca -->
				<a href="#" class="btn btn-danger btn-circle" id="btnEliminarMarca">
					<i class="fas fa-trash-alt"></i>
				</a>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th style="display: none;">ID</th>
							<th>Nombre</th>
							<th>Descripción</th>
							<th>Activo</th>
						</tr>
					</thead>
					<tbody>
						<%
						//						List<Marca> listaMarcas = (List<Marca>) request.getSession().getAttribute("listaMarcas");

						List<Marca> listaMarcas = (List<Marca>) request.getAttribute("listaMarcas");

						if (listaMarcas == null) {
							listaMarcas = new ArrayList<>();
						}

						if (!listaMarcas.isEmpty()) {
							for (Marca marca : listaMarcas) {
						%>
						<tr data-id="<%=marca.getIdMarca()%>">
							<td style="display: none;"><%=marca.getIdMarca()%></td>
							<td><%=marca.getNombre()%></td>
							<td><%=marca.getDescripcion()%></td>
							<td><%=marca.getActivo() ? "Sí" : "No"%></td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="4">No hay marcas disponibles</td>
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

	<!-- Modal para Alta de Marca -->
	<div class="modal fade" id="altaMarcaModal" tabindex="-1" role="dialog"
		aria-labelledby="altaMarcaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="altaMarcaModalLabel">Nueva Marca</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formAltaMarca">
					<div class="modal-body">
						<div class="form-group">
							<label for="nombre" class="required">Nombre</label> <input
								type="text" class="form-control" id="nombre" name="nombre"
								required>
						</div>
						<div class="form-group">
							<label for="descripcion">Descripción</label> <input type="text"
								class="form-control" id="descripcion" name="descripcion">
						</div>
						<div class="form-group">
							<label for="activo">Activo</label> <select class="form-control"
								id="activo" name="activo">
								<option value="true">Sí</option>
								<option value="false">No</option>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Guardar
							Marca</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		setupDataTableAndModal({
			dataTableId : "#dataTable",
			btnAltaId : "#btnAltaMarca",
			btnEditarId : "#btnEditarMarca",
			btnEliminarId : "#btnEliminarMarca",
			formId : "#formAltaMarca",
			modalId : "#altaMarcaModal",
			modalTitleId : "#altaMarcaModalLabel",
			altaUrl : "altaMarca",
			editarUrl : "editarMarca",
			eliminarUrl : "eliminarMarca",
			entityName : "Marca",
			limpiarFormulario : limpiarFormularioMarca,
			llenarFormulario : llenarFormularioMarca
		});

		/* FUNCIONES */

		// Función para limpiar el formulario de marca
		function limpiarFormularioMarca() {
			$('#productoId').val('');
			$('#nombre').val('');
			$('#descripcion').val('');
			$('#activo').val('true');
		}

		// Función para llenar el formulario de marca con datos para editar
		function llenarFormularioMarca(data) {
			$('#productoId').val(data.idMarca);
			$('#nombre').val(data.nombre);
			$('#descripcion').val(data.descripcion || '');
			$('#activo').val(data.activo ? 'true' : 'false');
		}
	</script>

</div>

<%@ include file="components/footer.jsp"%>
