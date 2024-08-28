package aledep.controller;

import aledep.model.Producto;
import aledep.service.ProductoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/productos")
public class ProductServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductoService productoService = new ProductoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtener lista de productos desde la base de datos
		List<Producto> productos = productoService.getAllProductos();

		HttpSession productsSession = request.getSession();
		productsSession.setAttribute("listaProductos", productos);


		RequestDispatcher dispatcher = request.getRequestDispatcher("productos_out.jsp"); // Ajusta la ruta
		dispatcher.forward(request, response);

	}

}
