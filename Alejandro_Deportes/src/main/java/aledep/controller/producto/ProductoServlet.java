package aledep.controller.producto;

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
public class ProductoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProductoService productoService = new ProductoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String filtro = request.getParameter("filtro");

		List<Producto> productos;

		if ("todos".equalsIgnoreCase(filtro)) {
			productos = productoService.getAllProductos();
		} else {
			productos = productoService.getProductosActivos();
		}

		// Guardar la lista de productos en la sesión
		HttpSession productsSession = request.getSession();
		productsSession.setAttribute("listaProductos", productos);

		RequestDispatcher dispatcher = request.getRequestDispatcher("productos_out.jsp"); 
		dispatcher.forward(request, response);
	}
}
