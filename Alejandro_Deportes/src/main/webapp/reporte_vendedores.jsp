<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="aledep.dto.UsuarioDTO"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<div class="container-fluid">
	<h1>Ranking de Vendedores</h1>

	<!-- Formulario para filtrar por tiempo -->
	<form id="formFiltroRanking" method="get">
		<input type="hidden" name="action" value="search">
		<div class="row">
			<div class="col-md-3">
				<label for="fechaDesde">Fecha Desde</label> <input type="date"
					id="fechaDesde" name="fechaDesde" class="form-control">
			</div>
			<div class="col-md-3">
				<label for="fechaHasta">Fecha Hasta</label> <input type="date"
					id="fechaHasta" name="fechaHasta" class="form-control">
			</div>
			<!-- <div class="col-md-3">
				<label for="periodo">Seleccionar Filtro de Tiempo</label> <select
					id="periodo" name="periodo" class="form-control">
					<option value="diario">Diario</option>
					<option value="semanal">Semanal</option>
					<option value="mensual">Mensual</option>
					<option value="anual">Anual</option>
				</select>
			</div> -->
			<div class="col-md-3">
				<button type="submit" class="btn btn-primary mt-4">Filtrar</button>
			</div>
		</div>
	</form>

	<!-- Gráficos de Ventas -->
	<div class="row mt-5">
		<!-- Primer gráfico: Cantidad de Ventas por Vendedor -->
		<div class="col-md-6">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">Cantidad de
						Ventas por Vendedor</h6>
				</div>
				<div class="card-body">
					<canvas id="cantidadVentasChart" width="400" height="150"></canvas>
				</div>
			</div>
		</div>

		<!-- Segundo gráfico: Valor Total de Ventas por Vendedor -->
		<div class="col-md-6">
			<div class="card shadow mb-4">
				<div class="card-header py-3">
					<h6 class="m-0 font-weight-bold text-primary">Valor Total de
						Ventas por Vendedor</h6>
				</div>
				<div class="card-body">
					<canvas id="valorVentasChart" width="400" height="150"></canvas>
				</div>
			</div>
		</div>
	</div>

	<!-- Tabla de ranking de vendedores -->
	<div class="table-responsive mt-4">
		<table class="table table-bordered" id="dataTableRanking">
			<thead>
				<tr>
					<th>Vendedor</th>
					<th>Total Ventas</th>
					<th>Valor Total</th>
				</tr>
			</thead>
			<tbody id="rankingBody">
				<!-- Los datos de los vendedores serán insertados aquí -->
			</tbody>
		</table>
	</div>
</div>

<script>
	$(document)
			.ready(
					function() {
						var today = new Date();
						var todayFormatted = today.toISOString().split('T')[0];
						$('#fechaHasta').val(todayFormatted);
						var lastYear = new Date();
						lastYear.setFullYear(today.getFullYear() - 1);
						var lastYearFormatted = lastYear.toISOString().split(
								'T')[0];
						$('#fechaDesde').val(lastYearFormatted);

						$('#formFiltroRanking').on('submit', function(event) {
							event.preventDefault();
							fetchRankingData();
						});

						function fetchRankingData() {
							var periodo = $('#periodo').val();
							var fechaDesde = $('#fechaDesde').val();
							var fechaHasta = $('#fechaHasta').val();

							$
									.ajax({
										url : 'rankingVendedoresSearch',
										type : 'GET',
										data : {
											action : 'search',
											periodo : periodo,
											fechaDesde : fechaDesde,
											fechaHasta : fechaHasta
										},
										success : function(response) {
											var vendedores = agruparPorVendedor(response
													|| []);
											renderTable(vendedores);
											renderCharts(vendedores);
										},
										error : function() {
											showError('Ocurrió un error al obtener los datos del ranking.');
										}
									});
						}

						// Función para agrupar ventas por vendedor
						function agruparPorVendedor(vendedores) {
							var vendedoresMap = {};

							vendedores
									.forEach(function(vendedor) {
										if (vendedoresMap[vendedor.nombre]) {
											vendedoresMap[vendedor.nombre].cantidadVentas += vendedor.cantidadVentas;
											vendedoresMap[vendedor.nombre].totalVentas += vendedor.totalVentas;
										} else {
											vendedoresMap[vendedor.nombre] = {
												nombre : vendedor.nombre,
												cantidadVentas : vendedor.cantidadVentas,
												totalVentas : vendedor.totalVentas
											};
										}
									});

							// Convertir el objeto en un array para facilitar el uso en el renderizado
							return Object.values(vendedoresMap);
						}

						function renderTable(vendedores) {
							$('#rankingBody').empty(); // Limpiar tabla

							if (vendedores.length === 0) {
								showError('No se encontraron datos en el rango de fechas seleccionado.');
								return;
							}

							$.each(vendedores, function(index, vendedor) {
								var fila = '<tr>' + '<td>' + vendedor.nombre
										+ '</td>' + '<td>'
										+ vendedor.cantidadVentas + '</td>'
										+ '<td>$ '
										+ vendedor.totalVentas.toFixed(2)
										+ '</td>' + '</tr>';
								$('#rankingBody').append(fila);
							});
						}

						function renderCharts(vendedores) {
							$('#cantidadVentasChart').remove();
							$('.card-body:eq(0)')
									.append(
											'<canvas id="cantidadVentasChart" width="400" height="150"></canvas>');

							$('#valorVentasChart').remove();
							$('.card-body:eq(1)')
									.append(
											'<canvas id="valorVentasChart" width="400" height="150"></canvas>');

							if (vendedores.length === 0)
								return;

							var nombres = vendedores.map(function(vendedor) {
								return vendedor.nombre;
							});
							var totalesVentas = vendedores.map(function(
									vendedor) {
								return vendedor.cantidadVentas;
							});
							var totalesValor = vendedores
									.map(function(vendedor) {
										return vendedor.totalVentas;
									});

							// Primer gráfico: Cantidad de Ventas por Vendedor
							var ctxCantidadVentas = $('#cantidadVentasChart')[0]
									.getContext('2d');
							new Chart(
									ctxCantidadVentas,
									{
										type : 'bar',
										data : {
											labels : nombres,
											datasets : [ {
												label : 'Cantidad de Ventas',
												data : totalesVentas,
												backgroundColor : 'rgba(75, 192, 192, 0.2)',
												borderColor : 'rgba(75, 192, 192, 1)',
												borderWidth : 2
											} ]
										},
										options : {
											scales : {
												y : {
													beginAtZero : true
												}
											},
											responsive : true,
											maintainAspectRatio : false
										}
									});

							// Segundo gráfico: Valor Total de Ventas por Vendedor
							var ctxValorVentas = $('#valorVentasChart')[0]
									.getContext('2d');
							new Chart(
									ctxValorVentas,
									{
										type : 'bar',
										data : {
											labels : nombres,
											datasets : [ {
												label : 'Valor Total de Ventas ($)',
												data : totalesValor,
												backgroundColor : 'rgba(255, 99, 132, 0.2)',
												borderColor : 'rgba(255, 99, 132, 1)',
												borderWidth : 2
											} ]
										},
										options : {
											scales : {
												y : {
													beginAtZero : true
												}
											},
											responsive : true,
											maintainAspectRatio : false
										}
									});
						}

						function showError(message) {
							Swal.fire({
								icon : 'info',
								title : 'Sin datos',
								text : message
							});
						}
					});
</script>

<%@ include file="components/footer.jsp"%>
