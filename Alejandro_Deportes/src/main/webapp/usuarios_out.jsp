<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aledep.model.Usuario"%>
<%@ page import="aledep.model.Rol"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
    <!-- Título de la sección -->
    <div class="card shadow mb-4">
        <div class="card-header py-3 d-flex justify-content-between align-items-center">
            <h6 class="m-0 font-weight-bold text-primary">Usuarios de Alejandro Deportes</h6>
        </div>
        <div class="card-body">
            <div class="filter-options mb-3" style="float: left">
                <a href="usuarios?filtro=activos" class="btn btn-primary">Mostrar Activos</a>
                <a href="usuarios?filtro=todos" class="btn btn-secondary">Mostrar Todos</a>
            </div>
            <div style="float: right">
                <!-- Botón para abrir el modal de alta de usuario -->
                <a href="#" class="btn btn-primary btn-circle mr-2" id="btnAltaUsuario" data-toggle="modal" data-target="#altaUsuarioModal">
                    <i class="fas fa-plus"></i>
                </a>
                <!-- Botón para abrir el modal de edición de usuario -->
                <a href="#" class="btn btn-warning btn-circle mr-2" id="btnEditarUsuario">
                    <i class="fas fa-edit"></i>
                </a>
                <!-- Botón para eliminar usuario -->
                <a href="#" class="btn btn-danger btn-circle" id="btnEliminarUsuario">
                    <i class="fas fa-trash-alt"></i>
                </a>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTableUsers" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th style="display: none;">ID</th>
                            <th>Nombre</th>
                            <th>Rol</th>
                            <th>Email</th>
                            <th>Activo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<Usuario> listaUsuarios = (List<Usuario>) request.getSession().getAttribute("listaUsuarios");
                        if (listaUsuarios == null) {
                            listaUsuarios = new ArrayList<>(); // Inicializa la lista para evitar null
                        }

                        if (!listaUsuarios.isEmpty()) {
                            for (Usuario usuario : listaUsuarios) {
                        %>
                        <tr data-id="<%=usuario.getIdUsuario()%>">
                            <td style="display: none;"><%=usuario.getIdUsuario()%></td>
                            <td><%=usuario.getNombre()%></td>
                            <td><%=usuario.getRol() != null ? usuario.getRol().getNombre() : "Sin rol"%></td>
                            <td><%=usuario.getEmail()%></td>
                            <td><%=usuario.getActivo() ? "Sí" : "No"%></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="5">No hay usuarios disponibles</td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal para Alta de Usuario -->
    <div class="modal fade" id="altaUsuarioModal" tabindex="-1" role="dialog" aria-labelledby="altaUsuarioModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="altaUsuarioModalLabel">Nuevo Usuario</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="formAltaUsuario">
                    <div class="modal-body">
                        <!-- Contenido del formulario de alta de usuario -->
                        <div class="row">
                            <!-- Primera columna -->
                            <div class="col-md-6">
                                <div style="display: none;" class="form-group">
                                    <label for="usuarioId">ID</label>
                                    <input type="text" class="form-control" id="usuarioId" name="usuarioId">
                                </div>
                                <div class="form-group">
                                    <label for="nombre" class="required">Nombre</label>
                                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="required">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" required>
                                </div>
                                <div class="form-group">
                                    <label for="password" class="required">Contraseña</label>
                                    <input type="password" class="form-control" id="password" name="password" required>
                                </div>
                            </div>
                            <!-- Segunda columna -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="idRol" class="required">Rol</label>
                                    <select class="form-control" id="idRol" name="idRol" required>
                                        <option value="">Seleccionar Rol</option>
                                        <%
                                        List<Rol> roles = (List<Rol>) request.getSession().getAttribute("listaRoles");
                                        if (roles != null) {
                                            for (Rol rol : roles) {
                                        %>
                                        <option value="<%=rol.getIdRol()%>">
                                            <%=rol.getNombre()%>
                                        </option>
                                        <%
                                            }
                                        }
                                        %>
                                    </select>
                                </div>
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
                        <button type="submit" class="btn btn-primary">Guardar Usuario</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        setupDataTableAndModal({
            dataTableId : "#dataTableUsers",
            btnAltaId : "#btnAltaUsuario",
            btnEditarId : "#btnEditarUsuario",
            btnEliminarId : "#btnEliminarUsuario",
            formId : "#formAltaUsuario",
            modalId : "#altaUsuarioModal",
            modalTitleId : "#altaUsuarioModalLabel",
            altaUrl : "altaUsuario",
            editarUrl : "editarUsuario",
            eliminarUrl : "eliminarUsuario",
            entityName : "Usuario",
            limpiarFormulario : limpiarFormularioUsuario,
            llenarFormulario : llenarFormularioUsuario
        });

        function limpiarFormularioUsuario() {
            $('#usuarioId').val('');
            $('#nombre').val('');
            $('#email').val('');
            $('#password').val('');
            $('#idRol').val('');
            $('#activo').val('true');
        }

        function llenarFormularioUsuario(data) {
            const usuario = data;
            $('#usuarioId').val(usuario.idUsuario);
            $('#nombre').val(usuario.nombre);
            $('#email').val(usuario.email);
            $('#password').val(usuario.password);
            $('#activo').val(usuario.activo ? 'true' : 'false');
            setSelectOption('#idRol', usuario.rol, usuario.idRol);
        }

        // Utility function to set select options
        function setSelectOption(selector, text, value) {
            let select = $(selector);
            select.find('option').filter(function() {
                return $(this).val() == value || $(this).text() == text;
            }).prop('selected', true);
        }
    </script>
</div>

<%@ include file="components/footer.jsp"%>
