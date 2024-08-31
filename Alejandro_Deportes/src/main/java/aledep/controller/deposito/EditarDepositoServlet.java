package aledep.controller.deposito;

import com.google.gson.Gson;
import aledep.dto.DepositoDTO;
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

@WebServlet("/editarDeposito")
public class EditarDepositoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final DepositoService depositoService = new DepositoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int depositoId = parseInt(request.getParameter("id"), -1);
            if (depositoId > 0) {
                Deposito deposito = depositoService.getDepositoById(depositoId);
                if (deposito != null) {
                    DepositoDTO depositoDTO = new DepositoDTO(deposito);
                    response.setContentType("application/json");
                    response.getWriter().write(new Gson().toJson(depositoDTO));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Depósito no encontrado");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de depósito inválido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener el depósito");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Deposito deposito = obtenerDepositoDesdeRequest(request);
            depositoService.updateDeposito(deposito);

            // Responder con un JSON indicando éxito
            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Depósito actualizado con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el depósito");
        }
    }

    private Deposito obtenerDepositoDesdeRequest(HttpServletRequest request) {
        int idDeposito = parseInt(request.getParameter("DepositoId"), -1);
        Deposito deposito = idDeposito > 0 ? depositoService.getDepositoById(idDeposito) : new Deposito();

        deposito.setNombre(request.getParameter("nombre"));
        deposito.setDescripcion(request.getParameter("descripcion"));
        deposito.setUbicacion(parseInt(request.getParameter("ubicacion"), 0));
        deposito.setCapacidad(parseInt(request.getParameter("capacidad"), 0));
        deposito.setActivo(Boolean.parseBoolean(request.getParameter("activo")));

        return deposito;
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
