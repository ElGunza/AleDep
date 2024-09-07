(function() {
    // Función reutilizable para configurar DataTable y modales
    function setupDataTableAndModal(options) {
        const {
            dataTableId,
            btnAltaId,
            btnEditarId,
            btnEliminarId,
            formId,
            modalId,
            modalTitleId,
            altaUrl,
            editarUrl,
            eliminarUrl,
            entityName,
            limpiarFormulario,
            llenarFormulario
        } = options;

        document.addEventListener("DOMContentLoaded", function () {
            if (!$.fn.DataTable.isDataTable(dataTableId)) {
                $(dataTableId).DataTable({
                    "pageLength": 10,
                    "lengthChange": false,
                    "language": {
                        "search": "Buscar:",
                        "paginate": {
                            "next": "Siguiente",
                            "previous": "Anterior"
                        },
                        "info": `Mostrando _START_ a _END_ de _TOTAL_ registros`,
                        "infoEmpty": `No hay registros disponibles`,
                        "emptyTable": `No hay ${entityName} disponibles`
                    }
                });
            }

            let selectedId = null;

            $(document).ready(function () {
                let table = $(dataTableId).DataTable();

                // Seleccionar fila
                $(`${dataTableId} tbody`).on('click', 'tr', function () {
                    $(`${dataTableId} tbody tr`).removeClass('selected');
                    $(this).addClass('selected');
                    selectedId = $(this).data('id');
                });

                // Desmarcar selección al cambiar la página
                table.on('draw', function () {
                    $(`${dataTableId} tbody tr`).removeClass('selected');
                    selectedId = null;
                });

                // Botón para abrir el modal de Alta
                $(btnAltaId).on('click', function () {
                    limpiarFormulario();
                    $(modalTitleId).text(`Nueva ${entityName}`);
                    $(modalId).modal('show');
                });

                // Manejar el envío del formulario del modal (alta o edición)
                $(formId).on('submit', function (event) {
                    event.preventDefault();

                    let url = selectedId ? editarUrl : altaUrl;

                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: $(this).serialize(),
                        success: function (response) {
                            if (response.status === 'success') {
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Registro guardado con exito',
                                    text: response.message
                                }).then(() => {
                                    $(modalId).modal('hide');
                                    location.reload();
                                });
                            } else {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: `Hubo un problema al guardar ${entityName.toLowerCase()}.`
                                });
                            }
                        },
                        error: function () {
                            Swal.fire({
                                icon: 'error',
                                title: 'Error',
                                text: `Hubo un problema al procesar la solicitud.`
                            });
                        }
                    });
                });

                // Botón para abrir el modal de Edición
                $(btnEditarId).on('click', function () {
                    if (selectedId) {
                        $.ajax({
                            url: editarUrl,
                            type: 'GET',
                            data: { id: selectedId },
                            success: function (data) {
                                llenarFormulario(data);
                                $(modalTitleId).text(`Editar ${entityName}`);
                                $(modalId).modal('show');
                            },
                            error: function () {
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: `No se pudo cargar la información de ${entityName.toLowerCase()}.`
                                });
                            }
                        });
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Advertencia',
                            text: `Por favor, selecciona ${entityName.toLowerCase()} primero.`
                        });
                    }
                });

                // Evento de doble clic para editar
                $(`${dataTableId} tbody`).on('dblclick', 'tr', function () {
                    $(btnEditarId).trigger('click');
                });

                // Botón para eliminar
                $(btnEliminarId).on('click', function () {
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
                                    url: eliminarUrl,
                                    type: 'POST',
                                    data: { id: selectedId },
                                    success: function (response) {
                                        if (response.status === 'success') {
                                            Swal.fire({
                                                icon: 'success',
                                                title: 'Eliminado',
                                                text: response.message
                                            }).then(() => {
                                                location.reload();
                                            });
                                        } else {
                                            Swal.fire({
                                                icon: 'error',
                                                title: 'Error',
                                                text: response.message
                                            });
                                        }
                                    },
                                    error: function () {
                                        Swal.fire({
                                            icon: 'error',
                                            title: 'Error',
                                            text: `Hubo un problema al procesar la solicitud.`
                                        });
                                    }
                                });
                            }
                        });
                    } else {
                        Swal.fire({
                            icon: 'warning',
                            title: 'Advertencia',
                            text: `Por favor, selecciona ${entityName.toLowerCase()} primero.`
                        });
                    }
                });

            });
        });
    }

	//AGREGAR OTRA FUNCION ACA ABAJO 



    // Exportar la función como un plugin de jQuery
    // (Despues de agregar una funcion, declarla aca bajo asi se puede utilizar)
    window.setupDataTableAndModal = setupDataTableAndModal;

})();
