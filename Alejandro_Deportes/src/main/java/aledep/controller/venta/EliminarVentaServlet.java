package aledep.controller.venta;

import aledep.service.VentaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/eliminarVenta")
public class EliminarVentaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final VentaService ventaService = new VentaService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int ventaId = Integer.parseInt(request.getParameter("id"));

			ventaService.desactivarVenta(ventaId);

			Map<String, String> responseData = new HashMap<>();
			responseData.put("status", "success");
			responseData.put("message", "Venta eliminada con éxito");

			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(responseData));

		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de venta inválido");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar la venta");
		}
	}
}
