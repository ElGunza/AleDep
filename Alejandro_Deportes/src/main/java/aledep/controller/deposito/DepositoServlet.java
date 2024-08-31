package aledep.controller.deposito;

import aledep.model.Deposito;
import aledep.service.DepositoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/depositos")
public class DepositoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final DepositoService depositoService = new DepositoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filtro = request.getParameter("filtro");

            List<Deposito> depositos;

            if ("todos".equalsIgnoreCase(filtro)) {
                depositos = depositoService.getAllDepositos();
            } else {
                depositos = depositoService.getDepositosActivos();
            }

            // Inicializa la lista de productos para cada depósito si es lazy-loaded
            depositos.forEach(deposito -> deposito.getProductos().size());

            // Guardar la lista de depósitos en el request (no en la sesión)
            request.setAttribute("listaDepositos", depositos);

            RequestDispatcher dispatcher = request.getRequestDispatcher("depositos_out.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los depósitos");
        }
    }
}
