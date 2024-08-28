package aledep.controller;

import com.google.gson.Gson;
import aledep.model.*;
import aledep.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/altaProducto")
public class AltaProductoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ProductoService productoService = new ProductoService();
	private final CategoriaService categoriaService = new CategoriaService();
	private final MarcaService marcaService = new MarcaService();
	private final ProveedorService proveedorService = new ProveedorService();
	private final DepositoService depositoService = new DepositoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		cargarListasEnSesion(request.getSession());

		// Obtener el producto por ID y devolverlo en formato JSON
		int productoId = Integer.parseInt(request.getParameter("id"));
		Producto producto = productoService.getProductoById(productoId);
		if (producto != null) {
			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(producto));
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Producto producto = obtenerProductoDesdeRequest(request);

		productoService.saveProducto(producto);

		// Responder con un JSON indicando éxito
		Map<String, String> responseData = new HashMap<>();
		responseData.put("status", "success");
		responseData.put("message", "Producto guardado con éxito");

		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(responseData));
	}

	/**
	 * Carga las listas de categorías, marcas, proveedores y depósitos en la sesión.
	 *
	 * @param session La sesión HTTP en la que se guardarán las listas.
	 */
	private void cargarListasEnSesion(HttpSession session) {
		List<Categoria> categorias = categoriaService.getAllCategorias();
		List<Marca> marcas = marcaService.getAllMarcas();
		List<Proveedor> proveedores = proveedorService.getAllProveedores();
		List<Deposito> depositos = depositoService.getAllDepositos();

		session.setAttribute("listaCategorias", categorias);
		session.setAttribute("listaMarcas", marcas);
		session.setAttribute("listaProveedores", proveedores);
		session.setAttribute("listaDepositos", depositos);
	}

	/**
	 * Obtiene un objeto Producto a partir de los parámetros de la solicitud HTTP.
	 *
	 * @param request La solicitud HTTP.
	 * @return Un objeto Producto con los datos de la solicitud.
	 */
	private Producto obtenerProductoDesdeRequest(HttpServletRequest request) {
		String idProductoStr = request.getParameter("productoId");
		Integer idProducto = (idProductoStr != null && !idProductoStr.isEmpty()) ? Integer.parseInt(idProductoStr)
				: null;

		Producto producto = (idProducto != null) ? productoService.getProductoById(idProducto) : new Producto();

		// Configurar los atributos del producto
		producto.setCodigo(request.getParameter("codigo"));
		producto.setNombre(request.getParameter("nombre"));
		producto.setTalle(request.getParameter("talle"));
		producto.setCantidad(parseInt(request.getParameter("cantidad"), 0));
		producto.setPrecioVenta(parseDouble(request.getParameter("precioVenta"), 0.0));
		producto.setPrecioCompra(parseDouble(request.getParameter("precioCompra"), 0.0));
		producto.setActivo(true);

		// Configurar las entidades relacionadas
		producto.setCategoria(categoriaService.getCategoriaById(parseInt(request.getParameter("idCategoria"), 0)));
		producto.setMarca(marcaService.getMarcaById(parseInt(request.getParameter("idMarca"), 0)));
		producto.setProveedor(proveedorService.getProveedorById(parseInt(request.getParameter("idProveedor"), 0)));
		producto.setDeposito(depositoService.getDepositoById(parseInt(request.getParameter("idDeposito"), 0)));

		return producto;
	}

	/**
	 * Parsea un String a un int, devolviendo un valor predeterminado en caso de
	 * error.
	 *
	 * @param value        El String a parsear.
	 * @param defaultValue El valor predeterminado a devolver en caso de error.
	 * @return El int parseado o el valor predeterminado.
	 */
	private int parseInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Parsea un String a un double, devolviendo un valor predeterminado en caso de
	 * error.
	 *
	 * @param value        El String a parsear.
	 * @param defaultValue El valor predeterminado a devolver en caso de error.
	 * @return El double parseado o el valor predeterminado.
	 */
	private double parseDouble(String value, double defaultValue) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
