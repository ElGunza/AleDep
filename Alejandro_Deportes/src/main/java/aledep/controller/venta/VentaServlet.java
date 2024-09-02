package aledep.controller.venta;

import aledep.model.Venta;
import aledep.model.Producto;
import aledep.model.Cliente;
import aledep.model.MetodoPago;
import aledep.service.VentaService;
import aledep.service.ProductoService;
import aledep.service.ClienteService;
import aledep.service.MetodoPagoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/ventas")
public class VentaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final VentaService ventaService = new VentaService();
	private final ProductoService productoService = new ProductoService();
	private final ClienteService clienteService = new ClienteService();
	private final MetodoPagoService metodoPagoService = new MetodoPagoService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<Venta> ventas = ventaService.getAllVentas();
			guardarVentasEnSesion(request.getSession(), ventas);

			// Cargar la lista de productos
			List<Producto> productos = productoService.getProductosActivos();
			guardarProductosEnSesion(request.getSession(), productos);
			
			List<Cliente> clientes = clienteService.getClientesActivos();
			guardarClientesEnSesion(request.getSession(), clientes);
			
			List<MetodoPago> metpagos = metodoPagoService.getMetodosPagoActivos();
			guardarMetPagoEnSesion(request.getSession(),metpagos);

			RequestDispatcher dispatcher = request.getRequestDispatcher("ventas_out.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			manejarExcepcion(response, e, "Error al obtener la lista de ventas.");
		}
	}

	private void guardarVentasEnSesion(HttpSession session, List<Venta> ventas) {
		session.setAttribute("listaVentas", ventas);
	}

	private void guardarProductosEnSesion(HttpSession session, List<Producto> productos) {
		session.setAttribute("listaProductos", productos);
	}
	
	private void guardarClientesEnSesion(HttpSession session, List<Cliente> clientes) {
		session.setAttribute("listaClientes", clientes);
	}
	
	private void guardarMetPagoEnSesion(HttpSession session, List<MetodoPago> metPago) {
		session.setAttribute("listaMetodosPago", metPago);
	}

	private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
		e.printStackTrace();
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
	}
}
