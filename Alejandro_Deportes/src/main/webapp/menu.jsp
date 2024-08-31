<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!--     para copiar todo el index.jsp  -->
<%@ include file="components/header.jsp"%>
<%@ include file="components/titleheader.jsp"%>
<%@ include file="components/modulos_sidebar.jsp"%>
<%@ include file="components/topbar.jsp"%>


<!-- Fila de Contenido -->
<div class="row">

    <!-- Gráfico de Área -->
    <div class="col-xl-8 col-lg-7">
        <div class="card shadow mb-4">
            <!-- Encabezado de la Tarjeta - Desplegable -->
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                <h6 class="m-0 font-weight-bold text-primary">Resumen de Ingresos</h6>
                <div class="dropdown no-arrow">
                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in"
                        aria-labelledby="dropdownMenuLink">
                        <div class="dropdown-header">Opciones:</div>
                        <a class="dropdown-item" href="#">Acción</a>
                        <a class="dropdown-item" href="#">Otra acción</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Algo más aquí</a>
                    </div>
                </div>
            </div>
            <!-- Cuerpo de la Tarjeta -->
            <div class="card-body">
                <div class="chart-area">
                    <canvas id="myAreaChart"></canvas>
                </div>
            </div>
        </div>
    </div>

    <!-- Gráfico de Pastel -->
    <div class="col-xl-4 col-lg-5">
        <div class="card shadow mb-4">
            <!-- Encabezado de la Tarjeta - Desplegable -->
            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                <h6 class="m-0 font-weight-bold text-primary">Fuentes de Ingresos</h6>
                <div class="dropdown no-arrow">
                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in"
                        aria-labelledby="dropdownMenuLink">
                        <div class="dropdown-header">Opciones:</div>
                        <a class="dropdown-item" href="#">Acción</a>
                        <a class="dropdown-item" href="#">Otra acción</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Algo más aquí</a>
                    </div>
                </div>
            </div>
            <!-- Cuerpo de la Tarjeta -->
            <div class="card-body">
                <div class="chart-pie pt-4 pb-2">
                    <canvas id="myPieChart"></canvas>
                </div>
                <div class="mt-4 text-center small">
                    <span class="mr-2">
                        <i class="fas fa-circle text-primary"></i> Ventas Directas
                    </span>
                    <span class="mr-2">
                        <i class="fas fa-circle text-success"></i> Redes Sociales
                    </span>
                    <span class="mr-2">
                        <i class="fas fa-circle text-info"></i> Referencias
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>




<%@ include file="components/footer.jsp"%>



