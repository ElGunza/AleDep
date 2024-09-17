package aledep.controller.cliente;

import com.google.gson.Gson;
import aledep.dto.ClienteDTO;
import aledep.model.Cliente;
import aledep.service.ClienteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/editarCliente")
public class EditarClienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ClienteService clienteService = new ClienteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int clienteId = Integer.parseInt(request.getParameter("id"));
            Cliente cliente = clienteService.getClienteById(clienteId);

            if (cliente != null) {
                ClienteDTO clienteDTO = clienteService.convertirAClienteDTO(cliente);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(clienteDTO));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cliente no encontrado");
            }
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al obtener el cliente.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Cliente cliente = obtenerClienteDesdeRequest(request);
            clienteService.updateCliente(cliente);

            enviarRespuestaExito(response, "Cliente actualizado con éxito");
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al actualizar el cliente.");
        }
    }

    private Cliente obtenerClienteDesdeRequest(HttpServletRequest request) {
        // Obtener el ID del cliente desde el request
        String idClienteStr = request.getParameter("clienteId");
        Integer idCliente = (idClienteStr != null && !idClienteStr.isEmpty()) ? Integer.parseInt(idClienteStr) : null;
        Cliente cliente = (idCliente != null) ? clienteService.getClienteById(idCliente) : new Cliente();

        cliente.setCuitDni(request.getParameter("cuitDni"));
        cliente.setTipoCuitDni(Integer.parseInt("0"));
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setDeuda(Float.parseFloat(request.getParameter("deuda")));
        cliente.setDireccion(request.getParameter("direccion"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setEmail(request.getParameter("email"));
        cliente.setPreferencias(request.getParameter("preferencias"));
        if (cliente.getIdCliente() == null) {
            cliente.setFechaIngreso(new Date()); 
        }

        cliente.setActivo(Boolean.parseBoolean(request.getParameter("activo")));

        return cliente;
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
