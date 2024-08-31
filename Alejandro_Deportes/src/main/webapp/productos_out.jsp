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
<%@ page import="aledep.model.Producto"%>
<%@ page import="aledep.model.Categoria"%>
<%@ page import="aledep.model.Marca"%>
<%@ page import="aledep.model.Proveedor"%>
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
			<h6 class="m-0 font-weight-bold text-primary">Productos de
				Alejandro Deportes</h6>

		</div>
		<div class="card-body">

			<div class="filter-options mb-3" style="float: left">
				<a href="productos?filtro=activos" class="btn btn-primary">Mostrar
					Activos</a> <a href="productos?filtro=todos" class="btn btn-secondary">Mostrar
					Todos</a>
			</div>
			<div style="float: right">
				<!-- Botón para abrir el modal de alta de producto -->
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnAltaProducto" data-toggle="modal"
					data-target="#altaProductoModal"> <i class="fas fa-plus"></i>
				</a>
				<!-- Botón para abrir el modal de edición de producto -->
				<a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarProducto"> <i class="fas fa-edit"></i>
				</a>
				<!-- Botón para eliminar producto -->
				<a href="#" class="btn btn-danger btn-circle"
					id="btnEliminarProducto"> <i class="fas fa-trash-alt"></i>
				</a>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th style="display: none;">ID</th>
							<th>Código</th>
							<th>Categoría</th>
							<th>Nombre</th>
							<th>Marca</th>
							<th>Talles disponibles</th>
							<th>Cantidad</th>
							<th>Precio de venta</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<Producto> listaProductos = (List<Producto>) request.getSession().getAttribute("listaProductos");
						if (listaProductos == null) {
							listaProductos = new ArrayList<>(); // Inicializa la lista para evitar null
						}

						if (!listaProductos.isEmpty()) {
							for (Producto producto : listaProductos) {
						%>
						<tr data-id="<%=producto.getIdProducto()%>">
							<td style="display: none;"><%=producto.getIdProducto()%></td>
							<td><%=producto.getCodigo()%></td>
							<td><%=producto.getCategoria() != null ? producto.getCategoria().getNombre() : "Sin categoría"%></td>
							<td><%=producto.getNombre()%></td>
							<td><%=producto.getMarca() != null ? producto.getMarca().getNombre() : "Sin Marca"%></td>
							<td><%=producto.getTalle()%></td>
							<td><%=producto.getCantidad()%></td>
							<td>$<%=String.format("%.2f", producto.getPrecioVenta())%></td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="7">No hay productos disponibles</td>
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

	<!-- Modal para Alta de Producto -->
	<div class="modal fade" id="altaProductoModal" tabindex="-1"
		role="dialog" aria-labelledby="altaProductoModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="altaProductoModalLabel">Nuevo
						Producto</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formAltaProducto">
					<div class="modal-body">
						<!-- Contenido del formulario de alta de producto -->
						<div class="row">
							<!-- Primera columna -->
							<div class="col-md-6">

								<div style="display: none;" class="form-group">
									<label for="productoId">ID</label> <input type="text"
										class="form-control" id="productoId" name="productoId">
								</div>

								<div class="form-group">
									<label for="codigo" class="required">Código</label> <input
										type="text" class="form-control" id="codigo" name="codigo"
										required>
								</div>
								<div class="form-group">
									<label for="nombre" class="required">Nombre</label> <input
										type="text" class="form-control" id="nombre" name="nombre"
										required>
								</div>
								<div class="form-group">
									<label for="idCategoria" class="required">Categoría</label> <select
										class="form-control" id="idCategoria" name="idCategoria"
										required>
										<option value="">Seleccionar Categoría</option>
										<%
										List<Categoria> categorias = (List<Categoria>) request.getSession().getAttribute("listaCategorias");
										if (categorias != null) {
											for (Categoria categoria : categorias) {
										%>
										<option value="<%=categoria.getIdCategoria()%>"><%=categoria.getNombre()%></option>
										<%
										}
										}
										%>
									</select>
								</div>
								<div class="form-group">
									<label for="idMarca" class="required">Marca</label> <select
										class="form-control" id="idMarca" name="idMarca" required>
										<option value="">Seleccionar Marca</option>
										<%
										List<Marca> marcas = (List<Marca>) request.getSession().getAttribute("listaMarcas");
										if (marcas != null) {
											for (Marca marca : marcas) {
										%>
										<option value="<%=marca.getIdMarca()%>"><%=marca.getNombre()%></option>
										<%
										}
										}
										%>
									</select>
								</div>
								<div class="form-group">
									<label for="talle">Talles Disponibles</label> <input
										type="text" class="form-control" id="talle" name="talle">
								</div>
								<div class="form-group">
									<label for="cantidadMax">Cantidad Máxima</label> <input
										type="number" class="form-control" id="cantidadMax"
										name="cantidadMax">
								</div>
								<div class="form-group">
									<label for="cantidadMin">Cantidad Mínima</label> <input
										type="number" class="form-control" id="cantidadMin"
										name="cantidadMin">
								</div>
								<div class="form-group">
									<label for="idDeposito">Depósito</label> <select
										class="form-control" id="idDeposito" name="idDeposito">
										<option value="">Seleccionar Depósito</option>
										<%
										List<Deposito> depositos = (List<Deposito>) request.getSession().getAttribute("listaDepositos");
										if (depositos != null) {
											for (Deposito deposito : depositos) {
										%>
										<option value="<%=deposito.getIdDeposito()%>"><%=deposito.getNombre()%></option>
										<%
										}
										}
										%>
									</select>
								</div>
								<div class="form-group">
									<label for="fechaCreacion">Fecha de Creación</label> <input
										type="date" class="form-control" id="fechaCreacion"
										name="fechaCreacion">
								</div>
							</div>
							<!-- Segunda columna -->
							<div class="col-md-6">
								<div class="form-group">
									<label for="precioVenta" class="required">Precio de
										Venta</label> <input type="number" class="form-control"
										id="precioVenta" name="precioVenta" step="0.01" required>
								</div>
								<div class="form-group">
									<label for="cantidad" class="required">Cantidad</label> <input
										type="number" class="form-control" id="cantidad"
										name="cantidad" required>
								</div>
								<div class="form-group">
									<label for="precioCompra">Precio de Compra</label> <input
										type="number" class="form-control" id="precioCompra"
										name="precioCompra" step="0.01">
								</div>
								<div class="form-group">
									<label for="colores">Colores</label> <input type="text"
										class="form-control" id="colores" name="colores">
								</div>
								<div class="form-group">
									<label for="imageProduct">Imagen del Producto</label> <input
										type="text" class="form-control" id="imageProduct"
										name="imageProduct">
								</div>
								<div class="form-group">
									<label for="codigoBarras">Código de Barras</label> <input
										type="text" class="form-control" id="codigoBarras"
										name="codigoBarras">
								</div>
								<div class="form-group">
									<label for="enlaceProducto">Enlace del Producto</label> <input
										type="text" class="form-control" id="enlaceProducto"
										name="enlaceProducto">
								</div>
								<div class="form-group">
									<label for="idProveedor">Proveedor</label> <select
										class="form-control" id="idProveedor" name="idProveedor">
										<option value="">Seleccionar Proveedor</option>
										<%
										List<Proveedor> proveedores = (List<Proveedor>) request.getSession().getAttribute("listaProveedores");
										if (proveedores != null) {
											for (Proveedor proveedor : proveedores) {
										%>
										<option value="<%=proveedor.getIdProveedor()%>"><%=proveedor.getNombre()%></option>
										<%
										}
										}
										%>
									</select>
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
							Producto</button>
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
	                    "emptyTable": "No hay productos disponibles"
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

	            // Botón para abrir el modal de Alta de Producto
	            $('#btnAltaProducto').on('click', function() {
	                // Limpiar el formulario
	                limpiarFormularioProducto();

	                // Cambiar el título del modal
	                $('#altaProductoModalLabel').text('Nuevo Producto');

	                // Mostrar el modal
	                $('#altaProductoModal').modal('show');
	            });

	            // Manejar el envío del formulario del modal (alta o edición)
	            $('#formAltaProducto').on('submit', function(event) {
	                event.preventDefault(); // Prevenir el comportamiento por defecto del formulario

	                let url = selectedId ? 'editarProducto' : 'altaProducto'; // Determinar si es alta o edición

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
	                                $('#altaProductoModal').modal('hide');
	                                // Recargar la tabla de productos
	                                location.reload();
	                            });
	                        } else {
	                            Swal.fire({
	                                icon: 'error',
	                                title: 'Error',
	                                text: 'Hubo un problema al guardar el producto.'
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

	            // Botón para abrir el modal de Edición de Producto
	            $('#btnEditarProducto').on('click', function() {
	                if (selectedId) {
	                    $.ajax({
	                        url: 'editarProducto',
	                        type: 'GET',
	                        data: { id: selectedId },
	                        success: function(data) {
	                            llenarFormularioProducto(data);

	                            // Cambiar el título del modal
	                            $('#altaProductoModalLabel').text('Editar Producto');
	                            $('#altaProductoModal').modal('show');
	                        },
	                        error: function() {
	                            Swal.fire({
	                                icon: 'error',
	                                title: 'Error',
	                                text: 'No se pudo cargar la información del producto.'
	                            });
	                        }
	                    });
	                } else {
	                    Swal.fire({
	                        icon: 'warning',
	                        title: 'Advertencia',
	                        text: 'Por favor, selecciona un producto primero.'
	                    });
	                }
	            });

	            $('#dataTable tbody').on('dblclick', 'tr', function() {
	                $('#btnEditarProducto').trigger('click');
	            });
	            
	            
	         // Botón para eliminar producto
	            $('#btnEliminarProducto').on('click', function() {
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
	                                url: 'eliminarProducto',
	                                type: 'POST',
	                                data: { id: selectedId },
	                                success: function(response) {
	                                    if (response.status === 'success') {
	                                        Swal.fire({
	                                            icon: 'success',
	                                            title: 'Eliminado',
	                                            text: response.message
	                                        }).then(() => {
	                                            // Recargar la tabla de productos
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
	                        text: 'Por favor, selecciona un producto primero.'
	                    });
	                }
	            });
	            
	            /* FUNCIONES */
	                    
	            // Función para limpiar el formulario de producto
	            function limpiarFormularioProducto() {
	                $('#productoId').val('');
	                $('#codigo').val('');
	                $('#nombre').val('');
	                $('#idCategoria').val('');
	                $('#idMarca').val('');
	                $('#talle').val('');
	                $('#cantidad').val('');
	                $('#precioVenta').val('');
	                $('#precioCompra').val('');
	                $('#colores').val('');
	                $('#imageProduct').val('');
	                $('#codigoBarras').val('');
	                $('#enlaceProducto').val('');
	                $('#idProveedor').val('');
	                $('#idDeposito').val('');
	                $('#activo').val('true');
	            }

	            // Función para llenar el formulario de producto con datos para editar
	            function llenarFormularioProducto(data) {
	            	
	                $('#productoId').val(data.idProducto);
	                $('#codigo').val(data.codigo);
	                $('#nombre').val(data.nombre);

	                $('#talle').val(data.talle);
	                $('#cantidad').val(data.cantidad);
	                $('#precioVenta').val(data.precioVenta);
	                $('#precioCompra').val(data.precioCompra);
	                $('#colores').val(data.colores);
	                $('#imageProduct').val(data.imageProduct);
	                $('#codigoBarras').val(data.codigoBarras);
	                $('#enlaceProducto').val(data.enlaceProducto);
	                //$('#idProveedor').val(data.idProveedor);
	                //$('#idDeposito').val(data.idDeposito);
	                //$('#idCategoria').val(data.idCategoria);
	                //$('#idMarca').val(data.idMarca);
	                
	                $('#activo').val(data.activo.toString());
	                
	                $('#idCategoria option').filter(function() {
	                    return $(this).text() == data.categoria;
	                }).prop('selected', true);

	                $('#idMarca option').filter(function() {
	                    return $(this).text() == data.marca;
	                }).prop('selected', true);

	                $('#idProveedor option').filter(function() {
	                    return $(this).text() == data.proveedor;
	                }).prop('selected', true);

	                $('#idDeposito option').filter(function() {
	                    return $(this).text() == data.deposito;
	                }).prop('selected', true);

	                
	                
	            }
	        });
	    });
	</script>

</div>


<%@ include file="components/footer.jsp"%>
