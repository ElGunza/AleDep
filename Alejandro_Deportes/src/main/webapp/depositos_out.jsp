<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="aledep.model.Deposito"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<style>
tr.selected {
	background-color: #007bff !important;
	color: #ffffff;
	font-weight: bold;
	transition: background-color 0.3s ease;
}

label.required::after {
	content: "*";
	color: red;
	margin-left: 5px;
	font-weight: bold;
}

input[required]:focus, select[required]:focus, textarea[required]:focus
	{
	outline: none;
	border-color: darkred;
}
</style>

<div class="container-fluid">
	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Dep�sitos de
				Alejandro Deportes</h6>
			<div>
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnAltaDeposito" data-toggle="modal"
					data-target="#altaDepositoModal"> <i class="fas fa-plus"></i>
				</a> <a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarDeposito"> <i class="fas fa-edit"></i>
				</a> <a href="#" class="btn btn-danger btn-circle"
					id="btnEliminarDeposito"> <i class="fas fa-trash-alt"></i>
				</a>
			</div>
		</div>
		<div class="card-body">
			<div class="filter-options mb-3">
				<a href="depositos?filtro=activos" class="btn btn-primary">Mostrar
					Activos</a> <a href="depositos?filtro=todos" class="btn btn-secondary">Mostrar
					Todos</a>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th style="display: none;">ID</th>
							<th>Nombre</th>
							<th>Descripci�n</th>
							<th>Ubicaci�n</th>
							<th>Capacidad</th>
							<th>Cantidad de Productos</th>
						</tr>
					</thead>
					<tbody>
						<%
						//List<Deposito> listaDepositos = (List<Deposito>) request.getSession().getAttribute("listaDepositos");
						List<Deposito> listaDepositos = (List<Deposito>) request.getAttribute("listaDepositos");

						if (listaDepositos != null && !listaDepositos.isEmpty()) {
							for (Deposito deposito : listaDepositos) {
						%>
						<tr data-id="<%=deposito.getIdDeposito()%>">
							<td style="display: none;"><%=deposito.getIdDeposito()%></td>
							<td><%=deposito.getNombre()%></td>
							<td><%=deposito.getDescripcion()%></td>
							<td><%=deposito.getUbicacion()%></td>
							<td><%=deposito.getCapacidad() != null ? deposito.getCapacidad() : "No definido"%></td>
							<td><%=deposito.getProductos() != null ? deposito.getProductos().size() : 0%></td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="6">No hay dep�sitos disponibles</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Modal para Alta/Edici�n de Dep�sito -->
	<div class="modal fade" id="altaDepositoModal" tabindex="-1"
		role="dialog" aria-labelledby="altaDepositoModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="altaDepositoModalLabel">Nuevo
						Dep�sito</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formAltaDeposito">
					<div class="modal-body">
						<div class="row">
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
									<label for="descripcion">Descripci�n</label> <input type="text"
										class="form-control" id="descripcion" name="descripcion">
								</div>
								<div class="form-group">
									<label for="ubicacion" class="required">Ubicaci�n</label> <input
										type="text" class="form-control" id="ubicacion"
										name="ubicacion" required>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="capacidad" class="required">Capacidad</label> <input
										type="number" class="form-control" id="capacidad"
										name="capacidad" required>
								</div>
								<div class="form-group">
									<label for="activo">Activo</label> <select class="form-control"
										id="activo" name="activo">
										<option value="true">S�</option>
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
							Dep�sito</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
        document.addEventListener("DOMContentLoaded", function() {
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
                        "emptyTable": "No hay dep�sitos disponibles"
                    }
                });
            }

            let selectedId = null;
            $(document).ready(function() {
                let table = $('#dataTable').DataTable();

                $('#dataTable tbody').on('click', 'tr', function() {
                    $('#dataTable tbody tr').removeClass('selected');
                    $(this).addClass('selected');
                    selectedId = $(this).data('id');
                });

                table.on('draw', function() {
                    $('#dataTable tbody tr').removeClass('selected');
                    selectedId = null;
                });

                $('#btnAltaDeposito').on('click', function() {
                    limpiarFormularioDeposito();
                    $('#altaDepositoModalLabel').text('Nuevo Dep�sito');
                    $('#altaDepositoModal').modal('show');
                });

                $('#formAltaDeposito').on('submit', function(event) {
                    event.preventDefault();
                    let url = selectedId ? 'editarDeposito' : 'altaDeposito';

                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: $(this).serialize(),
                        success: function(response) {
                            if (response.status === 'success') {
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Dep�sito guardado con �xito',
                                    text: response.message
                                }).then(() => {
                                    $('#altaDepositoModal').modal('hide');
                                    location.reload();
                                });
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'Hubo un problema al guardar el dep�sito.'
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

                $('#btnEditarDeposito').on('click', function() {
                    if (selectedId) {
                        $.ajax({
                            url: 'editarDeposito',
                            type: 'GET',
                            data: { id: selectedId },
                            success: function(data) {
                                llenarFormularioDeposito(data);
                                $('#altaDepositoModalLabel').text('Editar Dep�sito');
                                $('#altaDepositoModal').modal('show');
                            },
                            error: function() {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'No se pudo cargar la informaci�n del dep�sito.'
                                });
                            }
                        });
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Advertencia',
                            text: 'Por favor, selecciona un dep�sito primero.'
                        });
                    }
                });

                $('#dataTable tbody').on('dblclick', 'tr', function() {
                    $('#btnEditarDeposito').trigger('click');
                });

                $('#btnEliminarDeposito').on('click', function() {
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
                            text: 'Por favor, selecciona un dep�sito primero.'
                        });
                    }
                });

                function limpiarFormularioDeposito() {
                    $('#DepositoId').val('');
                    $('#nombre').val('');
                    $('#descripcion').val('');
                    $('#ubicacion').val('');
                    $('#capacidad').val('');
                    $('#activo').val('true');
                }

                function llenarFormularioDeposito(data) {
                	
                    $('#DepositoId').val(data.idDeposito);
                    $('#nombre').val(data.nombre);
                    $('#descripcion').val(data.descripcion);
                    $('#ubicacion').val(data.ubicacion);
                    $('#capacidad').val(data.capacidad);
                    $('#activo').val(data.activo.toString());
                }
            });
        });
    </script>
</div>

<%@ include file="components/footer.jsp"%>
