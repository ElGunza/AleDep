package aledep.controller.marca;

import aledep.service.MarcaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/eliminarMarca")
public class EliminarMarcaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final MarcaService marcaService = new MarcaService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int marcaId = Integer.parseInt(request.getParameter("id"));

            marcaService.desactivarMarca(marcaId);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Marca eliminada con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de marca inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar la marca");
        }
    }
}
