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
						List<Categoria> listaCategorias = (List<Categoria>) request.getSession().getAttribute("listaCategorias");
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
        document.addEventListener("DOMContentLoaded", function() {
            // Verificar si DataTable ya está inicializado
            if (!$.fn.DataTable.isDataTable("#dataTable")) {
                $('#dataTable').DataTable({
                    "pageLength": 10,
                    "lengthChange": false,
                    "language": {
                        "search": "Buscar:",
                        "paginate": {
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "info": "Mostrando _START_ a _END_ de _TOTAL_ registros",
                        "infoEmpty": "No hay registros disponibles",
                        "emptyTable": "No hay categorías disponibles"
                    }
                });
            }

            let selectedId = null;

            $(document).ready(function() {
                let table = $('#dataTable').DataTable();

                // Asocia el evento al tbody para seleccionar una fila
                $('#dataTable tbody').on('click', 'tr', function() {
                    $('#dataTable tbody tr').removeClass('selected');
                    $(this).addClass('selected');
                    selectedId = $(this).data('id');
                });

                // Desmarcar selección al cambiar la página
                table.on('draw', function() {
                    $('#dataTable tbody tr').removeClass('selected');
                    selectedId = null;
                });

                // Botón para abrir el modal de Alta de Categoría
                $('#btnAltaCategoria').on('click', function() {
                    limpiarFormularioCategoria();
                    $('#altaCategoriaModalLabel').text('Nueva Categoría');
                    $('#altaCategoriaModal').modal('show');
                });

                // Manejar el envío del formulario del modal (alta o edición)
                $('#formAltaCategoria').on('submit', function(event) {
                    event.preventDefault();

                    let url = selectedId ? 'editarCategoria' : 'altaCategoria';

                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: $(this).serialize(),
                        success: function(response) {
                            if (response.status === 'success') {
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Éxito',
                                    text: response.message
                                }).then(() => {
                                    $('#altaCategoriaModal').modal('hide');
                                    location.reload();
                                });
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'Hubo un problema al guardar la categoría.'
                                });
                            }
                        },
                        error: function() {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Hubo un problema al procesar la solicitud.'
                            });
                        }
                    });
                });

                // Botón para abrir el modal de Edición de Categoría
                $('#btnEditarCategoria').on('click', function() {
                    if (selectedId) {
                        $.ajax({
                            url: 'editarCategoria',
                            type: 'GET',
                            data: { id: selectedId },
                            success: function(data) {
                                llenarFormularioCategoria(data);
                                $('#altaCategoriaModalLabel').text('Editar Categoría');
                                $('#altaCategoriaModal').modal('show');
                            },
                            error: function() {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'No se pudo cargar la información de la categoría.'
                                });
                            }
                        });
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Advertencia',
                            text: 'Por favor, selecciona una categoría primero.'
                        });
                    }
                });

                $('#dataTable tbody').on('dblclick', 'tr', function() {
                    $('#btnEditarCategoria').trigger('click');
                });

                // Botón para eliminar categoría
                $('#btnEliminarCategoria').on('click', function() {
                    if (selectedId) {
                        Swal.fire({
                            title: '¿Estás seguro?',
                            text: "No podrás revertir esto",
                            icon: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: '#3085d6',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'Sí, eliminar'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                $.ajax({
                                    url: 'eliminarCategoria',
                                    type: 'POST',
                                    data: { id: selectedId },
                                    success: function(response) {
                                        if (response.status === 'success') {
                                            Swal.fire({
                                                icon: 'success',
                                                title: 'Eliminado',
                                                text: response.message
                                            }).then(() => {
                                                location.reload();
                                            });
                                        } else {
                                            Swal.fire({
                                                icon: 'error',
                                                title: 'Error',
                                                text: response.message
                                            });
                                        }
                                    },
                                    error: function() {
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Error',
                                            text: 'Hubo un problema al procesar la solicitud.'
                                        });
                                    }
                                });
                            }
                        });
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Advertencia',
                            text: 'Por favor, selecciona una categoría primero.'
                        });
                    }
                });
                
                /* FUNCIONES */
                        
                // Función para limpiar el formulario de categoría
                function limpiarFormularioCategoria() {
                    $('#productoId').val('');
                    $('#nombre').val('');
                    $('#activo').val('true');
                }

                // Función para llenar el formulario de categoría con datos para editar
                function llenarFormularioCategoria(data) {

                    $('#productoId').val(data.idCategoria);
                    $('#nombre').val(data.nombre);
                    $('#activo').val(data.activo ? 'true' : 'false');
                }

            });
        });
    </script>
</div>

<%@ include file="components/footer.jsp"%>
