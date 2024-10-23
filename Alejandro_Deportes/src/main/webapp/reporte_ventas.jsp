<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="aledep.dto.VentaDTO"%>

<!-- Incluir componentes comunes -->
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
	<h1>Reporte de Ventas Realizadas</h1>

	<!-- Formulario para filtrar por tiempo -->
	<form id="formFiltroVentas" method="get">
		<input type="hidden" name="action" value="search">
		<div class="row">
			<div class="col-md-3">
				<label for="fechaDesde">Fecha Desde</label>
				<input type="date" id="fechaDesde" name="fechaDesde" class="form-control">
			</div>
			<div class="col-md-3">
				<label for="fechaHasta">Fecha Hasta</label>
				<input type="date" id="fechaHasta" name="fechaHasta" class="form-control">
			</div>
			<div class="col-md-3">
				<label for="periodo">Seleccionar Filtro de Tiempo</label>
				<select id="periodo" name="periodo" class="form-control">
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

	<!-- Gráficos de Ventas -->
	<div class="row mt-5">
		<!-- Primer gráfico: Cantidad de Ventas -->
		<div class="col-md-6">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">Gráfico de Cantidad de Ventas por Periodo</h6>
				</div>
				<div class="card-body">
					<canvas id="ventasChart" width="400" height="150"></canvas>
				</div>
			</div>
		</div>

		<!-- Segundo gráfico: Valor Total de Ventas -->
		<div class="col-md-6">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">Gráfico de Valor Total de Ventas por Periodo</h6>
				</div>
				<div class="card-body">
					<canvas id="valorVentasChart" width="400" height="150"></canvas>
				</div>
			</div>
		</div>

		<!-- Nuevo gráfico: Medios de Pago Utilizados -->
		<div class="col-md-6 mt-4">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">Gráfico de Medios de Pago Utilizados</h6>
				</div>
				<div class="card-body">
					<canvas id="metodosPagoChart" width="400" height="250"></canvas>
				</div>
			</div>
		</div>

		<!-- Tabla de Medios de Pago -->
		<div class="col-md-6 mt-4">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">Tabla de Medios de Pago Utilizados</h6>
				</div>
				<div class="card-body">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Método de Pago</th>
								<th>Cantidad de Usos</th>
							</tr>
						</thead>
						<tbody id="metodosPagoBody">
							<!-- Los datos de los métodos de pago serán insertados aquí -->
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- Tabla de detalles de ventas -->
	<div class="table-responsive mt-4">
		<table class="table table-bordered" id="dataTableVentas">
			<thead>
				<tr>
					<th>Fecha</th>
					<th>Total Ventas</th>
					<th>Valor Ventas</th>
				</tr>
			</thead>
			<tbody id="ventasBody">
				<!-- Los datos de las ventas serán insertados aquí -->
			</tbody>
		</table>
	</div>
</div>

