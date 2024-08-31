package aledep.controller.producto;

import com.google.gson.Gson;

import aledep.dto.ProductoDTO;
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

@WebServlet("/editarProducto")
public class EditarProductoServlet extends HttpServlet {

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

		int productoId = Integer.parseInt(request.getParameter("id"));
		Producto producto = productoService.getProductoById(productoId);

		if (producto != null) {
			ProductoDTO productoDTO = productoService.convertirAProductoDTO(producto);
			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(productoDTO));
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Producto producto = obtenerProductoDesdeRequest(request);

		productoService.updateProducto(producto);

		Map<String, String> responseData = new HashMap<>();
		responseData.put("status", "success");
		responseData.put("message", "Producto actualizado con éxito");
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(responseData));
	}

	private Producto obtenerProductoDesdeRequest(HttpServletRequest request) {
		Integer idProducto = Integer.parseInt(request.getParameter("productoId"));
		Producto producto = productoService.getProductoById(idProducto);

		// Configurar los atributos del producto
		producto.setIdProducto(idProducto);
		producto.setCodigo(request.getParameter("codigo"));
		producto.setNombre(request.getParameter("nombre"));
		producto.setTalle(request.getParameter("talle"));
		producto.setCantidad(parseInt(request.getParameter("cantidad"), 0));
		producto.setPrecioVenta(parseDouble(request.getParameter("precioVenta"), 0.0));
		producto.setPrecioCompra(parseDouble(request.getParameter("precioCompra"), 0.0));
		producto.setActivo(Boolean.parseBoolean(request.getParameter("activo")));

		// Configurar las entidades relacionadas
		producto.setCategoria(categoriaService.getCategoriaById(parseInt(request.getParameter("idCategoria"), 0)));
		producto.setMarca(marcaService.getMarcaById(parseInt(request.getParameter("idMarca"), 0)));
		producto.setProveedor(proveedorService.getProveedorById(parseInt(request.getParameter("idProveedor"), 0)));
		producto.setDeposito(depositoService.getDepositoById(parseInt(request.getParameter("idDeposito"), 0)));

		return producto;
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

	private int parseInt(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private double parseDouble(String value, double defaultValue) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}