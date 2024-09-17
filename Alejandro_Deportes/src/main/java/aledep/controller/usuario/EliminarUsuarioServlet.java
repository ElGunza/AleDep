package aledep.controller.usuario;

import com.google.gson.Gson;
import aledep.service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/eliminarUsuario")
public class EliminarUsuarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int usuarioId = Integer.parseInt(request.getParameter("id"));

            usuarioService.desactivarUsuario(usuarioId);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Usuario eliminado con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar el usuario");
        }
    }
}
