<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="components/header.jsp" %>
<%@ include file="components/titleheader.jsp" %>
<%@ include file="components/modulos_sidebar.jsp" %>
<%@ include file="components/topbar.jsp" %>

<div class="container-fluid">
    <h1 class="h3 mb-2 text-gray-800">Configuración de Empresa</h1>
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Datos de la Empresa</h6>
        </div>
        <div class="card-body">
            <form action="configuracionEmpresa" method="post">
                <div class="form-group">
                    <label for="nombre">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" value="${empresa.nombre}">
                </div>
                <div class="form-group">
                    <label for="razonSocial">Razón Social</label>
                    <input type="text" class="form-control" id="razonSocial" name="razonSocial" value="${empresa.razonSocial}">
                </div>
                <div class="form-group">
                    <label for="cuit">CUIT</label>
                    <input type="text" class="form-control" id="cuit" name="cuit" value="${empresa.cuit}">
                </div>
                <div class="form-group">
                    <label for="direccion">Dirección</label>
                    <input type="text" class="form-control" id="direccion" name="direccion" value="${empresa.direccion}">
                </div>
                <div class="form-group">
                    <label for="telefono">Teléfono</label>
                    <input type="text" class="form-control" id="telefono" name="telefono" value="${empresa.telefono}">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="${empresa.email}">
                </div>
                <div class="form-group">
                    <label for="logoPath">Logo Path</label>
                    <input type="text" class="form-control" id="logoPath" name="logoPath" value="${empresa.logoPath}">
                </div>
                <div class="form-group">
                    <label for="monedaLegal">Moneda Legal</label>
                    <input type="text" class="form-control" id="monedaLegal" name="monedaLegal" value="${empresa.monedaLegal}">
                </div>
                <div class="form-group">
                    <label for="alertaMinimoStock">Alerta Mínimo Stock</label>
                    <input type="number" class="form-control" id="alertaMinimoStock" name="alertaMinimoStock" value="${empresa.alertaMinimoStock}">
                </div>
                <div class="form-group">
                    <label for="accessHistory">Historial de Acceso</label>
                    <select class="form-control" id="accessHistory" name="accessHistory">
                        <option value="true" ${empresa.accessHistory ? 'selected' : ''}>Sí</option>
                        <option value="false" ${!empresa.accessHistory ? 'selected' : ''}>No</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="modificationHistory">Historial de Modificación</label>
                    <select class="form-control" id="modificationHistory" name="modificationHistory">
                        <option value="true" ${empresa.modificationHistory ? 'selected' : ''}>Sí</option>
                        <option value="false" ${!empresa.modificationHistory ? 'selected' : ''}>No</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="deletionHistory">Historial de Eliminación</label>
                    <select class="form-control" id="deletionHistory" name="deletionHistory">
                        <option value="true" ${empresa.deletionHistory ? 'selected' : ''}>Sí</option>
                        <option value="false" ${!empresa.deletionHistory ? 'selected' : ''}>No</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Guardar Configuración</button>
            </form>
        </div>
    </div>
</div>

<script>
    const urlParams = new URLSearchParams(window.location.search);
    const success = urlParams.get('success');

    if (success === 'true') {
        Swal.fire({
            icon: 'success',
            title: 'Configuración guardada',
            text: 'La configuración de la empresa se ha guardado con éxito.',
            confirmButtonText: 'OK'
        });
    }
</script>

<%@ include file="components/footer.jsp" %>
