package aledep.controller.rol;

import com.google.gson.Gson;
import aledep.service.RolService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/eliminarRol")
public class EliminarRolServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RolService rolService = new RolService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int rolId = Integer.parseInt(request.getParameter("id"));
            rolService.desactivarRol(rolId);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Rol eliminado con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de rol inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar el rol");
        }
    }
}
