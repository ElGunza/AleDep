package aledep.controller.categoria;

import aledep.service.CategoriaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/eliminarCategoria")
public class EliminarCategoriaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CategoriaService categoriaService = new CategoriaService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int categoriaId = Integer.parseInt(request.getParameter("id"));

            // Llama al método de servicio para desactivar la categoría
            categoriaService.desactivarCategoria(categoriaId);

            // Responder con un JSON indicando éxito
            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Categoría eliminada con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de categoría inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar la categoría");
        }
    }
}
