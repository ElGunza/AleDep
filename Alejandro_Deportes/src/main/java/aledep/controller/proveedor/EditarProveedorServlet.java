package aledep.controller.proveedor;

import aledep.dto.ProveedorDTO;
import aledep.model.Proveedor;
import aledep.service.ProveedorService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editarProveedor")
public class EditarProveedorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProveedorService proveedorService = new ProveedorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int proveedorId = Integer.parseInt(request.getParameter("id"));
            Proveedor proveedor = proveedorService.getProveedorById(proveedorId);

            if (proveedor != null) {
                ProveedorDTO proveedorDTO = proveedorService.convertirAProveedorDTO(proveedor);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(proveedorDTO));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Proveedor no encontrado");
            }
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al obtener el proveedor.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Proveedor proveedor = obtenerProveedorDesdeRequest(request);
            proveedorService.updateProveedor(proveedor);

            enviarRespuestaExito(response, "Proveedor actualizado con éxito");
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al actualizar el proveedor.");
        }
    }

    private Proveedor obtenerProveedorDesdeRequest(HttpServletRequest request) {
        String idProveedorStr = request.getParameter("idProveedor");
        Proveedor proveedor;

        if (idProveedorStr != null && !idProveedorStr.isEmpty()) {
            Integer idProveedor = Integer.parseInt(idProveedorStr);
            proveedor = proveedorService.getProveedorById(idProveedor); 
        } else {
            proveedor = new Proveedor(); 
        }

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
