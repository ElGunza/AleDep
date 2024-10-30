
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- Incluir los componentes comunes -->
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>

<!-- Contenido principal -->
<div class="container-fluid">

	<!-- Formulario para seleccionar el período -->
	<form id="formFiltroDashboard">
		<div class="row" style="display: none;">
			<div class="col-md-3">
				<label for="periodo">Seleccionar Filtro de Tiempo</label> <select
					id="periodo" name="periodo" class="form-control">
					<option value="diario">Diario</option>
					<option value="semanal">Semanal</option>
					<option value="mensual">Mensual</option>
					<option value="anual">Anual</option>
				</select>
			</div>
			<div class="col-md-3 mt-4">
				<button type="submit" class="btn btn-primary">Filtrar</button>
			</div>
		</div>
	</form>

	<!-- Fila 1: Resumen de Ventas -->
	<div class="row mt-4">

		<!-- Gráfico de Ventas -->
		<div class="col-xl-8 col-lg-7">
			<div class="card shadow mb-4">
				<div
					class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">Total de ventas
						por día</h6>
				</div>
				<div class="card-body">
					<div class="chart-area">
						<canvas id="ventasChart" style="height: 400px;"></canvas>
					</div>
				</div>
				<div class="card-footer">
					<a href="reporteVentas" class="btn btn-primary btn-sm">Ver
						Detalle</a>
				</div>
			</div>
		</div>

		<!-- Gráfico de Valor de Ventas -->
		<div class="col-xl-4 col-lg-5">
			<div class="card shadow mb-4">
				<div
					class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">Valor Total de
						Ventas</h6>
				</div>
				<div class="card-body">
					<div class="chart-bar">
						<canvas id="valorVentasChart" style="height: 400px;"></canvas>
					</div>
				</div>
			</div>
		</div>

	</div>

	<!-- Fila 2: Otros reportes -->
	<div class="row">

		<!-- Gráfico de Ranking de Vendedores -->
		<div class="col-xl-6 col-lg-6">
			<div class="card shadow mb-4">
				<div
					class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">Ranking de
						Vendedores</h6>
				</div>
				<div class="card-body">
					<div class="chart-area">
						<canvas id="rankingVendedoresChart" style="height: 400px;"></canvas>
					</div>
				</div>
				<div class="card-footer">
					<a href="rankingVendedores" class="btn btn-primary btn-sm">Ver
						Detalle</a>
				</div>
			</div>
		</div>

		<!-- Gráfico de Inventario -->
		<div class="col-xl-6 col-lg-6">
			<div class="card shadow mb-4">
				<div
					class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					<h6 class="m-0 font-weight-bold text-primary">Productos más
						vendidos</h6>
				</div>
				<div class="card-body">
					<div class="chart-pie">
						<canvas id="inventarioChart" style="height: 400px;"></canvas>
					</div>
				</div>
				<div class="card-footer">
					<a href="reporteProductosVendidos" class="btn btn-primary btn-sm">Ver
						Detalle</a>
				</div>
			</div>
		</div>

	</div>
</div>

<!-- Incluir el pie de página -->
<%@ include file="components/footer.jsp"%>

