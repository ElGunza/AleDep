<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="aledep.model.Deposito"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>
<div class="container-fluid">
    <div class="card shadow mb-4">
        <div class="card-header py-3 d-flex justify-content-between align-items-center">
            <h6 class="m-0 font-weight-bold text-primary">Depósitos de
                Alejandro Deportes</h6>
            <div>
                <a href="#" class="btn btn-primary btn-circle mr-2" id="btnAltaDeposito" data-toggle="modal" data-target="#altaDepositoModal"> <i class="fas fa-plus"></i>
                </a> <a href="#" class="btn btn-warning btn-circle mr-2" id="btnEditarDeposito"> <i class="fas fa-edit"></i>
                </a> <a href="#" class="btn btn-danger btn-circle" id="btnEliminarDeposito"> <i class="fas fa-trash-alt"></i>
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
                <table class="table table-bordered" id="dataTableDepositos" width="100%" cellspacing="0">
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
						//List<Deposito> listaDepositos = (List<Deposito>) request.getSession().getAttribute("listaDepositos");
						List<Deposito> listaDepositos = (List<Deposito>) request.getAttribute("listaDepositos");

						if (listaDepositos != null && !listaDepositos.isEmpty()) {
							for (Deposito deposito : listaDepositos) {
						%>
                        <tr data-id="<%=deposito.getIdDeposito()%>">
                            <td style="display: none;">
                                <%=deposito.getIdDeposito()%>
                            </td>
                            <td>
                                <%=deposito.getNombre()%>
                            </td>
                            <td>
                                <%=deposito.getDescripcion()%>
                            </td>
                            <td>
                                <%=deposito.getUbicacion()%>
                            </td>
                            <td>
                                <%=deposito.getCapacidad() != null ? deposito.getCapacidad() : "No definido"%>
                            </td>
                            <td>
                                <%=deposito.getProductos() != null ? deposito.getProductos().size() : 0%>
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
    <!-- Modal para Alta/Edición de Depósito -->
    <div class="modal fade" id="altaDepositoModal" tabindex="-1" role="dialog" aria-labelledby="altaDepositoModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="altaDepositoModalLabel">Nuevo
                        Depósito</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="formAltaDeposito">
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div style="display: none;" class="form-group">
                                    <label for="DepositoId">ID</label> <input type="text" class="form-control" id="DepositoId" name="DepositoId">
                                </div>
                                <div class="form-group">
                                    <label for="nombre" class="required">Nombre</label> <input type="text" class="form-control" id="nombre" name="nombre" required>
                                </div>
                                <div class="form-group">
                                    <label for="descripcion">Descripción</label> <input type="text" class="form-control" id="descripcion" name="descripcion">
                                </div>
                                <div class="form-group">
                                    <label for="ubicacion" class="required">Ubicación</label> <input type="text" class="form-control" id="ubicacion" name="ubicacion" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="capacidad" class="required">Capacidad</label> <input type="number" class="form-control" id="capacidad" name="capacidad" required>
                                </div>
                                <div class="form-group">
                                    <label for="activo">Activo</label> <select class="form-control" id="activo" name="activo">
                                        <option value="true">Sí</option>
                                        <option value="false">No</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar
                            Depósito</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script>
    setupDataTableAndModal({
        dataTableId: "#dataTableDepositos",
        btnAltaId: "#btnAltaDeposito",
        btnEditarId: "#btnEditarDeposito",
        btnEliminarId: "#btnEliminarDeposito",
        formId: "#formAltaDeposito",
        modalId: "#altaDepositoModal",
        modalTitleId: "#altaDepositoModalLabel",
        altaUrl: "altaDeposito",
        editarUrl: "editarDeposito",
        eliminarUrl: "eliminarDeposito",
        entityName: "Deposito",
        limpiarFormulario: limpiarFormularioDeposito,
        llenarFormulario: llenarFormularioDeposito
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
    </script>
</div>
<%@ include file="components/footer.jsp"%>