package aledep.controller.rol;

import com.google.gson.Gson;
import aledep.model.Rol;
import aledep.model.Permiso;
import aledep.service.RolService;
import aledep.service.PermisoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet("/altaRol")
public class AltaRolServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RolService rolService = new RolService();
    private final PermisoService permisoService = new PermisoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Rol rol = obtenerRolDesdeRequest(request);
            rolService.saveRol(rol);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Rol guardado con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al guardar el rol.");
        }
    }

    private Rol obtenerRolDesdeRequest(HttpServletRequest request) {
        String idRolStr = request.getParameter("rolId");
        Integer idRol = (idRolStr != null && !idRolStr.isEmpty()) ? Integer.parseInt(idRolStr) : null;

        Rol rol = (idRol != null) ? rolService.getRolById(idRol) : new Rol();
        rol.setNombre(request.getParameter("nombre"));
        rol.setDescripcion(request.getParameter("descripcion"));

        // Obtener los permisos seleccionados
        String[] permisosSeleccionados = request.getParameterValues("permisos");
        Set<Permiso> permisos = new HashSet<>();
        if (permisosSeleccionados != null) {
            for (String permisoId : permisosSeleccionados) {
                Permiso permiso = permisoService.getPermisoById(Integer.parseInt(permisoId));
                permisos.add(permiso);
            }
        }
        rol.setPermisos(permisos);

        return rol;
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }
}
