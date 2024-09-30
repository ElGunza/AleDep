package aledep.controller.proveedor;

import aledep.model.Proveedor;
import aledep.service.ProveedorService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/proveedores")
public class ProveedorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProveedorService proveedorService = new ProveedorService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filtro = request.getParameter("filtro");

            List<Proveedor> proveedores;

            if ("todos".equalsIgnoreCase(filtro)) {
                proveedores = proveedorService.getAllProveedores();
            } else {
                proveedores = proveedorService.getProveedoresActivos();
            }

            guardarProveedoresEnSesion(request.getSession(), proveedores);

            RequestDispatcher dispatcher = request.getRequestDispatcher("proveedores_out.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los proveedores");
        }
    }

    private void guardarProveedoresEnSesion(HttpSession session, List<Proveedor> proveedores) {
        session.setAttribute("listaProveedores", proveedores);
    }
}
