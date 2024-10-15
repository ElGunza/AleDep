<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="aledep.dto.VentaDTO"%>
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

    <!-- Tabla de detalles de ventas -->
    <div class="table-responsive mt-4">
        <table class="table table-bordered" id="dataTableVentas">
            <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Total Ventas</th>
                    <th>Valor Ventas</th>
                    <th>Código Ventas</th>
                </tr>
            </thead>
            <tbody id="ventasBody">
                <!-- Los datos de las ventas serán insertados aquí -->
            </tbody>
        </table>
    </div>

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
    </div>
</div>

<script>
    $(document).ready(function() {

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
                    var ventas = response || [];
                    renderTable(ventas);
                    renderCharts(ventas); // Renderizar ambos gráficos
                },
                error: function() {
                    showError('Ocurrió un error al obtener los datos del reporte.');
                }
            });
        }

        // Función para mostrar las ventas en la tabla
        function renderTable(ventas) {
            $('#ventasBody').empty(); // Limpiar tabla

            if (ventas.length === 0) {
                showError('No se encontraron ventas en el rango de fechas seleccionado.');
                return;
            }

            var precioTotalAcumulado = 0; // Para sumar el total acumulado

            $.each(ventas, function(index, venta) {
                var idsHtml = venta.idsVentas.length > 0 ? createIdsHtml(venta.idsVentas) : 'Sin IDs de ventas';

                // Sumar el precio total para el acumulado
                precioTotalAcumulado += venta.precioTotal;

                var fila = '<tr>' +
                    '<td>' + venta.fechaCreacionStr + '</td>' +
                    '<td>' + venta.idsVentas.length + ' ventas</td>' +
                    '<td>$ ' + venta.precioTotal.toFixed(2) + '</td>' + // Precio total por fecha
                    '<td>' + idsHtml + '</td>' +
                    '</tr>';
                $('#ventasBody').append(fila);
            });

            // Agregar una fila para el precio total acumulado
            var totalFila = '<tr>' +
                '<td colspan="2"><strong>Total Acumulado:</strong></td>' +
                '<td><strong>$ ' + precioTotalAcumulado.toFixed(2) + '</strong></td>' +
                '<td></td>' +
                '</tr>';
            $('#ventasBody').append(totalFila);

            // Inicializar DataTable después de llenar la tabla
            //initializeDataTable();
        }

/*         function initializeDataTable() {
            if ($.fn.DataTable.isDataTable('#dataTableVentas')) {
                $('#dataTableVentas').DataTable().clear().destroy(); // Limpiar y destruir DataTable si ya existe
            }

            $('#dataTableVentas').DataTable({
                "paging": true,
                "searching": true,
                "ordering": true,
                "info": true,
                "destroy": true 
            });
        } */

        function createIdsHtml(idsVentas) {
            var idsHtml = '<ul>';
            $.each(idsVentas, function(i, idVenta) {
                idsHtml += '<li>ID Venta: ' + idVenta + '</li>';
            });
            idsHtml += '</ul>';
            return idsHtml;
        }

        // Función para renderizar ambos gráficos
        function renderCharts(ventas) {
            // Destruir y recrear los gráficos
            $('#ventasChart').remove();
            $('.card-body:eq(0)').append('<canvas id="ventasChart" width="400" height="150"></canvas>');

            $('#valorVentasChart').remove();
            $('.card-body:eq(1)').append('<canvas id="valorVentasChart" width="400" height="150"></canvas>');

            if (ventas.length === 0) return;

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
                        y: {
                            beginAtZero: true
                        }
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
                        y: {
                            beginAtZero: true
                        }
                    },
                    responsive: true,
                    maintainAspectRatio: false
                }
            });
        }

        // Función para mostrar un mensaje de error
        function showError(message) {
            Swal.fire({
                icon: 'info',
                title: 'Sin ventas',
                text: message
            });
        }
    });
</script>


<%@ include file="components/footer.jsp"%>
