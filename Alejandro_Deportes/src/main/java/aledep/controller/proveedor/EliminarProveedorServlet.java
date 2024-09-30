package aledep.controller.proveedor;

import aledep.service.ProveedorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/eliminarProveedor")
public class EliminarProveedorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProveedorService proveedorService = new ProveedorService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int proveedorId = Integer.parseInt(request.getParameter("id"));
            proveedorService.desactivarProveedor(proveedorId);

            enviarRespuestaExito(response, "Proveedor eliminado con éxito");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de proveedor inválido");
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al eliminar el proveedor.");
        }
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }

    private void enviarRespuestaExito(HttpServletResponse response, String mensaje) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": \"success\", \"message\": \"" + mensaje + "\"}");
    }
}
