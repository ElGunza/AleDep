<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>
<div class="container-fluid">
    <h1 class="h3 mb-4 text-gray-800">Reporte de Productos Vendidos</h1>

    <!-- Formulario para seleccionar el período -->
    <form id="formFiltroVentas">
        <div class="row">
            <div class="col-md-3">
                <label for="fechaDesde">Fecha Desde</label>
                <input type="date" id="fechaDesde" name="fechaDesde" class="form-control">
            </div>
            <div class="col-md-3">
                <label for="fechaHasta">Fecha Hasta</label>
                <input type="date" id="fechaHasta" name="fechaHasta" class="form-control">
            </div>
            <div class="col-md-3" style="display: none;">
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

    <!-- Gráfico de Productos Vendidos -->
    <div class="row mt-5">
        <div class="col-md-12">
            <canvas id="productosVendidosChart"></canvas>
        </div>
    </div>

    <!-- Tabla con productos vendidos -->
    <div class="row mt-4">
        <div class="col-md-12">
            <table class="table table-bordered" id="tablaProductosVendidos">
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad Vendida</th>
                        <th>Precio Lista</th>
                        <th>Stock Actual</th>
                    </tr>
                </thead>
                <tbody id="cuerpoProductosVendidos">
                    <!-- Los datos serán llenados aquí con AJAX -->
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="components/footer.jsp" %>

<script>
    var chartProductosVendidos; // Variable global para guardar la instancia del gráfico

    $(document).ready(function() {
        // Inicializar fechas automáticamente (6 meses hacia atrás)
        var today = new Date();
        var sixMonthsAgo = new Date();
        sixMonthsAgo.setMonth(today.getMonth() - 6);

        var todayFormatted = today.toISOString().split('T')[0];
        var sixMonthsAgoFormatted = sixMonthsAgo.toISOString().split('T')[0];

        $('#fechaHasta').val(todayFormatted);
        $('#fechaDesde').val(sixMonthsAgoFormatted);

        // Manejar el envío del formulario con AJAX
        $('#formFiltroVentas').on('submit', function(event) {
            event.preventDefault();
            fetchProductosVendidos();
        });

        function fetchProductosVendidos() {
            var periodo = $('#periodo').val();
            var fechaDesde = $('#fechaDesde').val();
            var fechaHasta = $('#fechaHasta').val();

            $.ajax({
                url: 'reporteProductosVendidosSearch',
                type: 'GET',
                data: {
                    periodo: periodo,
                    fechaDesde: fechaDesde,
                    fechaHasta: fechaHasta
                },
                success: function(response) {
                    renderizarGraficoProductosVendidos(response);
                    renderizarTablaProductosVendidos(response);
                },
                error: function() {
                    alert('Error al obtener los productos vendidos.');
                }
            });
        }

        function renderizarGraficoProductosVendidos(datos) {
            var ctx = $('#productosVendidosChart')[0].getContext('2d');
            var nombresProductos = datos.map(function(producto) {
                return producto.nombreProducto;
            });
            var cantidadesVendidas = datos.map(function(producto) {
                return producto.cantidadVendida;
            });

            // Destruir el gráfico anterior si existe
            if (chartProductosVendidos) {
                chartProductosVendidos.destroy();
            }

            // Crear nuevo gráfico
            chartProductosVendidos = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: nombresProductos,
                    datasets: [{
                        label: 'Cantidad Vendida',
                        data: cantidadesVendidas,
                        backgroundColor: '#4e73df'
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }

        function renderizarTablaProductosVendidos(datos) {
            var cuerpoTabla = $('#cuerpoProductosVendidos');
            cuerpoTabla.empty(); // Limpiar la tabla

            datos.forEach(function(producto) {
                var fila = '<tr>' +
                    '<td>' + producto.nombreProducto + '</td>' +
                    '<td>' + producto.cantidadVendida + '</td>' +
                    '<td>' + producto.precioVenta + '</td>' +
                    '<td>' + producto.stockActual + '</td>' +
                    '</tr>';
                cuerpoTabla.append(fila);
            });
        }
    });
</script>
