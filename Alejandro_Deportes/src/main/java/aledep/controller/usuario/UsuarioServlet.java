package aledep.controller.usuario;

import aledep.model.Usuario;
import aledep.service.UsuarioService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final UsuarioService usuarioService = new UsuarioService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String filtro = request.getParameter("filtro");

			List<Usuario> usuarios;

			if ("todos".equalsIgnoreCase(filtro)) {
				usuarios = usuarioService.getAllUsuarios();
			} else {
				usuarios = usuarioService.getUsuariosActivos();
			}

//			request.setAttribute("listaUsuarios", usuarios);

			guardarUsuariosEnSesion(request.getSession(), usuarios);

			RequestDispatcher dispatcher = request.getRequestDispatcher("usuarios_out.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los usuarios");
		}
	}

	private void guardarUsuariosEnSesion(HttpSession session, List<Usuario> usuarios) {
		session.setAttribute("listaUsuarios", usuarios);
	}
}
