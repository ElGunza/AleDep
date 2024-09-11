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
import java.util.ArrayList;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int ventaId = parseInt(request.getParameter("id"), -1);
            if (ventaId > 0) {
                Venta venta = ventaService.getVentaById(ventaId);
                if (venta != null) {
                    VentaDTO ventaDTO = ventaService.convertirAVentaDTO(venta);
                    response.setContentType("application/json");
                    response.getWriter().write(new Gson().toJson(ventaDTO));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Venta no encontrada");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de venta inválido");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener la venta");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener cliente y método de pago
            Integer clienteId = Integer.parseInt(request.getParameter("clienteId"));
            Integer metodoPagoId = Integer.parseInt(request.getParameter("metodoPagoId"));			
            Cliente cliente = clienteService.getClienteById(clienteId);
            MetodoPago metodoPago = metodoPagoService.getMetodoPagoById(metodoPagoId);

            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

            if (usuarioLogueado == null) {
                throw new IllegalArgumentException("Usuario no autenticado.");
            }

            int ventaId = Integer.parseInt(request.getParameter("ventaId"));
            Venta venta = ventaService.getVentaById(ventaId);

            venta.setCliente(cliente);
            venta.setMetodoPago(metodoPago);
            venta.setUsuario(usuarioLogueado); // Asignar el usuario logueado

            List<VentaDetalle> detalles = new ArrayList<>();
            double precioTotal = 0;

            String[] productosIds = request.getParameterValues("productoId");
            String[] cantidades = request.getParameterValues("cantidad");

            for (int i = 0; i < productosIds.length; i++) {
                Integer productoId = Integer.parseInt(productosIds[i]);
                Producto producto = productoService.getProductoById(productoId);
                Float cantidad = Float.parseFloat(cantidades[i]);

                VentaDetalle detalle = new VentaDetalle();
                detalle.setProducto(producto);
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(producto.getPrecioVenta());
                detalle.setVenta(venta);

                detalles.add(detalle);

                precioTotal += cantidad * producto.getPrecioVenta();

                producto.setCantidad(producto.getCantidad() - cantidad.intValue());
                productoService.actualizarStock(productoId, producto.getCantidad());
            }

            venta.setDetalles(detalles);
            venta.setPrecioTotal(precioTotal);

            ventaService.updateVenta(venta);

            // Responder con éxito
            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Venta actualizada con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));

        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al actualizar la venta.");
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

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
