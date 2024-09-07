package aledep.controller.usuario;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import aledep.dto.UsuarioDTO;
import aledep.model.Usuario;
import aledep.service.RolService;
import aledep.service.UsuarioService;

/**
 * Servlet implementation class AltaUsuarioServlet
 */
@WebServlet("/altaUsuario")
public class AltaUsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final UsuarioService usuarioService = new UsuarioService();
	private final RolService rolService = new RolService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			cargarListasEnSesion(request.getSession());

			// Devolver datos vacíos para nuevo usuario
			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(new UsuarioDTO()));
		} catch (Exception e) {
			manejarExcepcion(response, e, "Error al obtener el usuario.");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Usuario usuario = obtenerUsuarioDesdeRequest(request);
			usuarioService.saveUsuario(usuario);

			// Responder con un JSON indicando éxito
			Map<String, String> responseData = new HashMap<>();
			responseData.put("status", "success");
			responseData.put("message", "Usuario guardado con éxito");

			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(responseData));
		} catch (Exception e) {
			manejarExcepcion(response, e, "Error al guardar el usuario.");
		}
	}

	private void cargarListasEnSesion(HttpSession session) {
		session.setAttribute("listaRoles", rolService.getAllRoles());
	}

	private Usuario obtenerUsuarioDesdeRequest(HttpServletRequest request) {
		Usuario usuario = new Usuario();
		usuario.setNombre(request.getParameter("nombre"));
		usuario.setEmail(request.getParameter("email"));
		usuario.setPassword(request.getParameter("password"));
		usuario.setActivo(Boolean.parseBoolean(request.getParameter("activo")));
		usuario.setRol(rolService.getRolById(parseInt(request.getParameter("idRol"), 0)));
		return usuario;
	}

	private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
		e.printStackTrace();
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
	}

	private int parseInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
