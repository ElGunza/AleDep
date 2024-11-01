package aledep.controller.deposito;

import com.google.gson.Gson;
import aledep.model.Deposito;
import aledep.service.DepositoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/altaDeposito")
public class AltaDepositoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final DepositoService depositoService = new DepositoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Deposito deposito = obtenerDepositoDesdeRequest(request);

            depositoService.saveDeposito(deposito);

            // Responder con un JSON indicando éxito
            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Depósito guardado con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al guardar el depósito.");
        }
    }

    private Deposito obtenerDepositoDesdeRequest(HttpServletRequest request) {
        Deposito deposito = new Deposito();
        deposito.setNombre(request.getParameter("nombre"));
        deposito.setDescripcion(request.getParameter("descripcion"));
        deposito.setUbicacion(parseInt(request.getParameter("ubicacion"),0));
        deposito.setCapacidad(parseInt(request.getParameter("capacidad"), 0));
        deposito.setActivo(Boolean.parseBoolean(request.getParameter("activo")));
        return deposito;
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
