<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<style>
tr.selected {
	background-color: #007bff !important; /* Color azul intenso */
	color: #ffffff; /* Texto blanco para contraste */
	font-weight: bold; /* Hacer el texto m�s grueso */
	transition: background-color 0.3s ease; /* Suavizar la transici�n */
}

/* Estilo para el asterisco al lado de la etiqueta solo si el campo es obligatorio */
label.required::after {
	content: "*"; /* Asterisco para campos obligatorios */
	color: red; /* Color rojo */
	margin-left: 5px;
	/* Espacio entre el texto de la etiqueta y el asterisco */
	font-weight: bold; /* Asterisco en negrita */
}

/* Opcional: estilo adicional cuando el campo obligatorio est� en foco */
input[required]:focus, select[required]:focus, textarea[required]:focus
	{
	outline: none;
	border-color: darkred; /* Borde m�s oscuro en foco */
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
			<h6 class="m-0 font-weight-bold text-primary">Categor�as de
				Alejandro Deportes</h6>
		</div>
		<div class="card-body">

			<div class="filter-options mb-3" style="float: left">
				<a href="categorias?filtro=activos" class="btn btn-primary">Mostrar
					Activos</a> <a href="categorias?filtro=todos" class="btn btn-secondary">Mostrar
					Todos</a>
			</div>
			<div style="float: right">
				<!-- Bot�n para abrir el modal de alta de categor�a -->
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnAltaCategoria" data-toggle="modal"
					data-target="#altaCategoriaModal"> <i class="fas fa-plus"></i>
				</a>
				<!-- Bot�n para abrir el modal de edici�n de categor�a -->
				<a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarCategoria"> <i class="fas fa-edit"></i>
				</a>
				<!-- Bot�n para eliminar categor�a -->
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
							<td><%=categoria.getActivo() ? "S�" : "No"%></td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="3">No hay categor�as disponibles</td>
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

	<!-- Modal para Alta de Categor�a -->
	<div class="modal fade" id="altaCategoriaModal" tabindex="-1"
		role="dialog" aria-labelledby="altaCategoriaModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="altaCategoriaModalLabel">Nueva
						Categor�a</h5>
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
								<option value="true">S�</option>
								<option value="false">No</option>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Guardar
							Categor�a</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
        document.addEventListener("DOMContentLoaded", function() {
            // Verificar si DataTable ya est� inicializado
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
                        "emptyTable": "No hay categor�as disponibles"
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

                // Desmarcar selecci�n al cambiar la p�gina
                table.on('draw', function() {
                    $('#dataTable tbody tr').removeClass('selected');
                    selectedId = null;
                });

                // Bot�n para abrir el modal de Alta de Categor�a
                $('#btnAltaCategoria').on('click', function() {
                    limpiarFormularioCategoria();
                    $('#altaCategoriaModalLabel').text('Nueva Categor�a');
                    $('#altaCategoriaModal').modal('show');
                });

                // Manejar el env�o del formulario del modal (alta o edici�n)
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
                                    title: '�xito',
                                    text: response.message
                                }).then(() => {
                                    $('#altaCategoriaModal').modal('hide');
                                    location.reload();
                                });
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'Hubo un problema al guardar la categor�a.'
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

                // Bot�n para abrir el modal de Edici�n de Categor�a
                $('#btnEditarCategoria').on('click', function() {
                    if (selectedId) {
                        $.ajax({
                            url: 'editarCategoria',
                            type: 'GET',
                            data: { id: selectedId },
                            success: function(data) {
                                llenarFormularioCategoria(data);
                                $('#altaCategoriaModalLabel').text('Editar Categor�a');
                                $('#altaCategoriaModal').modal('show');
                            },
                            error: function() {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'No se pudo cargar la informaci�n de la categor�a.'
                                });
                            }
                        });
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Advertencia',
                            text: 'Por favor, selecciona una categor�a primero.'
                        });
                    }
                });

                $('#dataTable tbody').on('dblclick', 'tr', function() {
                    $('#btnEditarCategoria').trigger('click');
                });

                // Bot�n para eliminar categor�a
                $('#btnEliminarCategoria').on('click', function() {
                    if (selectedId) {
                        Swal.fire({
                            title: '�Est�s seguro?',
                            text: "No podr�s revertir esto",
                            icon: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: '#3085d6',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'S�, eliminar'
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
                            text: 'Por favor, selecciona una categor�a primero.'
                        });
                    }
                });
                
                /* FUNCIONES */
                        
                // Funci�n para limpiar el formulario de categor�a
                function limpiarFormularioCategoria() {
                    $('#productoId').val('');
                    $('#nombre').val('');
                    $('#activo').val('true');
                }

                // Funci�n para llenar el formulario de categor�a con datos para editar
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
