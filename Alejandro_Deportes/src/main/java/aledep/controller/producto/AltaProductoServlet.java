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

        try {
            cargarListasEnSesion(request.getSession());

            String idProductoStr = request.getParameter("id");
            Producto producto;
            ProductoDTO productoDTO;

            if (idProductoStr != null && !idProductoStr.isEmpty()) {
                // Si se proporciona un ID de producto, obtener el producto existente
                int productoId = Integer.parseInt(idProductoStr);
                producto = productoService.getProductoById(productoId);

                if (producto != null) {
                    productoDTO = productoService.convertirAProductoDTO(producto);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
                    return;
                }
            } else {
                // Si no se proporciona un ID, es un nuevo producto; generar un nuevo código
                producto = new Producto();
                int idSiguienteProducto = productoService.obtenerSiguienteIdProducto(); // Obtener el siguiente ID de producto
                String codigoGenerado = productoService.generarCodigoProducto(idSiguienteProducto);
                producto.setCodigo(codigoGenerado);

                // Crear un ProductoDTO para el nuevo producto con el código generado
                productoDTO = productoService.convertirAProductoDTO(producto);
            }

            // Devolver el ProductoDTO en formato JSON para ser usado en el modal
            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(productoDTO));

        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al obtener el producto.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Producto producto = obtenerProductoDesdeRequest(request);

            // Guardar el producto usando el servicio, que generará el código si es necesario
            productoService.saveProducto(producto);

            // Responder con un JSON indicando éxito
            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Producto guardado con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al guardar el producto.");
        }
    }

    private void cargarListasEnSesion(HttpSession session) {
        session.setAttribute("listaCategorias", categoriaService.getAllCategorias());
        session.setAttribute("listaMarcas", marcaService.getAllMarcas());
        session.setAttribute("listaProveedores", proveedorService.getAllProveedores());
        session.setAttribute("listaDepositos", depositoService.getAllDepositos());
    }

    /**
     * Obtiene un objeto Producto a partir de los parámetros de la solicitud HTTP.
     *
     * @param request La solicitud HTTP.
     * @return Un objeto Producto con los datos de la solicitud.
     */
    private Producto obtenerProductoDesdeRequest(HttpServletRequest request) {
        String idProductoStr = request.getParameter("productoId");
        Integer idProducto = (idProductoStr != null && !idProductoStr.isEmpty()) ? Integer.parseInt(idProductoStr) : null;

        Producto producto = (idProducto != null) ? productoService.getProductoById(idProducto) : new Producto();

        producto.setNombre(request.getParameter("nombre"));
        producto.setTalle(request.getParameter("talle"));
        producto.setCantidad(parseInt(request.getParameter("cantidad"), 0));
        producto.setPrecioVenta(parseDouble(request.getParameter("precioVenta"), 0.0));
        producto.setPrecioCompra(parseDouble(request.getParameter("precioCompra"), 0.0));
        producto.setActivo(true);

        // No permitir que el código sea modificado
        if (producto.getCodigo() == null || producto.getCodigo().isEmpty()) {
            int idSiguienteProducto = productoService.obtenerSiguienteIdProducto(); // Obtener el siguiente ID de producto
            String codigoGenerado = productoService.generarCodigoProducto(idSiguienteProducto);
            producto.setCodigo(codigoGenerado); // Asignar el código generado
        }

        // Configurar las entidades relacionadas
        producto.setCategoria(categoriaService.getCategoriaById(parseInt(request.getParameter("idCategoria"), 0)));
        producto.setMarca(marcaService.getMarcaById(parseInt(request.getParameter("idMarca"), 0)));
        producto.setProveedor(proveedorService.getProveedorById(parseInt(request.getParameter("idProveedor"), 0)));
        producto.setDeposito(depositoService.getDepositoById(parseInt(request.getParameter("idDeposito"), 0)));

        return producto;
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
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