<!-- Scripts para manejo de los gráficos y tablas -->
<script>
	$(document).ready(function() {
		// Configurar fechas predeterminadas
		var today = new Date();
		var todayFormatted = today.toISOString().split('T')[0];
		$('#fechaHasta').val(todayFormatted);
		var lastYear = new Date();
		lastYear.setFullYear(today.getFullYear() - 1);
		var lastYearFormatted = lastYear.toISOString().split('T')[0];
		$('#fechaDesde').val(lastYearFormatted);

		// Manejar el envío del formulario con AJAX
		$('#formFiltroVentas').on('submit', function(event) {
			event.preventDefault();
			fetchReportData();
		});

		// Función para obtener y mostrar los datos del reporte
		function fetchReportData() {
			var periodo = $('#periodo').val();
			var fechaDesde = $('#fechaDesde').val();
			var fechaHasta = $('#fechaHasta').val();

			$.ajax({
				url: 'reporteVentasSearch',
				type: 'GET',
				data: {
					action: 'search',
					periodo: periodo,
					fechaDesde: fechaDesde,
					fechaHasta: fechaHasta
				},
				success: function(response) {
					var ventas = response.ventas || [];
					var metodosPago = response.metodosPago || [];
					renderTable(ventas);
					renderCharts(ventas, metodosPago); // Renderizar gráficos
					renderMetodosPagoTable(metodosPago); // Renderizar tabla de métodos de pago
				},
				error: function() {
					showError('Ocurrió un error al obtener los datos del reporte.');
				}
			});
		}

		// Función para renderizar la tabla de ventas
		function renderTable(ventas) {
			$('#ventasBody').empty(); // Limpiar tabla

			if (ventas.length === 0) {
				showError('No se encontraron ventas en el rango de fechas seleccionado.');
				return;
			}

			var precioTotalAcumulado = 0;

			$.each(ventas, function(index, venta) {
				precioTotalAcumulado += venta.precioTotal;
				var fila = '<tr>'
						+ '<td>' + venta.fechaCreacionStr + '</td>'
						+ '<td>' + venta.idsVentas.length + ' ventas</td>'
						+ '<td>$ ' + venta.precioTotal.toFixed(2) + '</td>'
						+ '</tr>';
				$('#ventasBody').append(fila);
			});

			var totalFila = '<tr>'
					+ '<td colspan="2"><strong>Total Acumulado:</strong></td>'
					+ '<td><strong>$ ' + precioTotalAcumulado.toFixed(2) + '</strong></td>'
					+ '</tr>';
			$('#ventasBody').append(totalFila);
		}

		// Función para renderizar los gráficos
		function renderCharts(ventas, metodosPago) {
			// Gráfico de Cantidad de Ventas
			var fechas = ventas.map(function(venta) {
				return venta.fechaCreacionStr;
			});
			var totalesVentas = ventas.map(function(venta) {
				return venta.idsVentas.length;
			});
			var totalesValor = ventas.map(function(venta) {
				return venta.precioTotal;
			});

			// Primer gráfico: Cantidad de Ventas
			var ctxVentas = $('#ventasChart')[0].getContext('2d');
			new Chart(ctxVentas, {
				type: 'line',
				data: {
					labels: fechas,
					datasets: [{
						label: 'Cantidad de Ventas',
						data: totalesVentas,
						backgroundColor: 'rgba(75, 192, 192, 0.2)',
						borderColor: 'rgba(75, 192, 192, 1)',
						fill: true,
						borderWidth: 2
					}]
				},
				options: {
					scales: {
						y: { beginAtZero: true }
					},
					responsive: true,
					maintainAspectRatio: false
				}
			});

			// Segundo gráfico: Valor Total de Ventas
			var ctxValorVentas = $('#valorVentasChart')[0].getContext('2d');
			new Chart(ctxValorVentas, {
				type: 'bar',
				data: {
					labels: fechas,
					datasets: [{
						label: 'Valor Total de Ventas ($)',
						data: totalesValor,
						backgroundColor: 'rgba(255, 99, 132, 0.2)',
						borderColor: 'rgba(255, 99, 132, 1)',
						borderWidth: 2
					}]
				},
				options: {
					scales: {
						y: { beginAtZero: true }
					},
					responsive: true,
					maintainAspectRatio: false
				}
			});

			// Gráfico de Medios de Pago
			var nombresMetodosPago = metodosPago.map(function(metodo) {
				return metodo.nombre;
			});
			var cantidadUsos = metodosPago.map(function(metodo) {
				return metodo.cantidadUsos;
			});

			var ctxMetodosPago = $('#metodosPagoChart')[0].getContext('2d');
			new Chart(ctxMetodosPago, {
				type: 'pie',
				data: {
					labels: nombresMetodosPago,
					datasets: [{
						data: cantidadUsos,
						backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF']
					}]
				},
				options: {
					responsive: true,
					maintainAspectRatio: false,
					plugins: {
						legend: {
							display: true,
							position: 'top',
						}
					}
				}
			});
		}

		// Función para renderizar la tabla de medios de pago
		function renderMetodosPagoTable(metodosPago) {
			$('#metodosPagoBody').empty(); // Limpiar tabla

			$.each(metodosPago, function(index, metodo) {
				var fila = '<tr>'
						+ '<td>' + metodo.nombre + '</td>'
						+ '<td>' + metodo.cantidadUsos + '</td>'
						+ '</tr>';
				$('#metodosPagoBody').append(fila);
			});
		}

		// Función para mostrar un mensaje de error
		function showError(message) {
			Swal.fire({ icon: 'info', title: 'Sin ventas', text: message });
		}
	});
</script>

<!-- Incluir el pie de página -->
<%@ include file="components/footer.jsp"%>
