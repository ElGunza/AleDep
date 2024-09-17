<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aledep.model.Cliente"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
    <!-- Título de la sección -->
    <div class="card shadow mb-4">
        <div class="card-header py-3 d-flex justify-content-between align-items-center">
            <h6 class="m-0 font-weight-bold text-primary">Clientes de Alejandro Deportes</h6>
        </div>
        <div class="card-body">
            <div class="filter-options mb-3" style="float: left">
                <a href="clientes?filtro=activos" class="btn btn-primary">Mostrar Activos</a>
                <a href="clientes?filtro=todos" class="btn btn-secondary">Mostrar Todos</a>
            </div>
            <div style="float: right">
                <!-- Botón para abrir el modal de alta de cliente -->
                <a href="#" class="btn btn-primary btn-circle mr-2" id="btnAltaCliente" data-toggle="modal" data-target="#altaClienteModal">
                    <i class="fas fa-plus"></i>
                </a>
                <!-- Botón para abrir el modal de edición de cliente -->
                <a href="#" class="btn btn-warning btn-circle mr-2" id="btnEditarCliente">
                    <i class="fas fa-edit"></i>
                </a>
                <!-- Botón para eliminar cliente -->
                <a href="#" class="btn btn-danger btn-circle" id="btnEliminarCliente">
                    <i class="fas fa-trash-alt"></i>
                </a>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTableClientes">
                    <thead>
                        <tr>
                            <th>CUIT/DNI</th>
                            <th>Nombre</th>
                            <th>Dirección</th>
                            <th>Teléfono</th>
                            <th>Email</th>
                            <th>Deuda</th>
                            <th>Activo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<Cliente> listaClientes = (List<Cliente>) request.getSession().getAttribute("listaClientes");
                        if (listaClientes == null) {
                            listaClientes = new ArrayList<>(); 
                        }

                        if (!listaClientes.isEmpty()) {
                            for (Cliente cliente : listaClientes) {
                        %>
                        <tr data-id="<%=cliente.getIdCliente()%>">
                            <td><%=cliente.getCuitDni()%></td>
                            <td><%=cliente.getNombre()%></td>
                            <td><%=cliente.getDireccion()%></td>
                            <td><%=cliente.getTelefono()%></td>
                            <td><%=cliente.getEmail()%></td>
                            <td>$ <%=String.format("%.2f", cliente.getDeuda())%></td>
                            <td><%=cliente.getActivo() ? "Sí" : "No"%></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="8">No hay clientes disponibles</td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal para Alta de Cliente -->
    <div class="modal fade" id="altaClienteModal" tabindex="-1" role="dialog" aria-labelledby="altaClienteModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="altaClienteModalLabel">Nuevo Cliente</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form id="formAltaCliente">
                    <div class="modal-body">
                        <!-- Contenido del formulario de alta de cliente -->
                        <div class="row">
                            <!-- Primera columna -->
                            <div class="col-md-6">
                                <div style="display: none;" class="form-group">
                                    <label for="clienteId">ID</label>
                                    <input type="text" class="form-control" id="clienteId" name="clienteId">
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
                                    <label for="direccion">Dirección</label>
                                    <input type="text" class="form-control" id="direccion" name="direccion">
                                </div>
                                <div class="form-group">
                                    <label for="telefono">Teléfono</label>
                                    <input type="text" class="form-control" id="telefono" name="telefono">
                                </div>
                            </div>
                            <!-- Segunda columna -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="email" class="form-control" id="email" name="email">
                                </div>
                                <div class="form-group">
                                    <label for="deuda">Deuda</label>
                                    <input type="text" class="form-control" id="deuda" name="deuda">
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
                        <button type="submit" class="btn btn-primary">Guardar Cliente</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        setupDataTableAndModal({
            dataTableId : "#dataTableClientes",
            btnAltaId : "#btnAltaCliente",
            btnEditarId : "#btnEditarCliente",
            btnEliminarId : "#btnEliminarCliente",
            formId : "#formAltaCliente",
            modalId : "#altaClienteModal",
            modalTitleId : "#altaClienteModalLabel",
            altaUrl : "altaCliente",
            editarUrl : "editarCliente",
            eliminarUrl : "eliminarCliente",
            entityName : "Cliente",
            limpiarFormulario : limpiarFormularioCliente,
            llenarFormulario : llenarFormularioCliente
        });

        function limpiarFormularioCliente() {
            $('#clienteId').val('');
            $('#cuitDni').val('');
            $('#nombre').val('');
            $('#direccion').val('');
            $('#telefono').val('');
            $('#email').val('');
            $('#deuda').val('');
            $('#activo').val('true');
        }

        function llenarFormularioCliente(data) {
            const cliente = data;
            
            $('#clienteId').val(cliente.idCliente);
            $('#cuitDni').val(cliente.cuitDni);
            $('#nombre').val(cliente.nombre);
            $('#direccion').val(cliente.direccion);
            $('#telefono').val(cliente.telefono);
            $('#email').val(cliente.email);
            $('#deuda').val(cliente.deuda.toFixed(2));
            $('#activo').val(cliente.activo ? 'true' : 'false');
        }
    </script>
</div>

<%@ include file="components/footer.jsp"%>
