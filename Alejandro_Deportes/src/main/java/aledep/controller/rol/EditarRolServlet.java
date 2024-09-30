package aledep.controller.rol;

import com.google.gson.Gson;

import aledep.dto.RolDTO;
import aledep.model.Rol;
import aledep.service.RolService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/editarRol")
public class EditarRolServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final RolService rolService = new RolService();

	
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        try {
	            int rolId = Integer.parseInt(request.getParameter("id"));
	            Rol rol = rolService.getRolById(rolId);

	            if (rol != null) {
	                RolDTO rolDTO = rolService.convertirArolDTO(rol);
	                response.setContentType("application/json");
	                response.getWriter().write(new Gson().toJson(rolDTO));
	            } else {
	                response.sendError(HttpServletResponse.SC_NOT_FOUND, "rol no encontrada");
	            }
	        } catch (Exception e) {
	            manejarExcepcion(response, e, "Error al obtener la rol.");
	        }
	    }
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Rol rol = obtenerRolDesdeRequest(request);
			rolService.updateRol(rol);

			Map<String, String> responseData = new HashMap<>();
			responseData.put("status", "success");
			responseData.put("message", "Rol actualizado con éxito");

			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(responseData));
		} catch (Exception e) {
			manejarExcepcion(response, e, "Error al actualizar el rol.");
		}
	}

	private Rol obtenerRolDesdeRequest(HttpServletRequest request) {
		String idRolStr = request.getParameter("rolId");
		Integer idRol = (idRolStr != null && !idRolStr.isEmpty()) ? Integer.parseInt(idRolStr) : null;

		Rol rol = (idRol != null) ? rolService.getRolById(idRol) : new Rol();
		rol.setNombre(request.getParameter("nombre"));
		rol.setDescripcion(request.getParameter("descripcion"));

		return rol;
	}

	private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
		e.printStackTrace();
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
	}
}
