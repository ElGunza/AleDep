package aledep.controller.cliente;

import com.google.gson.Gson;
import aledep.service.ClienteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/eliminarCliente")
public class EliminarClienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ClienteService clienteService = new ClienteService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int clienteId = Integer.parseInt(request.getParameter("id"));
            clienteService.desactivarCliente(clienteId);

            enviarRespuestaExito(response, "Cliente eliminado con éxito");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de cliente inválido");
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al eliminar el cliente.");
        }
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }

    private void enviarRespuestaExito(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("message", mensaje);

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(responseData));
    }
}
