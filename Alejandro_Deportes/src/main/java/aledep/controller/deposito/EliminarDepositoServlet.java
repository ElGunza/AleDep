package aledep.controller.deposito;

import aledep.service.DepositoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

@WebServlet("/eliminarDeposito")
public class EliminarDepositoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final DepositoService depositoService = new DepositoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int depositoId = Integer.parseInt(request.getParameter("id"));

        // Llama al método de servicio para eliminar el depósito
        depositoService.deleteDeposito(depositoId);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("message", "Depósito eliminado con éxito");

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(responseData));
    }
}
