<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- Heading -->
<div class="sidebar-heading">Módulos</div>

<!-- INVENTARIO -->
<li class="nav-item"><a class="nav-link collapsed" href="#"
	data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true"
	aria-controls="collapseTwo"> <i class="fas fa-regular fa-box-open"></i>
		<span>Inventario</span>
</a>
	<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
		data-parent="#accordionSidebar">
		<div class="bg-white py-2 collapse-inner rounded">
			<h6 class="collapse-header">Acciones:</h6>
			<form action="productos" method="get">
				<a class="collapse-item" href="productos">Productos</a>
			</form>
			<form action="depositos" method="get">
				<a class="collapse-item" href="depositos">Depositos</a>
			</form>
			<form action="marcas" method="get">
				<a class="collapse-item" href="marcas">Marcas</a>
			</form>
			<form action="categorias" method="get">
				<a class="collapse-item" href="categorias">Categorías</a>
			</form>
		</div>
	</div></li>

<!-- VENTAS -->
<li class="nav-item"><a class="nav-link collapsed" href="#"
	data-toggle="collapse" data-target="#collapseUtilities"
	aria-expanded="true" aria-controls="collapseUtilities"> <i
		class="fas fa-regular fa-handshake"></i> <span>Ventas -
			Ingresos</span>
</a>
	<div id="collapseUtilities" class="collapse"
		aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
		<div class="bg-white py-2 collapse-inner rounded">
			<h6 class="collapse-header">Acciones:</h6>
			<form action="ventas" method="get">
				<a class="collapse-item" href="ventas">Ventas</a>
			</form>
			<a class="collapse-item" href="devoluciones_out.jsp">Devoluciones</a>
		</div>
	</div></li>

<!-- CLIENTES -->
<li class="nav-item"><a class="nav-link collapsed" href="#"
	data-toggle="collapse" data-target="#collapseClientes"
	aria-expanded="true" aria-controls="collapseClientes"> <i
		class="fas fa-user"></i> <span>Clientes</span>
</a>
	<div id="collapseClientes" class="collapse"
		aria-labelledby="headingClientes" data-parent="#accordionSidebar">
		<div class="bg-white py-2 collapse-inner rounded">
			<h6 class="collapse-header">Acciones:</h6>
			<a class="collapse-item" href="clientes_out.jsp">Clientes</a> <a
				class="collapse-item" href="historial_clientes.jsp">Historial</a> <a
				class="collapse-item" href="deudas_cobros.jsp">Deudas y Cobros</a>
		</div>
	</div></li>

<!-- COMPRAS -->
<li class="nav-item"><a class="nav-link collapsed" href="#"
	data-toggle="collapse" data-target="#collapseCompras"
	aria-expanded="true" aria-controls="collapseCompras"> <i
		class="fas fa-regular fa-handshake"></i> <span>Compras -
			Egresos</span>
</a>
	<div id="collapseCompras" class="collapse"
		aria-labelledby="headingCompras" data-parent="#accordionSidebar">
		<div class="bg-white py-2 collapse-inner rounded">
			<h6 class="collapse-header">Acciones:</h6>
			<a class="collapse-item" href="compras_out.jsp">Compras</a> <a
				class="collapse-item" href="seg_envios.jsp">Seguimiento de
				Envíos</a>
		</div>
	</div></li>

<!-- PROVEEDORES -->
<li class="nav-item"><a class="nav-link collapsed" href="#"
	data-toggle="collapse" data-target="#collapseProv" aria-expanded="true"
	aria-controls="collapseProv"> <i class="fas fa-users"></i> <span>Proveedores</span>
</a>
	<div id="collapseProv" class="collapse" aria-labelledby="headingProv"
		data-parent="#accordionSidebar">
		<div class="bg-white py-2 collapse-inner rounded">
			<h6 class="collapse-header">Acciones:</h6>
			<a class="collapse-item" href="proveedores_out.jsp">Proveedores</a> <a
				class="collapse-item" href="historial_proveedores.jsp">Historial</a>
		</div>
	</div></li>

<!-- CONTABILIDAD -->
<li class="nav-item"><a class="nav-link collapsed" href="#"
	data-toggle="collapse" data-target="#collapseAccount"
	aria-expanded="true" aria-controls="collapseAccount"> <i
		class="fas fa-solid fa-dollar-sign"></i> <span>Contabilidad</span>
</a>
	<div id="collapseAccount" class="collapse"
		aria-labelledby="headingAccount" data-parent="#accordionSidebar">
		<div class="bg-white py-2 collapse-inner rounded">
			<h6 class="collapse-header">Acciones:</h6>
			<a class="collapse-item" href="ventasgastos.jsp">Ventas y Gastos</a>
			<a class="collapse-item" href="estadoscontables.jsp">Estados
				Contables</a> <a class="collapse-item" href="impuestos.jsp">Impuestos</a>
			<a class="collapse-item" href="auditoria.jsp">Auditoria</a>
		</div>
	</div></li>

<!-- REPORTES -->
<li class="nav-item"><a class="nav-link collapsed" href="#"
	data-toggle="collapse" data-target="#collapseReports"
	aria-expanded="true" aria-controls="collapseReports"> <i
		class="fas fa-fw fa-chart-bar"></i> <span>Reportes</span>
</a>
	<div id="collapseReports" class="collapse"
		aria-labelledby="headingReports" data-parent="#accordionSidebar">
		<div class="bg-white py-2 collapse-inner rounded">
			<h6 class="collapse-header">Generación de Reportes:</h6>
			<a class="collapse-item" href="reporte_ventas.jsp">Reporte de
				Ventas</a> <a class="collapse-item"
				href="reporte_ranking_vendedores.jsp">Ranking de Vendedores</a> <a
				class="collapse-item" href="reporte_ranking_clientes.jsp">Ranking
				de Clientes</a> <a class="collapse-item"
				href="reporte_ranking_productos.jsp">Ranking de Productos</a> <a
				class="collapse-item" href="reporte_medios_pago.jsp">Medios de
				Pago</a> <a class="collapse-item" href="reporte_estado_inventario.jsp">Estado
				de Inventario</a> <a class="collapse-item"
				href="reporte_cambios_devoluciones.jsp">Cambios y Devoluciones</a> <a
				class="collapse-item" href="reporte_estado_ocupacion_depositos.jsp">Estado
				de Depósitos</a>
		</div>
	</div></li>

<!-- CONFIGURACIÓN -->
<li class="nav-item"><a class="nav-link collapsed" href="#"
	data-toggle="collapse" data-target="#collapseConfig"
	aria-expanded="true" aria-controls="collapseConfig"> <i
		class="fas fa-solid fa-wrench"></i> <span>Configuración</span>
</a>
	<div id="collapseConfig" class="collapse"
		aria-labelledby="headingConfig" data-parent="#accordionSidebar">
		<div class="bg-white py-2 collapse-inner rounded">
			<h6 class="collapse-header">Acciones:</h6>
			<a class="collapse-item" href="usuarios_out.jsp">Usuarios</a> <a
				class="collapse-item" href="roles_out.jsp">Roles</a> <a
				class="collapse-item" href="config_gral.jsp">Configuración
				General</a>
		</div>
	</div></li>

<!-- Divider -->
<hr class="sidebar-divider">
</ul>
<!-- End of Sidebar -->

