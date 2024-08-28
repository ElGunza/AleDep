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
		
		List<Deposito> depositos = depositoService.getAllDepositos();
		for (Deposito deposito : depositos) {
		    deposito.getProductos().size();
		}
		request.getSession().setAttribute("listaDepositos", depositos);

		RequestDispatcher dispatcher = request.getRequestDispatcher("depositos_out.jsp");
		dispatcher.forward(request, response);
	}

}
