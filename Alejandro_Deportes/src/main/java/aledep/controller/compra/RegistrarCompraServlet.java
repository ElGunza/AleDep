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
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/registrarCompra")
public class RegistrarCompraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CompraService compraService = new CompraService();
    private final ProductoService productoService = new ProductoService();
    private final ProveedorService proveedorService = new ProveedorService();
    private final MetodoPagoService metodoPagoService = new MetodoPagoService();
    private final UsuarioService usuarioService = new UsuarioService();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer proveedorId = parseId(request.getParameter("proveedorId"));
            Integer metodoPagoId = parseId(request.getParameter("metodoPagoId"));
            Integer usuarioId = parseId(request.getParameter("usuarioId"));
            Proveedor proveedor = proveedorService.getProveedorById(proveedorId);
            MetodoPago metodoPago = metodoPagoService.getMetodoPagoById(metodoPagoId);
            Usuario usuario = usuarioService.getUsuarioById(usuarioId);
            
            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

            if (proveedor == null || metodoPago == null || usuarioLogueado == null) {
                throw new IllegalArgumentException("Datos de proveedor, método de pago o usuario inválidos.");
            }

            Compra compra = new Compra();
            compra.setProveedor(proveedor);
            compra.setMetodoPago(metodoPago);
            compra.setUsuario(usuario);
            compra.setFechaCreacion(new Date());
            compra.setActivo(true);

            System.out.println("NEW COMPRA SERVLET");
            
            List<CompraDetalle> detalles = procesarDetalles(request, compra);
            double precioTotal = calcularPrecioTotal(detalles);

            compra.setDetalles(detalles);
            compra.setPrecioTotal (precioTotal);

            
            System.out.println("Compra creada con proveedor: " + compra.getProveedor().getNombre());
            System.out.println("Total detalles: " + compra.getDetalles().size());

            for (CompraDetalle detalle : compra.getDetalles()) {
                System.out.println("Detalle: Producto: " + detalle.getProducto().getNombre() +
                                   ", Cantidad: " + detalle.getCantidad() +
                                   ", Precio Unitario: " + detalle.getPrecioUnitario());
            }
            
            compraService.saveCompra(compra);

            CompraDTO compraDTO = compraService.convertirACompraDTO(compra);
            compraDTO.setFechaCreacionStr(formatDate(compra.getFechaCreacion()));

            
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Compra registrada con éxito");
            responseData.put("compra", compraDTO);

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));

        } catch (Exception e) {
            manejarExcepcion(response, e, e.getMessage());
        }
    }

    private List<CompraDetalle> procesarDetalles(HttpServletRequest request, Compra compra) {
        String[] productosIds = request.getParameterValues("productoId[]");
        String[] cantidades = request.getParameterValues("cantidad[]");

        if (productosIds == null || cantidades == null || productosIds.length != cantidades.length) {
            throw new IllegalArgumentException("Productos y cantidades no coinciden.");
        }

        List<CompraDetalle> detalles = new ArrayList<>();

        for (int i = 0; i < productosIds.length; i++) {
            Integer productoId = parseId(productosIds[i]);
            Producto producto = productoService.getProductoById(productoId);
            Float cantidad = Float.parseFloat(cantidades[i]);  
            
            CompraDetalle detalle = new CompraDetalle();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(producto.getPrecioCompra());
            detalle.setCompra(compra);

            detalles.add(detalle);

            actualizarStockProducto(producto, cantidad);
        }
        return detalles;
    }

    private void actualizarStockProducto(Producto producto, Float cantidad) {
        int nuevaCantidad = producto.getCantidad() + cantidad.intValue();
        producto.setCantidad(nuevaCantidad);
        productoService.actualizarStock(producto.getIdProducto(), nuevaCantidad);
    }

    private double calcularPrecioTotal(List<CompraDetalle> detalles) {
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
