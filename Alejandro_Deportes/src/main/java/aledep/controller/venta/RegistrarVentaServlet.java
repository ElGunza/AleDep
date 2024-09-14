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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer clienteId = parseId(request.getParameter("clienteId"));
            Integer metodoPagoId = parseId(request.getParameter("metodoPagoId"));
            Cliente cliente = clienteService.getClienteById(clienteId);
            MetodoPago metodoPago = metodoPagoService.getMetodoPagoById(metodoPagoId);
            
            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

            if (cliente == null || metodoPago == null || usuarioLogueado == null) {
                throw new IllegalArgumentException("Datos de cliente, método de pago o usuario inválidos.");
            }

            Venta venta = new Venta();
            venta.setCliente(cliente);
            venta.setMetodoPago(metodoPago);
            venta.setUsuario(usuarioLogueado);
            venta.setFechaCreacion(new Date());
            venta.setActivo(true);

            List<VentaDetalle> detalles = procesarDetalles(request, venta);
            double precioTotal = calcularPrecioTotal(detalles);

            venta.setDetalles(detalles);
            venta.setPrecioTotal(precioTotal);

            ventaService.saveVenta(venta);

            VentaDTO ventaDTO = ventaService.convertirAVentaDTO(venta);
            ventaDTO.setFechaCreacionStr(formatDate(venta.getFechaCreacion()));

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Venta registrada con éxito");
            responseData.put("venta", ventaDTO);

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));

        } catch (Exception e) {
            manejarExcepcion(response, e, e.getMessage());
        }
    }

    private List<VentaDetalle> procesarDetalles(HttpServletRequest request, Venta venta) {
        String[] productosIds = request.getParameterValues("productoId[]");
        String[] cantidades = request.getParameterValues("cantidad[]");

        if (productosIds == null || cantidades == null || productosIds.length != cantidades.length) {
            throw new IllegalArgumentException("Productos y cantidades no coinciden.");
        }

        List<VentaDetalle> detalles = new ArrayList<>();

        for (int i = 0; i < productosIds.length; i++) {
            Integer productoId = parseId(productosIds[i]);
            Producto producto = productoService.getProductoById(productoId);
            Float cantidad = Float.parseFloat(cantidades[i]);

            VentaDetalle detalle = new VentaDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecioVenta());
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

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "error");
        responseData.put("message", mensaje); 

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
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
