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
	/* background-color: #ffcccc;  */ /* Fondo más oscuro en foco */
}
</style>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aledep.model.Deposito"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>
<div class="container-fluid">
	<!-- 	<h1 class="h3 mb-2 text-gray-800">Inventario</h1> -->
	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Depósitos de
				Alejandro Deportes</h6>
			<div>
				<!-- Botón para abrir el modal de alta de Depósito -->
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnAltaDeposito" data-toggle="modal"
					data-target="#altaDepositoModal"> <i class="fas fa-plus"></i>
				</a>
				<!-- Botón para abrir el modal de edición de Depósito -->
				<a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarDeposito"> <i class="fas fa-edit"></i>
				</a>
				<!-- Botón para eliminar Depósito -->
				<a href="#" class="btn btn-danger btn-circle"
					id="btnEliminarDeposito"> <i class="fas fa-trash-alt"></i>
				</a>
			</div>
		</div>
		<div class="card-body">
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th style="display: none;">ID</th>
							<th>Nombre</th>
							<th>Descripción</th>
							<th>Ubicación</th>
							<th>Capacidad</th>
							<th>Cantidad de Productos</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<Deposito> listaDepositos = (List<Deposito>) request.getSession().getAttribute("listaDepositos");
						if (listaDepositos == null) {
							listaDepositos = new ArrayList<>(); // Inicializa la lista para evitar null
						}

						if (!listaDepositos.isEmpty()) {
							for (Deposito deposito : listaDepositos) {
						%>
						<tr data-id="<%=deposito.getIdDeposito()%>">
							<td style="display: none;"><%=deposito.getIdDeposito()%></td>
							<td><%=deposito.getNombre()%></td>
							<td><%=deposito.getDescripcion()%></td>
							<td><%=deposito.getUbicacion()%></td>
							<td><%=deposito.getCapacidad() != null ? deposito.getCapacidad() : "No definido"%>
							</td>
							<td><%=deposito.getProductos() != null ? deposito.getProductos().size() : 0%>
							</td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="6">No hay depósitos disponibles</td>
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
	<!-- Modal para Alta de Deposito -->
	<div class="modal fade" id="altaDepositoModal" tabindex="-1"
		role="dialog" aria-labelledby="altaDepositoModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="altaDepositoModalLabel">Nuevo
						Deposito</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formAltaDeposito">
					<div class="modal-body">
						<!-- Contenido del formulario de alta de Deposito -->
						<div class="row">
							<!-- Primera columna -->
							<div class="col-md-6">
								<div style="display: none;" class="form-group">
									<label for="DepositoId">ID</label> <input type="text"
										class="form-control" id="DepositoId" name="DepositoId">
								</div>
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
									<label for="ubicacion" class="required">Ubicación</label> <input
										type="text" class="form-control" id="ubicacion"
										name="ubicacion" required>
								</div>
							</div>
							<!-- Segunda columna -->
							<div class="col-md-6">
								<div class="form-group">
									<label for="capacidad" class="required">Capacidad</label> <input
										type="number" class="form-control" id="capacidad"
										name="capacidad" required>
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
							Deposito</button>
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
                    "emptyTable": "No hay depósitos disponibles"
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

            // Botón para abrir el modal de Alta de Depósito
            $('#btnAltaDeposito').on('click', function() {
                // Limpiar el formulario
                limpiarFormularioDeposito();

                // Cambiar el título del modal
                $('#altaDepositoModalLabel').text('Nuevo Depósito');

                // Mostrar el modal
                $('#altaDepositoModal').modal('show');
            });

            // Manejar el envío del formulario del modal (alta o edición)
            $('#formAltaDeposito').on('submit', function(event) {
                event.preventDefault(); // Prevenir el comportamiento por defecto del formulario

                let url = selectedId ? 'editarDeposito' : 'altaDeposito'; // Determinar si es alta o edición

                $.ajax({
                    url: url,
                    type: 'POST',
                    data: $(this).serialize(), // Serializar todos los datos del formulario
                    success: function(response) {
                        if (response.status === 'success') {
                            Swal.fire({
                                icon: 'success',
                                title: 'Éxito',
                                text: response.message
                            }).then(() => {
                                // Cerrar el modal
                                $('#altaDepositoModal').modal('hide');
                                // Recargar la tabla de Depósitos
                                location.reload();
                            });
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'Hubo un problema al guardar el depósito.'
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

            // Botón para abrir el modal de Edición de Depósito
            $('#btnEditarDeposito').on('click', function() {
                if (selectedId) {
                    $.ajax({
                        url: 'editarDeposito',
                        type: 'GET',
                        data: { id: selectedId },
                        success: function(data) {
                            llenarFormularioDeposito(data);

                            // Cambiar el título del modal
                            $('#altaDepositoModalLabel').text('Editar Depósito');
                            $('#altaDepositoModal').modal('show');
                        },
                        error: function() {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: 'No se pudo cargar la información del depósito.'
                            });
                        }
                    });
                } else {
                    Swal.fire({
                        icon: 'warning',
                        title: 'Advertencia',
                        text: 'Por favor, selecciona un depósito primero.'
                    });
                }
            });

            $('#dataTable tbody').on('dblclick', 'tr', function() {
                $('#btnEditarDeposito').trigger('click');
            });

            // Botón para eliminar Depósito
            $('#btnEliminarDeposito').on('click', function() {
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
                                url: 'eliminarDeposito',
                                type: 'POST',
                                data: { id: selectedId },
                                success: function(response) {
                                    if (response.status === 'success') {
                                        Swal.fire({
                                            icon: 'success',
                                            title: 'Eliminado',
                                            text: response.message
                                        }).then(() => {
                                            // Recargar la tabla de Depósitos
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
                        text: 'Por favor, selecciona un depósito primero.'
                    });
                }
            });

            /* FUNCIONES */

            // Función para limpiar el formulario de Depósito
            function limpiarFormularioDeposito() {
                $('#DepositoId').val('');
                $('#nombre').val('');
                $('#descripcion').val('');
                $('#ubicacion').val('');
                $('#capacidad').val('');
                $('#activo').val('true');
            }

            // Función para llenar el formulario de Depósito con datos para editar
            function llenarFormularioDeposito(data) {
                $('#DepositoId').val(data.idDeposito);
                $('#nombre').val(data.nombre);
                $('#descripcion').val(data.descripcion);
                $('#ubicacion').val(data.ubicacion);
                $('#capacidad').val(data.capacidad);
                $('#activo').val(data.activo);
            }
        });
    });
    </script>
</div>

<%@ include file="components/footer.jsp"%>
