package aledep.controller.compra;

import aledep.model.Compra;
import aledep.model.CompraDetalle;
import aledep.model.Producto;
import aledep.dto.CompraDTO;
import aledep.model.Proveedor;
import aledep.model.MetodoPago;
import aledep.model.Usuario;
import aledep.service.CompraService;
import aledep.service.ProductoService;
import aledep.service.UsuarioService;
import aledep.service.ProveedorService;
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

@WebServlet("/editarCompra")
public class EditarCompraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CompraService compraService = new CompraService();
    private final ProductoService productoService = new ProductoService();
    private final ProveedorService proveedorService = new ProveedorService();
    private final MetodoPagoService metodoPagoService = new MetodoPagoService();
    private final UsuarioService usuarioService = new UsuarioService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int compraId = parseId(request.getParameter("id"));
            Compra compra = compraService.getCompraById(compraId);

            if (compra != null) {
                CompraDTO compraDTO = compraService.convertirACompraDTO(compra);
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(compraDTO));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Compra no encontrada");
            }
        } catch (Exception e) {
            manejarExcepcion(response, e, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer proveedorId = parseId(request.getParameter("proveedorId"));
            Integer metodoPagoId = parseId(request.getParameter("metodoPagoId"));
            Integer compraId = parseId(request.getParameter("compraId"));
            Integer usuarioId = parseId(request.getParameter("usuarioId"));

            Proveedor proveedor = proveedorService.getProveedorById(proveedorId);
            MetodoPago metodoPago = metodoPagoService.getMetodoPagoById(metodoPagoId);
            Usuario usuario = usuarioService.getUsuarioById(usuarioId);

            validarDatos(proveedor, metodoPago, compraId);

            Compra compra = compraService.getCompraById(compraId);
            actualizarDatosCompra(compra, proveedor, metodoPago, usuario, request);

            List<CompraDetalle> detalles = procesarDetalles(request, compra);
            double precioTotal = calcularPrecioTotal(detalles);

            compra.setDetalles(detalles);
            compra.setPrecioTotal( precioTotal);

            compraService.updateCompra(compra);

            enviarRespuestaExito(response, "Compra actualizada con éxito");

        } catch (Exception e) {
            manejarExcepcion(response, e, e.getMessage());
        }
    }

    private void validarDatos(Proveedor proveedor, MetodoPago metodoPago, Integer compraId) {
        if (proveedor == null) {
            throw new IllegalArgumentException("Proveedor no encontrado.");
        }
        if (metodoPago == null) {
            throw new IllegalArgumentException("Método de pago no encontrado.");
        }
        if (compraId == null || compraId <= 0) {
            throw new IllegalArgumentException("ID de compra inválido.");
        }
    }

    private void actualizarDatosCompra(Compra compra, Proveedor proveedor, MetodoPago metodoPago, Usuario usuario, HttpServletRequest request) throws Exception {
        compra.setProveedor(proveedor);
        compra.setMetodoPago(metodoPago);
        compra.setUsuario(usuario);

        String fechaCreacionParam = request.getParameter("fechaCreacion");
        if (fechaCreacionParam != null && !fechaCreacionParam.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaCreacion = dateFormat.parse(fechaCreacionParam);
            compra.setFechaCreacion(fechaCreacion);
        } else {
            compra.setFechaCreacion(new Date());
        }
    }

    private List<CompraDetalle> procesarDetalles(HttpServletRequest request, Compra compra) {
        String[] productosIds = request.getParameterValues("productoId[]");
        String[] cantidades = request.getParameterValues("cantidad[]");
        String[] preciosUnitarios = request.getParameterValues("precioUnitario[]");

        if (productosIds == null || cantidades == null || productosIds.length != cantidades.length) {
            throw new IllegalArgumentException("Los productos y cantidades no coinciden.");
        }

        List<CompraDetalle> nuevosDetalles = new ArrayList<>();
        for (int i = 0; i < productosIds.length; i++) {
            Producto producto = productoService.getProductoById(parseId(productosIds[i]));
            Float cantidad = Float.parseFloat(cantidades[i]);
            Double precioUnitario = Double.parseDouble(preciosUnitarios[i]);

            CompraDetalle detalle = new CompraDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);

            if (precioUnitario < 0) {
                detalle.setPrecioUnitario(producto.getPrecioCompra());
            } else {
                detalle.setPrecioUnitario(precioUnitario);
            }

            detalle.setCompra(compra);
            nuevosDetalles.add(detalle);
        }
        
        return nuevosDetalles;
    }


    private double calcularPrecioTotal(List<CompraDetalle> detalles) {
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
