package aledep.controller.compra;

import aledep.service.CompraService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/eliminarCompra")
public class EliminarCompraServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CompraService compraService = new CompraService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int compraId = Integer.parseInt(request.getParameter("id"));

			compraService.desactivarCompra(compraId);

			Map<String, String> responseData = new HashMap<>();
			responseData.put("status", "success");
			responseData.put("message", "Compra eliminada con éxito");

			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(responseData));

		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de compra inválido");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar la compra");
		}
	}
}
