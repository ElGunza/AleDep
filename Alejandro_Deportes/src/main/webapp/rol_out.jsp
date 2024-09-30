<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="aledep.model.Rol"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
    <div class="card shadow mb-4">
        <div class="card-header py-3 d-flex justify-content-between align-items-center">
            <h6 class="m-0 font-weight-bold text-primary">Roles de Usuarios</h6>
        </div>
        <div class="card-body">

            <div class="filter-options mb-3" style="float: left">
                <a href="roles?filtro=activos" class="btn btn-primary">Mostrar Activos</a>
                <a href="roles?filtro=todos" class="btn btn-secondary">Mostrar Todos</a>
            </div>

            <div style="float: right">
                <a href="#" class="btn btn-primary btn-circle mr-2" id="btnAltaRol" data-toggle="modal" data-target="#altaRolModal">
                    <i class="fas fa-plus"></i>
                </a>
                <a href="#" class="btn btn-warning btn-circle mr-2" id="btnEditarRol">
                    <i class="fas fa-edit"></i>
                </a>
                <a href="#" class="btn btn-danger btn-circle" id="btnEliminarRol">
                    <i class="fas fa-trash-alt"></i>
                </a>
            </div>

            <div class="table-responsive">
                <table class="table table-bordered" id="dataTableRol" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<Rol> listaRoles = (List<Rol>) request.getAttribute("listaRoles");
                        if (listaRoles != null && !listaRoles.isEmpty()) {
                            for (Rol rol : listaRoles) {
                        %>
                        <tr data-id="<%=rol.getIdRol()%>">
                            <td><%=rol.getIdRol()%></td>
                            <td><%=rol.getNombre()%></td>
                            <td><%=rol.getDescripcion()%></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="3">No hay roles disponibles</td>
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

    <!-- Modal para Alta de Rol con Gestión de Permisos -->
    <div class="modal fade" id="altaRolModal" tabindex="-1" role="dialog" aria-labelledby="altaRolModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="altaRolModalLabel">Nuevo Rol</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="formAltaRol" method="post" action="altaRol">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="nombre" class="required">Nombre</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" required>
                        </div>
                        <div class="form-group">
                            <label for="descripcion">Descripción</label>
                            <input type="text" class="form-control" id="descripcion" name="descripcion">
                        </div>

                        <!-- Gestión de Permisos -->
                        <div class="form-group">
                            <label for="permisos">Permisos Disponibles</label>
                            <div id="listaPermisos">
                                <!-- Aquí se cargan dinámicamente los permisos -->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar Rol</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function() {
            // Cargar permisos cuando se abre el modal de alta o edición de roles
            $('#altaRolModal').on('show.bs.modal', function (event) {
                var modal = $(this);
                $.ajax({
                    url: 'permisos?json=true', // Solicitud para obtener los permisos en JSON
                    method: 'GET',
                    success: function(data) {
                        // Limpiar la lista de permisos
                        $('#listaPermisos').empty();

                        // Cargar permisos en el modal
                        data.forEach(function(permiso) {
                            var checkbox = `<div class="form-check">
                                <input class="form-check-input" type="checkbox" id="permiso_${permiso.idPermiso}" name="permisos" value="${permiso.idPermiso}">
                                <label class="form-check-label" for="permiso_${permiso.idPermiso}">${permiso.nombre}</label>
                            </div>`;
                            $('#listaPermisos').append(checkbox);
                        });
                    }
                });
            });
        });

        setupDataTableAndModal({
            dataTableId: "#dataTableRol",
            btnAltaId: "#btnAltaRol",
            btnEditarId: "#btnEditarRol",
            btnEliminarId: "#btnEliminarRol",
            formId: "#formAltaRol",
            modalId: "#altaRolModal",
            modalTitleId: "#altaRolModalLabel",
            altaUrl: "altaRol",
            editarUrl: "editarRol",
            eliminarUrl: "eliminarRol",
            entityName: "Rol",
            limpiarFormulario: limpiarFormularioRol,
            llenarFormulario: llenarFormularioRol
        });

        // Función para limpiar el formulario de Rol
        function limpiarFormularioRol() {
            $('#nombre').val('');
            $('#descripcion').val('');
            $('#listaPermisos').empty(); // Limpiamos los permisos cuando se limpia el formulario
        }

        // Función para llenar el formulario de Rol con datos para editar
        function llenarFormularioRol(data) {
            $('#nombre').val(data.nombre);
            $('#descripcion').val(data.descripcion || '');
            // Aquí puedes agregar lógica adicional para marcar los permisos ya asignados
        }
    </script>
</div>

<%@ include file="components/footer.jsp"%>
