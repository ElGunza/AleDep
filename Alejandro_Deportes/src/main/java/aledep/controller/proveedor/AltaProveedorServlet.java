package aledep.controller.proveedor;

import aledep.model.Proveedor;
import aledep.service.ProveedorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/altaProveedor")
public class AltaProveedorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProveedorService proveedorService = new ProveedorService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Proveedor proveedor = obtenerProveedorDesdeRequest(request);
            proveedorService.saveProveedor(proveedor);

            enviarRespuestaExito(response, "Proveedor guardado con éxito");
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al guardar el proveedor.");
        }
    }

    private Proveedor obtenerProveedorDesdeRequest(HttpServletRequest request) {
        Proveedor proveedor = new Proveedor();
        proveedor.setCuitDni(request.getParameter("cuitDni"));
        proveedor.setNombre(request.getParameter("nombre"));
        proveedor.setContacto(request.getParameter("contacto"));
        proveedor.setTipoCuitDni(request.getParameter("tipoCuitDni"));
        proveedor.setActivo(Boolean.parseBoolean(request.getParameter("activo")));
        return proveedor;
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
