package aledep.controller.categoria;

import com.google.gson.Gson;
import aledep.dto.CategoriaDTO;
import aledep.model.Categoria;
import aledep.service.CategoriaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/altaCategoria")
public class AltaCategoriaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CategoriaService categoriaService = new CategoriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int categoriaId = Integer.parseInt(request.getParameter("id"));
            Categoria categoria = categoriaService.getCategoriaById(categoriaId);

            if (categoria != null) {
                CategoriaDTO categoriaDTO = categoriaService.convertirACategoriaDTO(categoria);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(categoriaDTO));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Categoría no encontrada");
            }
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al obtener la categoría.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Categoria categoria = obtenerCategoriaDesdeRequest(request);
            categoriaService.saveCategoria(categoria);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Categoría guardada con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al guardar la categoría.");
        }
    }

    private Categoria obtenerCategoriaDesdeRequest(HttpServletRequest request) {
        String idCategoriaStr = request.getParameter("categoriaId");
        Integer idCategoria = (idCategoriaStr != null && !idCategoriaStr.isEmpty()) ? Integer.parseInt(idCategoriaStr) : null;

        Categoria categoria = (idCategoria != null) ? categoriaService.getCategoriaById(idCategoria) : new Categoria();

        categoria.setNombre(request.getParameter("nombre"));
        categoria.setActivo(Boolean.parseBoolean(request.getParameter("activo")));

        return categoria;
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }
}
