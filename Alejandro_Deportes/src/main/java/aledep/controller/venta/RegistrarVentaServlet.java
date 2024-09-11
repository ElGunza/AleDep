package aledep.controller.venta;

import aledep.model.Venta;
import aledep.model.VentaDetalle;
import aledep.model.Producto;
import aledep.dto.VentaDTO;
import aledep.model.Cliente;
import aledep.model.MetodoPago;
import aledep.model.Usuario;
import aledep.service.VentaService;
import aledep.service.ProductoService;
import aledep.service.ClienteService;
import aledep.service.MetodoPagoService;
//import aledep.service.UsuarioService;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/registrarVenta")
public class RegistrarVentaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final VentaService ventaService = new VentaService();
	private final ProductoService productoService = new ProductoService();
	private final ClienteService clienteService = new ClienteService();
	private final MetodoPagoService metodoPagoService = new MetodoPagoService();
//	private final UsuarioService usuarioService = new UsuarioService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Obtener cliente y método de pago
			Integer clienteId = Integer.parseInt(request.getParameter("clienteId"));
			Integer metodoPagoId = Integer.parseInt(request.getParameter("metodoPagoId"));
			Cliente cliente = clienteService.getClienteById(clienteId);
			MetodoPago metodoPago = metodoPagoService.getMetodoPagoById(metodoPagoId);

			// Obtener el usuario logueado desde la sesión
			HttpSession session = request.getSession();
			Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

			if (usuarioLogueado == null) {
				throw new IllegalArgumentException("Usuario no autenticado.");
			}

			// Crear una nueva venta
			Venta venta = new Venta();
			venta.setCliente(cliente);
			venta.setMetodoPago(metodoPago);
			venta.setUsuario(usuarioLogueado); // Asignar el usuario logueado
			venta.setFechaCreacion(new Date());
			venta.setActivo(true);

			// Procesar los productos de la venta
			List<VentaDetalle> detalles = new ArrayList<>();
			double precioTotal = 0;

			String[] productosIds = request.getParameterValues("productoId");
			String[] cantidades = request.getParameterValues("cantidad");

			for (int i = 0; i < productosIds.length; i++) {
				Integer productoId = Integer.parseInt(productosIds[i]);
				Producto producto = productoService.getProductoById(productoId);
				Float cantidad = Float.parseFloat(cantidades[i]);

				// Crear detalle de la venta
				VentaDetalle detalle = new VentaDetalle();
				detalle.setProducto(producto);
				detalle.setCantidad(cantidad);
				detalle.setPrecioUnitario(producto.getPrecioVenta());
				detalle.setVenta(venta);

				detalles.add(detalle);

				// Calcular el precio total
				precioTotal += cantidad * producto.getPrecioVenta();

				// Actualizar el stock del producto
				producto.setCantidad(producto.getCantidad() - cantidad.intValue());
				productoService.actualizarStock(productoId, producto.getCantidad());
			}

			// Asignar detalles y precio total a la venta
			venta.setDetalles(detalles);
			venta.setPrecioTotal(precioTotal);

			// Guardar la venta
			ventaService.saveVenta(venta);

			// Convertir Venta a VentaDTO para la respuesta
			VentaDTO ventaDTO = ventaService.convertirAVentaDTO(venta);

			// Formatear la fecha de creación como String
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String fechaCreacionStr = dateFormat.format(venta.getFechaCreacion());
			ventaDTO.setFechaCreacionStr(fechaCreacionStr);

			// Responder con éxito y enviar el VentaDTO
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("status", "success");
			responseData.put("message", "Venta registrada con éxito");
			responseData.put("venta", ventaDTO);

			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(responseData));

		} catch (Exception e) {
			manejarExcepcion(response, e, "Error al registrar la venta.");
		}
	}

	// Método para manejar excepciones y enviar una respuesta de error
	private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
		e.printStackTrace();
		Map<String, String> responseData = new HashMap<>();
		responseData.put("status", "error");
		responseData.put("message", mensaje);
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(responseData));
	}
}
