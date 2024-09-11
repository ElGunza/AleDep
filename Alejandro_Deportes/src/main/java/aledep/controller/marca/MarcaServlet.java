package aledep.controller.marca;

import aledep.model.Marca;
import aledep.service.MarcaService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/marcas")
public class MarcaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final MarcaService marcaService = new MarcaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filtro = request.getParameter("filtro");

            List<Marca> marcas;

            if ("todos".equalsIgnoreCase(filtro)) {
                marcas = marcaService.getAllMarcas();
            } else {
                marcas = marcaService.getMarcasActivas();
            }

            request.setAttribute("listaMarcas", marcas);

            RequestDispatcher dispatcher = request.getRequestDispatcher("marcas_out.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener las marcas");
        }
    }
}