<!-- Scripts para cargar los gráficos -->
<script>
	$(document)
			.ready(
					function() {
						// Llamar a la función de carga de datos del dashboard automáticamente cuando se carga la página
						fetchDashboardData();

						$('#formFiltroDashboard').on('submit', function(event) {
							event.preventDefault();
							fetchDashboardData();
						});

						function fetchDashboardData() {
							var periodo = $('#periodo').val(); // Obtener el valor del período seleccionado

							$
									.ajax({
										url : 'dashboard',
										method : 'GET',
										data : {
											periodo : periodo
										}, // Enviar el período como parámetro
										dataType : 'json',
										success : function(response) {
											// Renderizar los gráficos con los datos obtenidos
											renderVentasChart(response.listaVentas);
											renderValorVentasChart(response.listaVentas);
											renderRankingVendedoresChart(response.rankingVendedores);
											renderInventarioChart(response.rankingProductosVendidos);
										},
										error : function() {
											alert('Error al obtener los datos del dashboard.');
										}
									});
						}

						// Función para renderizar el gráfico de ventas
						function renderVentasChart(ventas) {
							var ctxVentas = $('#ventasChart')[0]
									.getContext('2d');
							var fechas = ventas.map(function(venta) {
								return venta.fechaCreacionStr;
							});
							var totalesVentas = ventas.map(function(venta) {
								return venta.idsVentas.length;
							});

							new Chart(
									ctxVentas,
									{
										type : 'line',
										data : {
											labels : fechas,
											datasets : [ {
												label : 'Cantidad de Ventas',
												data : totalesVentas,
												backgroundColor : 'rgba(75, 192, 192, 0.2)',
												borderColor : 'rgba(75, 192, 192, 1)',
												borderWidth : 2,
												fill : true
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

						// Función para renderizar el gráfico de valor de ventas
						function renderValorVentasChart(ventas) {
							var ctxValorVentas = $('#valorVentasChart')[0]
									.getContext('2d');
							var fechas = ventas.map(function(venta) {
								return venta.fechaCreacionStr;
							});
							var totalesValor = ventas.map(function(venta) {
								return venta.precioTotal;
							});

							new Chart(
									ctxValorVentas,
									{
										type : 'bar',
										data : {
											labels : fechas,
											datasets : [ {
												label : 'Valor Total de Ventas ($)',
												data : totalesValor,
												backgroundColor : 'rgba(255, 99, 132, 0.2)',
												borderColor : 'rgba(255, 99, 132, 1)',
												borderWidth : 2,
												fill : true
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

						// Función para renderizar el gráfico de ranking de vendedores
						function renderRankingVendedoresChart(rankingVendedores) {
							var ctxRanking = $('#rankingVendedoresChart')[0]
									.getContext('2d');

							// Agrupar las ventas por vendedor
							var vendedoresMap = {};

							rankingVendedores
									.forEach(function(vendedor) {
										if (vendedoresMap[vendedor.nombre]) {
											vendedoresMap[vendedor.nombre].cantidadVentas += vendedor.cantidadVentas;
										} else {
											vendedoresMap[vendedor.nombre] = {
												nombre : vendedor.nombre,
												cantidadVentas : vendedor.cantidadVentas
											};
										}
									});

							// Convertir el objeto agrupado en un array para renderizar el gráfico
							var nombresVendedores = Object.keys(vendedoresMap);
							var totalVentas = Object.values(vendedoresMap).map(
									function(vendedor) {
										return vendedor.cantidadVentas;
									});

							// Destruir gráfico existente si ya fue creado
							if (window.chartRankingVendedores) {
								window.chartRankingVendedores.destroy();
							}

							// Crear el nuevo gráfico de ranking de vendedores
							window.chartRankingVendedores = new Chart(
									ctxRanking,
									{
										type : 'bar',
										data : {
											labels : nombresVendedores,
											datasets : [ {
												label : 'Cantidad de Ventas',
												data : totalVentas,
												backgroundColor : 'rgba(54, 162, 235, 0.2)',
												borderColor : 'rgba(54, 162, 235, 1)',
												borderWidth : 2,
												fill : true
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

						// Función para renderizar el gráfico de inventario
						function renderInventarioChart(productosVendidos) {
							var ctxInventario = $('#inventarioChart')[0]
									.getContext('2d');
							var nombresProductos = productosVendidos
									.map(function(producto) {
										return producto.nombreProducto;
									});
							var cantidadesVendidas = productosVendidos
									.map(function(producto) {
										return producto.cantidadVendida;
									});

							new Chart(ctxInventario, {
								type : 'pie',
								data : {
									labels : nombresProductos,
									datasets : [ {
										label : 'Productos Vendidos',
										data : cantidadesVendidas,
										backgroundColor : [
												'rgba(255, 206, 86, 0.2)',
												'rgba(54, 162, 235, 0.2)',
												'rgba(255, 99, 132, 0.2)',
												'rgba(75, 192, 192, 0.2)' ],
										borderColor : [
												'rgba(255, 206, 86, 1)',
												'rgba(54, 162, 235, 1)',
												'rgba(255, 99, 132, 1)',
												'rgba(75, 192, 192, 1)' ],
										borderWidth : 1
									} ]
								},
								options : {
									responsive : true,
									maintainAspectRatio : false
								}
							});
						}
					});
</script>

