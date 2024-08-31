package aledep.controller.producto;

import aledep.service.ProductoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

@WebServlet("/eliminarProducto")
public class EliminarProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ProductoService productoService = new ProductoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int productoId = parseInt(request.getParameter("id"), -1);

            if (productoId > 0) {
                productoService.desactivarProducto(productoId);

                enviarRespuestaExitosa(response, "Producto eliminado con éxito");
            } else {
                enviarRespuestaError(response, "ID de producto inválido");
            }
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al eliminar el producto.");
        }
    }

    /**
     * Maneja las excepciones y responde con un mensaje de error JSON.
     *
     * @param response El objeto HttpServletResponse.
     * @param e        La excepción capturada.
     * @param mensaje  El mensaje de error a enviar al cliente.
     * @throws IOException Si ocurre un error al escribir la respuesta.
     */
    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        enviarRespuestaError(response, mensaje);
    }

    /**
     * Envía una respuesta de éxito en formato JSON.
     *
     * @param response El objeto HttpServletResponse.
     * @param mensaje  El mensaje de éxito.
     * @throws IOException Si ocurre un error al escribir la respuesta.
     */
    private void enviarRespuestaExitosa(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("message", mensaje);

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(responseData));
    }

    /**
     * Envía una respuesta de error en formato JSON.
     *
     * @param response El objeto HttpServletResponse.
     * @param mensaje  El mensaje de error.
     * @throws IOException Si ocurre un error al escribir la respuesta.
     */
    private void enviarRespuestaError(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "error");
        responseData.put("message", mensaje);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(new Gson().toJson(responseData));
    }

    /**
     * Parsea un String a un int, devolviendo un valor predeterminado en caso de error.
     *
     * @param value        El String a parsear.
     * @param defaultValue El valor predeterminado a devolver en caso de error.
     * @return El int parseado o el valor predeterminado.
     */
    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
