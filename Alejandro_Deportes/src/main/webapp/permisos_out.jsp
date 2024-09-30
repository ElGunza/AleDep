<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="aledep.model.Permiso"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
    <div class="card shadow mb-4">
        <div class="card-header py-3 d-flex justify-content-between align-items-center">
            <h6 class="m-0 font-weight-bold text-primary">Permisos del Sistema</h6>
        </div>
        <div class="card-body">
            <div class="filter-options mb-3" style="float: left">
                <a href="permisos?filtro=activos" class="btn btn-primary">Mostrar Activos</a>
                <a href="permisos?filtro=todos" class="btn btn-secondary">Mostrar Todos</a>
            </div>

            <div style="float: right">
                <a href="#" class="btn btn-primary btn-circle mr-2" id="btnAltaPermiso" data-toggle="modal" data-target="#altaPermisoModal">
                    <i class="fas fa-plus"></i>
                </a>
                <a href="#" class="btn btn-warning btn-circle mr-2" id="btnEditarPermiso">
                    <i class="fas fa-edit"></i>
                </a>
                <a href="#" class="btn btn-danger btn-circle" id="btnEliminarPermiso">
                    <i class="fas fa-trash-alt"></i>
                </a>
            </div>

            <div class="table-responsive">
                <table class="table table-bordered" id="dataTablePermiso" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th>Activo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<Permiso> listaPermisos = (List<Permiso>) request.getAttribute("listaPermisos");
                        if (listaPermisos != null && !listaPermisos.isEmpty()) {
                            for (Permiso permiso : listaPermisos) {
                        %>
                        <tr data-id="<%=permiso.getIdPermiso()%>">
                            <td><%=permiso.getIdPermiso()%></td>
                            <td><%=permiso.getNombre()%></td>
                            <td><%=permiso.getDescripcion()%></td>
                            <td><%=permiso.getActivo() ? "Sí" : "No"%></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="4">No hay permisos disponibles</td>
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

    <!-- Modal para Alta de Permiso -->
    <div class="modal fade" id="altaPermisoModal" tabindex="-1" role="dialog" aria-labelledby="altaPermisoModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="altaPermisoModalLabel">Nuevo Permiso</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="formAltaPermiso" action="altaPermiso" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="nombre" class="required">Nombre</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="descripcion">Descripción</label>
                            <input type="text" class="form-control" id="descripcion" name="descripcion">
                        </div>
                        <div class="form-group">
                            <label for="activo">Activo</label>
                            <select class="form-control" id="activo" name="activo">
                                <option value="true">Sí</option>
                                <option value="false">No</option>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar Permiso</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        setupDataTableAndModal({
            dataTableId: "#dataTablePermiso",
            btnAltaId: "#btnAltaPermiso",
            btnEditarId: "#btnEditarPermiso",
            btnEliminarId: "#btnEliminarPermiso",
            formId: "#formAltaPermiso",
            modalId: "#altaPermisoModal",
            modalTitleId: "#altaPermisoModalLabel",
            altaUrl: "altaPermiso",
            editarUrl: "editarPermiso",
            eliminarUrl: "eliminarPermiso",
            entityName: "Permiso",
            limpiarFormulario: limpiarFormularioPermiso,
            llenarFormulario: llenarFormularioPermiso
        });

        // Función para limpiar el formulario de Permiso
        function limpiarFormularioPermiso() {
            $('#nombre').val('');
            $('#descripcion').val('');
            $('#activo').val('true');
        }

        // Función para llenar el formulario de Permiso con datos para editar
        function llenarFormularioPermiso(data) {
            $('#nombre').val(data.nombre);
            $('#descripcion').val(data.descripcion || '');
            $('#activo').val(data.activo ? 'true' : 'false');
        }
    </script>

</div>

<%@ include file="components/footer.jsp"%>
