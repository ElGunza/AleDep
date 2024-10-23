<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>
<div class="container-fluid">
	<h1 class="h3 mb-4 text-gray-800">Ranking de Clientes por Ventas
		Realizadas</h1>

	<!-- Formulario para seleccionar el período -->
	<form id="formFiltroClientes">
		<div class="row">
			<div class="col-md-3">
				<label for="fechaDesde">Fecha Desde</label> <input type="date"
					id="fechaDesde" name="fechaDesde" class="form-control">
			</div>
			<div class="col-md-3">
				<label for="fechaHasta">Fecha Hasta</label> <input type="date"
					id="fechaHasta" name="fechaHasta" class="form-control">
			</div>
			<div class="col-md-3" style="display: none;">
				<label for="periodo">Seleccionar Filtro de Tiempo</label> <select
					id="periodo" name="periodo" class="form-control">
					<option value="diario">Diario</option>
					<option value="semanal">Semanal</option>
					<option value="mensual">Mensual</option>
					<option value="anual">Anual</option>
				</select>
			</div>
			<div class="col-md-3">
				<button type="submit" class="btn btn-primary mt-4">Filtrar</button>
			</div>
		</div>
	</form>

	<!-- Gráfico de torta para cantidad de ventas por cliente -->
	<div class="row mt-5">
		<div class="col-md-6">
			<h5>Cantidad de Ventas por Cliente</h5>
			<div class="chart-container">
				<canvas id="clientesRankingChart"></canvas>
			</div>
		</div>
		<!-- Gráfico de torta para monto total vendido por cliente -->
		<div class="col-md-6">
			<h5>Monto Total Vendido por Cliente</h5>
			<div class="chart-container">
				<canvas id="clientesMontoTotalChart"></canvas>
			</div>
		</div>
	</div>

	<!-- Tabla con ranking de clientes -->
	<div class="row mt-5">
		<div class="col-md-12">
			<table class="table table-bordered" id="tablaRankingClientes">
				<thead>
					<tr>
						<th>Cliente</th>
						<th>Cantidad de Ventas</th>
						<th>Total Vendido</th>
					</tr>
				</thead>
				<tbody id="cuerpoRankingClientes">
					<!-- Los datos serán llenados aquí con AJAX -->
				</tbody>
			</table>
		</div>
	</div>
</div>

<%@ include file="components/footer.jsp"%>

<style>
.chart-container {
	position: relative;
	height: 400px;
	width: 100%;
}
</style>

<script>
	var chartVentas, chartMontoTotal; // Variables para almacenar los gráficos

	$(document)
			.ready(
					function() {
						// Calcular la fecha de hoy y la fecha de hace 6 meses
						var today = new Date();
						var sixMonthsAgo = new Date();
						sixMonthsAgo.setMonth(today.getMonth() - 6);

						// Convertir las fechas al formato YYYY-MM-DD para los inputs de tipo date
						var todayFormatted = today.toISOString().split('T')[0];
						var sixMonthsAgoFormatted = sixMonthsAgo.toISOString()
								.split('T')[0];

						// Asignar las fechas a los inputs
						$('#fechaHasta').val(todayFormatted);
						$('#fechaDesde').val(sixMonthsAgoFormatted);

						// Manejar el envío del formulario con AJAX
						$('#formFiltroClientes').on('submit', function(event) {
							event.preventDefault();
							fetchRankingClientes();
						});

						function fetchRankingClientes() {
							var periodo = $('#periodo').val();
							var fechaDesde = $('#fechaDesde').val();
							var fechaHasta = $('#fechaHasta').val();

							$
									.ajax({
										url : 'reporteRankingClientesSearch',
										type : 'GET',
										data : {
											periodo : periodo,
											fechaDesde : fechaDesde,
											fechaHasta : fechaHasta
										},
										success : function(response) {
											renderizarTablaRankingClientes(response);
											renderizarGraficoVentasClientes(response);
											renderizarGraficoMontoTotalClientes(response);
										},
										error : function() {
											Swal
													.fire(
															'Error',
															'Error al obtener el ranking de clientes.',
															'error');
										}
									});
						}

						// Función para renderizar el gráfico de cantidad de ventas por cliente
						function renderizarGraficoVentasClientes(datos) {
							var ctx = $('#clientesRankingChart')[0]
									.getContext('2d');

							var nombresClientes = datos.map(function(cliente) {
								return cliente.nombreCliente;
							});
							var cantidadVentas = datos.map(function(cliente) {
								return cliente.cantidadVentas;
							});

							// Destruir gráfico anterior si existe
							if (chartVentas) {
								chartVentas.destroy();
							}

							chartVentas = new Chart(ctx, {
								type : 'pie',
								data : {
									labels : nombresClientes,
									datasets : [ {
										data : cantidadVentas,
										backgroundColor : [ '#FF6384',
												'#36A2EB', '#FFCE56',
												'#4BC0C0', '#9966FF',
												'#FF9F40', '#FF6384' ]
									} ]
								},
								options : {
									responsive : true,
									maintainAspectRatio : false,
									plugins : {
										legend : {
											display : true,
											position : 'top',
										}
									}
								}
							});
						}

						// Función para renderizar el gráfico de monto total vendido por cliente
						function renderizarGraficoMontoTotalClientes(datos) {
							var ctx = $('#clientesMontoTotalChart')[0]
									.getContext('2d');

							var nombresClientes = datos.map(function(cliente) {
								return cliente.nombreCliente;
							});
							var montoTotalVendido = datos
									.map(function(cliente) {
										return cliente.totalVendido;
									});

							// Destruir gráfico anterior si existe
							if (chartMontoTotal) {
								chartMontoTotal.destroy();
							}

							chartMontoTotal = new Chart(ctx, {
								type : 'pie',
								data : {
									labels : nombresClientes,
									datasets : [ {
										data : montoTotalVendido,
										backgroundColor : [ '#4BC0C0',
												'#FF6384', '#FFCE56',
												'#36A2EB', '#9966FF',
												'#FF9F40', '#FF6384' ]
									} ]
								},
								options : {
									responsive : true,
									maintainAspectRatio : false,
									plugins : {
										legend : {
											display : true,
											position : 'top',
										}
									}
								}
							});
						}

						// Función para renderizar la tabla de ranking de clientes
						function renderizarTablaRankingClientes(datos) {
							var cuerpoTabla = $('#cuerpoRankingClientes');
							cuerpoTabla.empty(); // Limpiar la tabla

							datos.forEach(function(cliente) {
								var fila = '<tr>' + '<td>'
										+ cliente.nombreCliente + '</td>'
										+ '<td>' + cliente.cantidadVentas
										+ '</td>' + '<td>$'
										+ cliente.totalVendido.toFixed(2)
										+ '</td>' + '</tr>';
								cuerpoTabla.append(fila);
							});
						}
					});
</script>
