<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="aledep.model.Proveedor"%>
<%@ page import="aledep.model.Usuario"%>
<%@ page import="aledep.model.MetodoPago"%>
<%@ page import="aledep.model.Producto"%>
<%@ page import="aledep.dto.CompraDTO"%>
<%@ page import="aledep.dto.CompraDetalleDTO"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>
<div class="container-fluid">
	<!-- Mensajes de éxito y error -->
	<div id="successMessage" class="alert alert-success"
		style="display: none;"></div>
	<div id="errorMessage" class="alert alert-danger"
		style="display: none;"></div>
	<!-- Compras Registradas -->
	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Compras
				Registradas</h6>
		</div>
		<div class="card-body">
			<div style="float: right" class="mb-3">
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnRegistrarCompra" data-toggle="modal"
					data-target="#registrarCompraModal"> <i class="fas fa-plus"></i>
				</a> <a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarCompra"> <i class="fas fa-edit"></i>
				</a>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTableCompras">
					<thead>
						<tr>
							<th>N° Compra</th>
							<th>Fecha de Creación</th>
							<th>Proveedor</th>
							<th>Usuario</th>
							<th>Método de Pago</th>
							<th>Precio Total</th>
							<th>Productos</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<CompraDTO> listaComprasDTO = (List<CompraDTO>) request.getSession().getAttribute("listaComprasDTO");
						if (listaComprasDTO != null && !listaComprasDTO.isEmpty()) {
							for (CompraDTO compraDTO : listaComprasDTO) {
						%>
						<tr data-id="<%=compraDTO.getIdCompra()%>">
							<td><%=compraDTO.getIdCompra()%></td>
							<td><%=compraDTO.getFechaCreacionStr()%></td>
							<td><%=compraDTO.getProveedor()%></td>
							<td><%=compraDTO.getUsuario()%></td>
							<td><%=compraDTO.getMetodoPago()%></td>
							<td>$ <%=String.format("%.2f", compraDTO.getPrecioTotal().doubleValue())%></td>

							<td>
								<ul>
									<%
									for (CompraDetalleDTO detalleDTO : compraDTO.getDetalles()) {
									%>
									<li><%=detalleDTO.getProducto()%> - Cantidad: <%=detalleDTO.getCantidad()%>
									</li>
									<%
									}
									%>
								</ul>
							</td>
						</tr>
						<%
						}
						} else {
						%>
						<tr>
							<td colspan="7">No hay compras registradas</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Modal para Registrar/Editar Compra -->
	<div class="modal fade" id="registrarCompraModal" role="dialog"
		aria-labelledby="registrarCompraModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="registrarCompraModalLabel">Registrar
						Nueva Compra</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formRegistrarCompra">
					<div class="modal-body">
						<div class="row">
							<!-- Columna izquierda: Proveedor, Usuario, Fecha de Creación -->
							<div class="col-md-6">
								<div class="form-group">
									<label for="codigo">Código</label> <input type="text"
										class="form-control" id="compraId" name="compraId" readonly>
								</div>
								<div class="form-group">
									<label for="proveedorId" class="required">Proveedor</label> <select
										class="form-control select2" id="proveedorId"
										name="proveedorId" required>
										<option value="">Seleccionar Proveedor</option>
										<%
										List<Proveedor> proveedores = (List<Proveedor>) request.getSession().getAttribute("listaProveedores");
										if (proveedores != null) {
											for (Proveedor proveedor : proveedores) {
										%>
										<option value="<%=proveedor.getIdProveedor()%>">
											<%=proveedor.getNombre()%>
										</option>
										<%
										}
										}
										%>
									</select>
								</div>
								<div class="form-group">
									<label for="usuarioId" class="required">Usuario</label> <select
										class="form-control select2" id="usuarioId" name="usuarioId"
										required>
										<option value="">Seleccionar Usuario</option>
										<%
										List<Usuario> usuarios = (List<Usuario>) request.getSession().getAttribute("listaUsuarios");
										if (usuarios != null) {
											for (Usuario usuario : usuarios) {
										%>
										<option value="<%=usuario.getIdUsuario()%>">
											<%=usuario.getNombre()%>
										</option>
										<%
										}
										}
										%>
									</select>
								</div>
							</div>
							<!-- Columna derecha: Método de Pago -->
							<div class="col-md-6">
								<div class="form-group">
									<label for="fechaCreacion">Fecha de Creación</label> <input
										type="date" class="form-control" id="fechaCreacion"
										name="fechaCreacion" required>
								</div>
								<div class="form-group">
									<label for="metodoPagoId" class="required">Método de
										Pago</label> <select class="form-control select2" id="metodoPagoId"
										name="metodoPagoId" required>
										<option value="">Seleccionar Método de Pago</option>
										<%
										List<MetodoPago> metodosPago = (List<MetodoPago>) request.getSession().getAttribute("listaMetodosPago");
										if (metodosPago != null) {
											for (MetodoPago metodoPago : metodosPago) {
										%>
										<option value="<%=metodoPago.getIdMetPago()%>">
											<%=metodoPago.getNombre()%>
										</option>
										<%
										}
										}
										%>
									</select>
								</div>
								<!-- Precio total destacado -->
								<div class="form-group text-right">
									<h4>
										Total: $ <span id="precioTotal">0.00</span>
									</h4>
									<input type="hidden" id="precioTotalInput" name="precioTotal">
								</div>
							</div>
						</div>
						<!-- Tabla de productos -->
						<h5>Productos</h5>
						<div class="table-responsive">
							<table class="table table-bordered" id="productsBuyTable">
								<thead>
									<tr>
										<th>Producto</th>
										<th>Cantidad</th>
										<th>Precio Unitario</th>
										<th>Total</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody id="productsBuyContainer">
									<!-- Aquí se agregarán las filas de productos -->
								</tbody>
							</table>
						</div>
						<!-- Botón para agregar producto -->
						<button type="button" class="btn btn-secondary mb-3"
							id="addProductButton">Agregar Producto</button>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Guardar
							Compra</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="components/footer.jsp"%>
	<script>
    $(document).ready(function() {

    	$('#dataTableCompras').DataTable({
    	    "order": [[0, "desc"]],
    	});

        // Inicializar select2 para los selects
        $('.select2').select2({
            width: '100%',
            placeholder: "Seleccionar una opción",
            allowClear: true,
            dropdownParent: $('#registrarCompraModal')
        });
        
        $('.select2').on('select2:open', function(e) {
            const evt = "scroll.select2";
            $(e.target).parents().off(evt); // Desactivar scroll en los elementos padres
            $(window).off(evt); // Desactivar scroll en la ventana principal
        });

        let selectedId = null;
        let preciosUnitarios = []; // Array para almacenar precios unitarios de cada producto


        // Manejar selección de filas en la tabla
        $('#dataTableCompras tbody').on('click', 'tr', function() {
            $('#dataTableCompras tbody tr').removeClass('selected');
            $(this).addClass('selected');
            selectedId = $(this).data('id');
        });

        // Doble click para editar la compra
        $('#dataTableCompras tbody').on('dblclick', 'tr', function() {
            $('#btnEditarCompra').trigger('click');
        });
        
        // Abrir modal para registrar nueva compra
        $('#btnRegistrarCompra').on('click', function() {
            selectedId = null;
            $('#formRegistrarCompra')[0].reset();
            $('.select2').val(null).trigger('change');
            $('#productsBuyContainer').empty();
            $('#registrarCompraModalLabel').text('Registrar Nueva Compra');
            $('#registrarCompraModal').modal('show');
        });

        // Abrir modal para editar compra
        $('#btnEditarCompra').on('click', function() {
            if (selectedId) {
                $.ajax({
                    url: 'editarCompra',
                    type: 'GET',
                    data: { id: selectedId },
                    success: function(data) {
                        llenarFormularioCompra(data);
                        $('#registrarCompraModalLabel').text('Editar Compra');
                        $('#registrarCompraModal').modal('show');
                    },
                    error: function() {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'No se pudo cargar la información de la compra.'
                        });
                    }
                });
            } else {
                Swal.fire({
                    icon: 'warning',
                    title: 'Advertencia',
                    text: 'Por favor, selecciona una compra primero.'
                });
            }
        });

        // Eliminar compra
        $('#btnAnularCompra').on('click', function() {
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
                            url: 'eliminarCompra',
                            type: 'POST',
                            data: { id: selectedId },
                            success: function() {
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Eliminado',
                                    text: 'La compra ha sido eliminada.'
                                }).then(() => {
                                    location.reload();
                                });
                            },
                            error: function() {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'Hubo un problema al eliminar la compra.'
                                });
                            }
                        });
                    }
                });
            } else {
                Swal.fire({
                    icon: 'warning',
                    title: 'Advertencia',
                    text: 'Por favor, selecciona una compra primero.'
                });
            }
        });

        // Enviar formulario para registrar o editar compra
        $('#formRegistrarCompra').on('submit', function(event) {
            event.preventDefault();
            let url = selectedId ? 'editarCompra' : 'registrarCompra';

            $.ajax({
                url: url,
                type: 'POST',
                data: $(this).serialize(),
                success: function(response) {
                    if (response.status === 'success') {
                        Swal.fire({
                            icon: 'success',
                            title: 'Compra guardada con éxito',
                            text: response.message
                        }).then(() => {
                            $('#registrarCompraModal').modal('hide');
                            location.reload();
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Hubo un problema al guardar la compra.'
                        });
                    }
                },
                error: function(xhr) {
                    const response = JSON.parse(xhr.responseText);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: response.message
                    });
                }
            });
        });

        // Añadir fila para nuevo producto
        $('#addProductButton').on('click', function() {
            addProductRow("", "", 1, 0, 0);
        });
        
        // Actualizar el total de la compra cuando cambian cantidad o precio unitario
        $('#productsBuyContainer').on('input', '.cantidad, .precio-unitario', function() {
            $(this).data('manual', true); // Marcar que el precio ha sido modificado manualmente
            actualizarTotalCompra();
        });

        // Limpiar modal al cerrarlo
        $('#registrarCompraModal').on('hidden.bs.modal', function() {
            $('#formRegistrarCompra')[0].reset();
            $('#productsBuyContainer').empty();
            $('#precioTotal').text("");
            $('#precioTotalInput').val(0);
            $('.select2').val(null).trigger('change');
        });

        // Función para crear un select de productos
        function createSelectProduct(productId) {
            var select = $('<select></select>').addClass('productBuyselect form-control select2').attr('name', 'productoId[]').attr('required', true);

            <%List<Producto> productos = (List<Producto>) request.getSession().getAttribute("listaProductos");
if (productos != null) {
	for (Producto producto : productos) {%>
            var option = $('<option></option>').val('<%=producto.getIdProducto()%>').text('<%=producto.getNombre()%>');
            if (productId == "<%=producto.getIdProducto()%>") {
                option.attr('selected', true);
            }
            select.append(option);
            <%}
}%>

            select.select2({
                width: '100%',
                placeholder: "Seleccionar una opción"
            });
            
            // Al cambiar producto, actualizar el precio unitario si no ha sido modificado manualmente
            select.on('change', function() {
            	
            	debugger;
            		
                var selectedProductId = $(this).val();  // Usamos el valor del select para obtener el producto seleccionado
                var precioUnitario = preciosUnitarios[selectedProductId]; // Obtener precio desde el array usando el ID

                var precioUnitarioInput = $(this).closest('tr').find('.precio-unitario');

                // Solo actualizar si el precio no ha sido cambiado manualmente
                if (!precioUnitarioInput.data('manual') && precioUnitario !== undefined) {
                    precioUnitarioInput.val(precioUnitario.toFixed(2)); // Asignar precio
                }
                actualizarTotalCompra();
            });

            return select;
        }

        // Añadir una fila con un producto
        function addProductRow(idCompraDetalle, productoId, cantidad, precioUnitario, totalProducto) {
            var row = $('<tr></tr>');
            var productCell = $('<td></td>').append(createSelectProduct(productoId));
            var cantidadInput = createInput("cantidad[]", cantidad, "number", false, ["cantidad"]);
            var precioUnitarioInput = createInput("precioUnitario[]", precioUnitario.toFixed(2), "text", false, ["precio-unitario"]);
            var totalProductoInput = createInput("totalProducto[]", totalProducto.toFixed(2), "text", true, ["total-producto"]);
            var eliminarButton = $('<button></button>').addClass("btn btn-danger mt-2").attr("type", "button").text("Eliminar");

            eliminarButton.on('click', function() {
                row.remove();
                actualizarTotalCompra();
            });

            row.append(productCell)
                .append($('<td></td>').append(cantidadInput))
                .append($('<td></td>').append(precioUnitarioInput))
                .append($('<td></td>').append(totalProductoInput))
                .append($('<td></td>').append(eliminarButton));

            $('#productsBuyContainer').append(row);

            $('.productBuyselect').select2({
                width: '100%',
                placeholder: "Seleccionar una opción"
            });
        }

        // Actualizar el total de la compra
        function actualizarTotalCompra() {
            let totalCompra = 0;

            $('#productsBuyContainer tr').each(function() {
                const cantidad = parseFloat($(this).find('.cantidad').val()) || 0;
                const precioUnitario = parseFloat($(this).find('.precio-unitario').val()) || 0;
                const totalProducto = cantidad * precioUnitario;

                $(this).find('.total-producto').val(totalProducto.toFixed(2));
                totalCompra += totalProducto;
            });

            $('#precioTotal').text(totalCompra.toFixed(2));
            $('#precioTotalInput').val(totalCompra.toFixed(2));
        }

        // Llenar formulario de compra
        function llenarFormularioCompra(data) {
            $('#compraId').val(data.idCompra).trigger('change');
            $('#proveedorId').val(data.idProveedor).trigger('change');
            $('#usuarioId').val(data.idUsuario).trigger('change');
            $('#metodoPagoId').val(data.idMetodoPago).trigger('change');

            if (data.fechaCreacionStr) {
                const partesFecha = data.fechaCreacionStr.split('/');
                const dia = partesFecha[0];
                const mes = partesFecha[1];
                const anio = partesFecha[2];
                const fechaFormateada = anio + '-' + mes + '-' + dia;
                $('#fechaCreacion').val(fechaFormateada);
            } else {
                $('#fechaCreacion').val('');
            }

            $('#productsBuyContainer').empty();

            data.detalles.forEach(function(detalle) {
                addProductRow(detalle.idCompraDetalle, detalle.idProducto, detalle.cantidad, detalle.precioUnitario, detalle.cantidad * detalle.precioUnitario);
            });

            $('#precioTotal').text(data.precioTotal.toFixed(2));
            $('#precioTotalInput').val(data.precioTotal.toFixed(2));
        }

        // Crear inputs dinámicamente
        function createInput(name, value, type = "text", readonly = false, additionalClasses = []) {
            var input = $('<input></input>').addClass('form-control').attr('name', name).attr('type', type).val(value);
            if (readonly) {
                input.prop('readonly', true);
            }
            additionalClasses.forEach(clase => input.addClass(clase));
            return input;
        }
    });
    </script>
</div>