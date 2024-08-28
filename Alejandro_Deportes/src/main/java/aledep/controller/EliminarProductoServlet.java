package aledep.controller;

import aledep.service.ProductoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

@WebServlet("/eliminarProducto")
public class EliminarProductoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ProductoService productoService = new ProductoService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int productoId = Integer.parseInt(request.getParameter("id"));

		// Llama al método de servicio para eliminar el producto
		productoService.deleteProducto(productoId);

		Map<String, String> responseData = new HashMap<>();
		responseData.put("status", "success");
		responseData.put("message", "Producto eliminado con éxito");

		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(responseData));
	}
}
