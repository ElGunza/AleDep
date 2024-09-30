package aledep.controller.rol;

import aledep.model.Rol;
import aledep.service.RolService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/roles")
public class RolServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final RolService rolService = new RolService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filtro = request.getParameter("filtro");

            List<Rol> roles;

            if ("todos".equalsIgnoreCase(filtro)) {
                roles = rolService.getAllRoles();
            } else {
                roles = rolService.getActivosRoles();
            }

            request.setAttribute("listaRoles", roles);

            RequestDispatcher dispatcher = request.getRequestDispatcher("rol_out.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los roles");
        }
    }
}
