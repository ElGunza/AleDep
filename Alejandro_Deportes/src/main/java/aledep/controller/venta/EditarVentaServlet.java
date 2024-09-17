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
import aledep.service.UsuarioService;
import aledep.service.ClienteService;
import aledep.service.MetodoPagoService;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/editarVenta")
public class EditarVentaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final VentaService ventaService = new VentaService();
    private final ProductoService productoService = new ProductoService();
    private final ClienteService clienteService = new ClienteService();
    private final MetodoPagoService metodoPagoService = new MetodoPagoService();
    private final UsuarioService usuarioService = new UsuarioService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int ventaId = parseId(request.getParameter("id"));
            Venta venta = ventaService.getVentaById(ventaId);

            if (venta != null) {
                VentaDTO ventaDTO = ventaService.convertirAVentaDTO(venta);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(ventaDTO));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Venta no encontrada");
            }
        } catch (Exception e) {
        	manejarExcepcion(response, e, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	
            Integer clienteId = parseId(request.getParameter("clienteId"));
            Integer metodoPagoId = parseId(request.getParameter("metodoPagoId"));
            Integer ventaId = parseId(request.getParameter("ventaId"));
            Integer usuarioId = parseId(request.getParameter("usuarioId"));

            Cliente cliente = clienteService.getClienteById(clienteId);
            MetodoPago metodoPago = metodoPagoService.getMetodoPagoById(metodoPagoId);

            Usuario usuario = usuarioService.getUsuarioById(usuarioId);
            
            validarDatos(cliente, metodoPago, ventaId);

            Venta venta = ventaService.getVentaById(ventaId);
            actualizarDatosVenta(venta, cliente, metodoPago, usuario, request);

            List<VentaDetalle> detalles = procesarDetalles(request, venta);
            double precioTotal = calcularPrecioTotal(detalles);

            venta.setDetalles(detalles);
            venta.setPrecioTotal(precioTotal);

            ventaService.updateVenta(venta);

            enviarRespuestaExito(response, "Venta actualizada con éxito");

        } catch (Exception e) {
        	manejarExcepcion(response, e, e.getMessage());
        }
    }

    private void validarDatos(Cliente cliente, MetodoPago metodoPago, Integer ventaId) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
        if (metodoPago == null) {
            throw new IllegalArgumentException("Método de pago no encontrado.");
        }
        if (ventaId == null || ventaId <= 0) {
            throw new IllegalArgumentException("ID de venta inválido.");
        }
    }

    private void actualizarDatosVenta(Venta venta, Cliente cliente, MetodoPago metodoPago, Usuario usuarioLogueado, HttpServletRequest request) throws Exception {
        venta.setCliente(cliente);
        venta.setMetodoPago(metodoPago);
        venta.setUsuario(usuarioLogueado);

        String fechaCreacionParam = request.getParameter("fechaCreacion");
        if (fechaCreacionParam != null && !fechaCreacionParam.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion = dateFormat.parse(fechaCreacionParam);
            venta.setFechaCreacion(fechaCreacion);
        } else {
            venta.setFechaCreacion(new Date());
        }
    }

	private List<VentaDetalle> procesarDetalles(HttpServletRequest request, Venta venta) {
        String[] productosIds = request.getParameterValues("productoId[]");
        String[] cantidades = request.getParameterValues("cantidad[]");
        String[] preciosUnitarios = request.getParameterValues("precioUnitario[]");

        if (productosIds == null || cantidades == null || productosIds.length != cantidades.length) {
            throw new IllegalArgumentException("Los productos y cantidades no coinciden.");
        }

        List<VentaDetalle> detalles = new ArrayList<>();
        for (int i = 0; i < productosIds.length; i++) {
            Producto producto = productoService.getProductoById(parseId(productosIds[i]));
            Float cantidad = Float.parseFloat(cantidades[i]);
            Double precioUnitario = Double.parseDouble(preciosUnitarios[i]);
            
            VentaDetalle detalle = new VentaDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            
//            Revisar esto, el precio del producto deberia salir una vez que se elige
            if (precioUnitario<0) {
                detalle.setPrecioUnitario(producto.getPrecioVenta());
            } else {
                detalle.setPrecioUnitario(precioUnitario);
            }
            
            detalle.setVenta(venta);

            detalles.add(detalle);

            actualizarStockProducto(producto, cantidad);
        }
        return detalles;
    }

    private void actualizarStockProducto(Producto producto, Float cantidad) {
        int nuevaCantidad = producto.getCantidad() - cantidad.intValue();
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
        }
        producto.setCantidad(nuevaCantidad);
        productoService.actualizarStock(producto.getIdProducto(), nuevaCantidad);
    }

    private double calcularPrecioTotal(List<VentaDetalle> detalles) {
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();
    }

    private void enviarRespuestaExito(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("message", mensaje);

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(responseData));
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "error");
        responseData.put("message", mensaje);  // Enviar el mensaje de la excepción al frontend

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // Código de error 400
        response.getWriter().write(new Gson().toJson(responseData));
    }

    private Integer parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
