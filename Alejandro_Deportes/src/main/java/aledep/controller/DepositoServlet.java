package aledep.controller;

import aledep.model.Deposito;
import aledep.service.DepositoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/depositos")
public class DepositoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DepositoService depositoService = new DepositoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtener lista de depósitos desde la base de datos
//		List<Deposito> depositos = depositoService.getAllDepositos();

		// Guardar la lista de depósitos en la sesión
//		HttpSession session = request.getSession();
//		session.setAttribute("listaDepositos", depositos);

		
		List<Deposito> depositos = depositoService.getAllDepositos();
		for (Deposito deposito : depositos) {
		    // Forzar la carga de la colección
		    deposito.getProductos().size();
		}
		request.getSession().setAttribute("listaDepositos", depositos);

		
		// Redirigir a la página de salida de depósitos
		RequestDispatcher dispatcher = request.getRequestDispatcher("depositos_out.jsp");
		dispatcher.forward(request, response);
	}

}
