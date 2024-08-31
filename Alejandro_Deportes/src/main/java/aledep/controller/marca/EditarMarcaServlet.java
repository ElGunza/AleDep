package aledep.controller.marca;

import com.google.gson.Gson;
import aledep.dto.MarcaDTO;
import aledep.model.Marca;
import aledep.service.MarcaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/editarMarca")
public class EditarMarcaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final MarcaService marcaService = new MarcaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int marcaId = Integer.parseInt(request.getParameter("id"));
            Marca marca = marcaService.getMarcaById(marcaId);

            if (marca != null) {
                MarcaDTO marcaDTO = marcaService.convertirAMarcaDTO(marca);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(marcaDTO));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Marca no encontrada");
            }
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al obtener la marca.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Marca marca = obtenerMarcaDesdeRequest(request);
            marcaService.updateMarca(marca);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Marca actualizada con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al actualizar la marca.");
        }
    }

    private Marca obtenerMarcaDesdeRequest(HttpServletRequest request) {
        String idMarcaStr = request.getParameter("marcaId");
        Integer idMarca = (idMarcaStr != null && !idMarcaStr.isEmpty()) ? Integer.parseInt(idMarcaStr) : null;

        Marca marca = (idMarca != null) ? marcaService.getMarcaById(idMarca) : new Marca();

        marca.setNombre(request.getParameter("nombre"));
        marca.setDescripcion(request.getParameter("descripcion"));
        marca.setActivo(Boolean.parseBoolean(request.getParameter("activo")));

        return marca;
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }
}
