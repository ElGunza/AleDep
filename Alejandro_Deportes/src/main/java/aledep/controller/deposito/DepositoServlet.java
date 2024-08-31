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
    private DepositoService depositoService = new DepositoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener el parámetro de filtro (opcional)
        String filtro = request.getParameter("filtro");

        List<Deposito> depositos;

        if ("todos".equalsIgnoreCase(filtro)) {
            // Si el filtro es "todos", obtener todos los depósitos
            depositos = depositoService.getAllDepositos();
        } else {
            // Por defecto, obtener solo los depósitos activos
            depositos = depositoService.getDepositosActivos();
        }

        // Inicializar la lista de productos para cada depósito (si es necesario)
        for (Deposito deposito : depositos) {
            deposito.getProductos().size(); // Esto inicializa la colección de productos si es lazy-loaded
        }

        // Guardar la lista de depósitos en la sesión
        request.getSession().setAttribute("listaDepositos", depositos);

        // Redirigir al JSP correspondiente
        RequestDispatcher dispatcher = request.getRequestDispatcher("depositos_out.jsp");
        dispatcher.forward(request, response);
    }
}
