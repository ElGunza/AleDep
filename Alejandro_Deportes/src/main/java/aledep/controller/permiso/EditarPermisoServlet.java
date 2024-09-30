package aledep.controller.permiso;

import com.google.gson.Gson;
import aledep.model.Permiso;
import aledep.service.PermisoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/editarPermiso")
public class EditarPermisoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PermisoService permisoService = new PermisoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Permiso permiso = obtenerPermisoDesdeRequest(request);
            permisoService.updatePermiso(permiso);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Permiso actualizado con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al actualizar el permiso.");
        }
    }

    private Permiso obtenerPermisoDesdeRequest(HttpServletRequest request) {
        String idPermisoStr = request.getParameter("permisoId");
        Integer idPermiso = (idPermisoStr != null && !idPermisoStr.isEmpty()) ? Integer.parseInt(idPermisoStr) : null;

        Permiso permiso = (idPermiso != null) ? permisoService.getPermisoById(idPermiso) : new Permiso();
        permiso.setNombre(request.getParameter("nombre"));
        permiso.setDescripcion(request.getParameter("descripcion"));
        permiso.setActivo(Boolean.parseBoolean(request.getParameter("activo")));

        return permiso;
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }
}
