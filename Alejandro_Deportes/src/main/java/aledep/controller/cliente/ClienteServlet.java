package aledep.controller.cliente;

import aledep.model.Cliente;
import aledep.service.ClienteService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ClienteService clienteService = new ClienteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filtro = request.getParameter("filtro");

            List<Cliente> clientes;

            if ("todos".equalsIgnoreCase(filtro)) {
                clientes = clienteService.getAllClientes();
            } else {
                clientes = clienteService.getClientesActivos();
            }
            
			guardarClientesEnSesion(request.getSession(), clientes);            

            RequestDispatcher dispatcher = request.getRequestDispatcher("clientes_out.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener los clientes");
        }
    }

    private void guardarClientesEnSesion(HttpSession session, List<Cliente> clientes) {
    	session.setAttribute("listaClientes", clientes);
    }

}


