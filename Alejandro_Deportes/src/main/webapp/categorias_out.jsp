<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aledep.model.Categoria"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Categorías de
				Alejandro Deportes</h6>
		</div>
		<div class="card-body">

			<div class="filter-options mb-3" style="float: left">
				<a href="categorias?filtro=activos" class="btn btn-primary">Mostrar
					Activos</a> <a href="categorias?filtro=todos" class="btn btn-secondary">Mostrar
					Todos</a>
			</div>
			<div style="float: right">
				<!-- Botón para abrir el modal de alta de categoría -->
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnAltaCategoria" data-toggle="modal"
					data-target="#altaCategoriaModal"> <i class="fas fa-plus"></i>
				</a>
				<!-- Botón para abrir el modal de edición de categoría -->
				<a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarCategoria"> <i class="fas fa-edit"></i>
				</a>
				<!-- Botón para eliminar categoría -->
				<a href="#" class="btn btn-danger btn-circle"
					id="btnEliminarCategoria"> <i class="fas fa-trash-alt"></i>
				</a>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th style="display: none;">ID</th>
							<th>Nombre</th>
							<th>Activo</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("listaCategorias");

						if (listaCategorias == null) {
							listaCategorias = new ArrayList<>();
						}

						if (!listaCategorias.isEmpty()) {
							for (Categoria categoria : listaCategorias) {
						%>
						<tr data-id="<%=categoria.getIdCategoria()%>">
							<td style="display: none;"><%=categoria.getIdCategoria()%></td>
							<td><%=categoria.getNombre()%></td>
							<td><%=categoria.getActivo() ? "Sí" : "No"%></td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="3">No hay categorías disponibles</td>
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

	<!-- Modal para Alta de Categoría -->
	<div class="modal fade" id="altaCategoriaModal" tabindex="-1"
		role="dialog" aria-labelledby="altaCategoriaModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="altaCategoriaModalLabel">Nueva
						Categoría</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formAltaCategoria">
					<div class="modal-body">
						<div class="form-group">
							<label for="nombre" class="required">Nombre</label> <input
								type="text" class="form-control" id="nombre" name="nombre"
								required>
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
							Categoría</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		setupDataTableAndModal({
			dataTableId : "#dataTable",
			btnAltaId : "#btnAltaCategoria",
			btnEditarId : "#btnEditarCategoria",
			btnEliminarId : "#btnEliminarCategoria",
			formId : "#formAltaCategoria",
			modalId : "#altaCategoriaModal",
			modalTitleId : "#altaCategoriaModalLabel",
			altaUrl : "altaCategoria",
			editarUrl : "editarCategoria",
			eliminarUrl : "eliminarCategoria",
			entityName : "Categoría",
			limpiarFormulario : limpiarFormularioCategoria,
			llenarFormulario : llenarFormularioCategoria
		});

		// Funciones específicas para limpiar y llenar formularios
		function limpiarFormularioCategoria() {
			$('#categoriaId').val('');
			$('#nombre').val('');
			$('#activo').val('true');
		}

		function llenarFormularioCategoria(data) {
			$('#categoriaId').val(data.idCategoria);
			$('#nombre').val(data.nombre);
			$('#activo').val(data.activo ? 'true' : 'false');
		}
	</script>

</div>

<%@ include file="components/footer.jsp"%>
