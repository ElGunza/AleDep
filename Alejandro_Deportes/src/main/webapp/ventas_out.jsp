<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="aledep.model.Cliente"%>
<%@ page import="aledep.model.Usuario"%>
<%@ page import="aledep.model.MetodoPago"%>
<%@ page import="aledep.model.Producto"%>
<%@ page import="aledep.dto.VentaDTO"%>
<%@ page import="aledep.dto.VentaDetalleDTO"%>
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
	<!-- Ventas Registradas -->
	<div class="card shadow mb-4">
		<div
			class="card-header py-3 d-flex justify-content-between align-items-center">
			<h6 class="m-0 font-weight-bold text-primary">Ventas Registradas</h6>
		</div>
		<div class="card-body">
			<div style="float: right" class="mb-3">
				<a href="#" class="btn btn-primary btn-circle mr-2"
					id="btnRegistrarVenta" data-toggle="modal"
					data-target="#registrarVentaModal"> <i class="fas fa-plus"></i>
				</a> <a href="#" class="btn btn-warning btn-circle mr-2"
					id="btnEditarVenta"> <i class="fas fa-edit"></i>
				</a> <a href="#" class="btn btn-danger btn-circle" id="btnAnularVenta">
					<i class="fas fa-trash-alt"></i>
				</a>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered" id="dataTableVentas">
					<thead>
						<tr>
							<th>N° Venta</th>
							<th>Fecha de Creación</th>
							<th>Cliente</th>
							<th>Usuario</th>
							<th>Método de Pago</th>

							<th>Precio Total</th>
							<th>Productos</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<VentaDTO> listaVentasDTO = (List<VentaDTO>) request.getSession().getAttribute("listaVentasDTO");
						if (listaVentasDTO != null && !listaVentasDTO.isEmpty()) {
							for (VentaDTO ventaDTO : listaVentasDTO) {
						%>
						<tr data-id="<%=ventaDTO.getIdVenta()%>">
							<td><%=ventaDTO.getIdVenta()%></td>
							<td><%=ventaDTO.getFechaCreacionStr()%></td>
							<td><%=ventaDTO.getCliente()%></td>
							<td><%=ventaDTO.getUsuario()%></td>
							<td><%=ventaDTO.getMetodoPago()%></td>

							<td>$ <%=String.format("%.2f", ventaDTO.getPrecioTotal())%></td>
							<td>
								<ul>
									<%
									for (VentaDetalleDTO detalleDTO : ventaDTO.getDetalles()) {
									%>
									<li><%=detalleDTO.getProducto()%> - Cantidad: <%=detalleDTO.getCantidad()%></li>
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
							<td colspan="6">No hay ventas registradas</td>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Modal para Registrar/Editar Venta -->
	<div class="modal fade" id="registrarVentaModal" role="dialog"
		aria-labelledby="registrarVentaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="registrarVentaModalLabel">Registrar
						Nueva Venta</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="formRegistrarVenta">
					<div class="modal-body">
						<div class="row">
							<!-- Columna izquierda: Cliente, Usuario, Fecha de Creación -->
							<div class="col-md-6">
								<div class="form-group">
									<label for="codigo">Código</label> <input type="text"
										class="form-control" id="ventaId" name="ventaId" readonly>
								</div>
								<div class="form-group">
									<label for="clienteId" class="required">Cliente</label> <select
										class="form-control select2" id="clienteId" name="clienteId"
										required>
										<option value="">Seleccionar Cliente</option>
										<%
										List<Cliente> clientes = (List<Cliente>) request.getSession().getAttribute("listaClientes");
										if (clientes != null) {
											for (Cliente cliente : clientes) {
										%>
										<option value="<%=cliente.getIdCliente()%>"><%=cliente.getNombre()%></option>
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
										<option value="<%=usuario.getIdUsuario()%>"><%=usuario.getNombre()%></option>
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
										<option value="<%=metodoPago.getIdMetPago()%>"><%=metodoPago.getNombre()%></option>
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
							<table class="table table-bordered" id="productsTable">
								<thead>
									<tr>
										<th>Producto</th>
										<th>Cantidad</th>
										<th>Precio Unitario</th>
										<th>Total</th>
										<th>Acciones</th>
									</tr>
								</thead>
								<tbody id="productsContainer">
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
							Venta</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@ include file="components/footer.jsp"%>
	<script>
    $(document).ready(function() {

    	$('#dataTableVentas').DataTable({
            "order": [[0, "desc"]]  // Ordena por la primera columna de forma descendente
        });

    	
    	$('.select2').select2({
            width: '100%',
            placeholder: "Seleccionar una opción",
            allowClear: true,
            dropdownParent: $('#registrarVentaModal')
        });
        
        $('.select2').on('select2:open', function(e) {
            const evt = "scroll.select2";
            $(e.target).parents().off(evt); // Desactivar scroll en los elementos padres
            $(window).off(evt); // Desactivar scroll en la ventana principal
        });

        let selectedId = null;
        let preciosUnitarios = []; // Array para almacenar precios unitarios de cada producto

        // Seleccionar fila de la tabla
        $('#dataTableVentas tbody').on('click', 'tr', function() {
            $('#dataTableVentas tbody tr').removeClass('selected');
            $(this).addClass('selected');
            selectedId = $(this).data('id');
        });

        // Doble click para editar la venta
        $('#dataTableVentas tbody').on('dblclick', 'tr', function() {
            $('#btnEditarVenta').trigger('click');
        });

        // Botón para abrir el modal de Registrar Venta
        $('#btnRegistrarVenta').on('click', function() {
            selectedId = null;
            $('#formRegistrarVenta')[0].reset();
            $('.select2').val(null).trigger('change');
            $('#productsContainer').empty();
            $('#registrarVentaModalLabel').text('Registrar Nueva Venta');
            $('#registrarVentaModal').modal('show');
        });

        // Botón para abrir el modal de Editar Venta
        $('#btnEditarVenta').on('click', function() {
            if (selectedId) {
                $.ajax({
                    url: 'editarVenta',
                    type: 'GET',
                    data: { id: selectedId },
                    success: function(data) {
                        llenarFormularioVenta(data);
                        $('#registrarVentaModalLabel').text('Editar Venta');
                        $('#registrarVentaModal').modal('show');
                    },
                    error: function() {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'No se pudo cargar la información de la venta.'
                        });
                    }
                });
            } else {
                Swal.fire({
                    icon: 'warning',
                    title: 'Advertencia',
                    text: 'Por favor, selecciona una venta primero.'
                });
            }
        });

        // Botón para eliminar una venta
        $('#btnEliminarVenta').on('click', function() {
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
                            url: 'eliminarVenta',
                            type: 'POST',
                            data: { id: selectedId },
                            success: function() {
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Eliminado',
                                    text: 'La venta ha sido eliminada.'
                                }).then(() => {
                                    location.reload();
                                });
                            },
                            error: function() {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'Hubo un problema al eliminar la venta.'
                                });
                            }
                        });
                    }
                });
            } else {
                Swal.fire({
                    icon: 'warning',
                    title: 'Advertencia',
                    text: 'Por favor, selecciona una venta primero.'
                });
            }
        });

        // Enviar formulario mediante AJAX para registrar o editar
        $('#formRegistrarVenta').on('submit', function(event) {
            event.preventDefault();
            let url = selectedId ? 'editarVenta' : 'registrarVenta';

            $.ajax({
                url: url,
                type: 'POST',
                data: $(this).serialize(),
                success: function(response) {
                    if (response.status === 'success') {
                        Swal.fire({
                            icon: 'success',
                            title: 'Venta guardada con éxito',
                            text: response.message
                        }).then(() => {
                            $('#registrarVentaModal').modal('hide');
                            location.reload();
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Hubo un problema al guardar la venta.'
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

        // Agregar nueva fila para un producto
        $('#addProductButton').on('click', function() {
            addProductRow("", "", 1, 0, 0); // Añadir nueva fila en blanco
        });

        // Actualizar el total de la venta cuando cambian cantidad o precio unitario
        $('#productsContainer').on('input', '.cantidad, .precio-unitario', function() {
            $(this).data('manual', true); // Marcar que el precio ha sido modificado manualmente
            actualizarTotalVenta();
        });

        // Limpiar modal al cerrarlo
        $('#registrarVentaModal').on('hidden.bs.modal', function() {
            $('#formRegistrarVenta')[0].reset();
            $('#productsContainer').empty();
            $('#precioTotal').text("");
            $('#precioTotalInput').val(0);
            $('.select2').val(null).trigger('change');
        });

        // Función para crear un select con productos y actualizar precio unitario
        function createSelectProduct(productId) {
            var select = $('<select></select>').addClass('productselect form-control select2').attr('name', 'productoId[]').attr('required', true);

            // Generar opciones desde el backend con productos y almacenar precios
            <%List<Producto> productos = (List<Producto>) request.getSession().getAttribute("listaProductos");
if (productos != null) {
	for (int i = 0; i < productos.size(); i++) {
		Producto producto = productos.get(i);%>
                    
                    preciosUnitarios['<%=producto.getIdProducto()%>'] = <%=producto.getPrecioVenta()%>; // Guardar precio usando el ID del producto
                    
                    var option = $('<option></option>').val('<%=producto.getIdProducto()%>').text('<%=producto.getNombre()%>');
                    if (productId == "<%=producto.getIdProducto()%>") {
                        option.attr('selected', true);
                    }
                    select.append(option);
            <%}
}%>

            // Convertir en select2 después de agregar las opciones
            select.select2({
                width: '100%',
                placeholder: "Seleccionar una opción"
            });

            // Al cambiar producto, actualizar el precio unitario si no ha sido modificado manualmente
            select.on('change', function() {
                var selectedProductId = $(this).val();  // Usamos el valor del select para obtener el producto seleccionado
                var precioUnitario = preciosUnitarios[selectedProductId]; // Obtener precio desde el array usando el ID

                var precioUnitarioInput = $(this).closest('tr').find('.precio-unitario');

                // Solo actualizar si el precio no ha sido cambiado manualmente
                if (!precioUnitarioInput.data('manual') && precioUnitario !== undefined) {
                    precioUnitarioInput.val(precioUnitario.toFixed(2)); // Asignar precio
                }
                actualizarTotalVenta();
            });

            return select;
        }

        // Función para añadir una fila con el producto
        function addProductRow(idVentaDetalle, productoId, cantidad, precioUnitario, totalProducto) {
            var row = $('<tr></tr>');

            var productCell = $('<td></td>').append(createSelectProduct(productoId));
            var cantidadInput = createInput("cantidad[]", cantidad, "number", false, ["cantidad"]);
            var precioUnitarioInput = createInput("precioUnitario[]", precioUnitario.toFixed(2), "text", false, ["precio-unitario"]).data('manual', false); // Inicialmente no ha sido modificado manualmente
            var totalProductoInput = createInput("totalProducto[]", totalProducto.toFixed(2), "text", true, ["total-producto"]);
            var eliminarButton = $('<button></button>').addClass("btn btn-danger mt-2").attr("type", "button").text("Eliminar");

            eliminarButton.on('click', function() {
                row.remove();
                actualizarTotalVenta();
            });

            row.append(productCell)
               .append($('<td></td>').append(cantidadInput))
               .append($('<td></td>').append(precioUnitarioInput))
               .append($('<td></td>').append(totalProductoInput))
               .append($('<td></td>').append(eliminarButton));

            $('#productsContainer').append(row);

            // Inicializamos el select2 después de agregar la fila
            $('.productselect').select2({
                width: '100%',
                placeholder: "Seleccionar una opción"
            });
        }

        // Función para actualizar el total de la venta
        function actualizarTotalVenta() {
            let totalVenta = 0;

            $('#productsContainer tr').each(function() {
                const cantidad = parseFloat($(this).find('.cantidad').val()) || 0;
                const precioUnitario = parseFloat($(this).find('.precio-unitario').val()) || 0;
                const totalProducto = cantidad * precioUnitario;

                $(this).find('.total-producto').val(totalProducto.toFixed(2));
                totalVenta += totalProducto;
            });

            $('#precioTotal').text(totalVenta.toFixed(2));
            $('#precioTotalInput').val(totalVenta.toFixed(2));
        }

        // Función para llenar el formulario con los datos de una venta
        function llenarFormularioVenta(data) {
            $('#ventaId').val(data.idVenta).trigger('change');
            $('#clienteId').val(data.idCliente).trigger('change');
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

            $('#productsContainer').empty();

            data.detalles.forEach(function(detalle) {
                addProductRow(detalle.idVentaDetalle, detalle.idProducto, detalle.cantidad, detalle.precioUnitario, detalle.cantidad * detalle.precioUnitario);
            });

            $('#precioTotal').text(data.precioTotal.toFixed(2));
            $('#precioTotalInput').val(data.precioTotal.toFixed(2));
        }

        // Función para crear inputs
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
