package aledep.controller.permiso;

import aledep.model.Permiso;
import aledep.service.PermisoService;
import com.google.gson.Gson; // Asegúrate de importar Gson para manejar JSON.

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/permisos")
public class PermisoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PermisoService permisoService = new PermisoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filtro = request.getParameter("filtro");
            String jsonRequest = request.getParameter("json"); // Verificamos si se está solicitando JSON.

            List<Permiso> permisos;

            if ("todos".equalsIgnoreCase(filtro)) {
                permisos = permisoService.getAllPermisos();
            } else {
                permisos = permisoService.getActivosPermisos();
            }

            // Si la solicitud es para JSON (desde AJAX)
            if ("true".equals(jsonRequest)) {
                // Devolvemos los permisos como JSON
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String permisosJson = new Gson().toJson(permisos);
                response.getWriter().write(permisosJson);
            } else {
                // Redirigimos a la página JSP (para solicitudes normales)
                request.setAttribute("listaPermisos", permisos);
                RequestDispatcher dispatcher = request.getRequestDispatcher("permisos_out.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los permisos");
        }
    }
}
