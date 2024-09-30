<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aledep.model.Proveedor"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
    <!-- Título de la sección -->
    <div class="card shadow mb-4">
        <div class="card-header py-3 d-flex justify-content-between align-items-center">
            <h6 class="m-0 font-weight-bold text-primary">Proveedores de Alejandro Deportes</h6>
        </div>
        <div class="card-body">
            <div class="filter-options mb-3" style="float: left">
                <a href="proveedores?filtro=activos" class="btn btn-primary">Mostrar Activos</a>
                <a href="proveedores?filtro=todos" class="btn btn-secondary">Mostrar Todos</a>
            </div>
            <div style="float: right">
                <!-- Botón para abrir el modal de alta de proveedor -->
                <a href="#" class="btn btn-primary btn-circle mr-2" id="btnAltaProveedor" data-toggle="modal" data-target="#altaProveedorModal">
                    <i class="fas fa-plus"></i>
                </a>
                <!-- Botón para abrir el modal de edición de proveedor -->
                <a href="#" class="btn btn-warning btn-circle mr-2" id="btnEditarProveedor">
                    <i class="fas fa-edit"></i>
                </a>
                <!-- Botón para eliminar proveedor -->
                <a href="#" class="btn btn-danger btn-circle" id="btnEliminarProveedor">
                    <i class="fas fa-trash-alt"></i>
                </a>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTableProveedores">
                    <thead>
                        <tr>
                            <th>CUIT/DNI</th>
                            <th>Nombre</th>
                            <th>Contacto</th>
                            <th>Activo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<Proveedor> listaProveedores = (List<Proveedor>) request.getSession().getAttribute("listaProveedores");
                        if (listaProveedores == null) {
                            listaProveedores = new ArrayList<>();
                        }

                        if (!listaProveedores.isEmpty()) {
                            for (Proveedor proveedor : listaProveedores) {
                        %>
                        <tr data-id="<%=proveedor.getIdProveedor()%>">
                            <td><%=proveedor.getCuitDni()%></td>
                            <td><%=proveedor.getNombre()%></td>
                            <td><%=proveedor.getContacto()%></td>
                            <td><%=proveedor.getActivo() ? "Sí" : "No"%></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="4">No hay proveedores disponibles</td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal para Alta de Proveedor -->
    <div class="modal fade" id="altaProveedorModal" tabindex="-1" role="dialog" aria-labelledby="altaProveedorModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="altaProveedorModalLabel">Nuevo Proveedor</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="formAltaProveedor">
                    <div class="modal-body">
                        <!-- Contenido del formulario de alta de proveedor -->
                        <div class="row">
                            <!-- Primera columna -->
                            <div class="col-md-6">
                                <div style="display: none;" class="form-group">
                                    <label for="proveedorId">ID</label>
                                    <input type="text" class="form-control" id="proveedorId" name="proveedorId">
                                </div>
                                <div class="form-group">
                                    <label for="cuitDni" class="required">CUIT/DNI</label>
                                    <input type="text" class="form-control" id="cuitDni" name="cuitDni" required>
                                </div>
                                <div class="form-group">
                                    <label for="nombre" class="required">Nombre</label>
                                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                                </div>
                                <div class="form-group">
                                    <label for="contacto">Contacto</label>
                                    <input type="text" class="form-control" id="contacto" name="contacto">
                                </div>
                            </div>
                            <!-- Segunda columna -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="activo">Activo</label>
                                    <select class="form-control" id="activo" name="activo">
                                        <option value="true">Sí</option>
                                        <option value="false">No</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar Proveedor</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        setupDataTableAndModal({
            dataTableId : "#dataTableProveedores",
            btnAltaId : "#btnAltaProveedor",
            btnEditarId : "#btnEditarProveedor",
            btnEliminarId : "#btnEliminarProveedor",
            formId : "#formAltaProveedor",
            modalId : "#altaProveedorModal",
            modalTitleId : "#altaProveedorModalLabel",
            altaUrl : "altaProveedor",
            editarUrl : "editarProveedor",
            eliminarUrl : "eliminarProveedor",
            entityName : "Proveedor",
            limpiarFormulario : limpiarFormularioProveedor,
            llenarFormulario : llenarFormularioProveedor
        });

        function limpiarFormularioProveedor() {
            $('#proveedorId').val('');
            $('#cuitDni').val('');
            $('#nombre').val('');
            $('#contacto').val('');
            $('#activo').val('true');
        }

        function llenarFormularioProveedor(data) {
            const proveedor = data;
            
            $('#proveedorId').val(proveedor.idProveedor);
            $('#cuitDni').val(proveedor.cuitDni);
            $('#nombre').val(proveedor.nombre);
            $('#contacto').val(proveedor.contacto);
            $('#activo').val(proveedor.activo ? 'true' : 'false');
        }
    </script>
</div>

<%@ include file="components/footer.jsp"%>
