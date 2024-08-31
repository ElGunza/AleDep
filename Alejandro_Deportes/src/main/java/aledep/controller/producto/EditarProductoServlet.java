package aledep.controller.producto;

import com.google.gson.Gson;
import aledep.dto.ProductoDTO;
import aledep.model.Producto;
import aledep.service.ProductoService;
import aledep.service.CategoriaService;
import aledep.service.MarcaService;
import aledep.service.ProveedorService;
import aledep.service.DepositoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
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

        try {
            cargarListasEnSesion(request.getSession());

            int productoId = parseInt(request.getParameter("id"), -1);

            if (productoId > 0) {
                Producto producto = productoService.getProductoById(productoId);

                if (producto != null) {
                    ProductoDTO productoDTO = productoService.convertirAProductoDTO(producto);
                    enviarRespuestaExitosa(response, productoDTO);
                } else {
                    enviarRespuestaError(response, "Producto no encontrado");
                }
            } else {
                enviarRespuestaError(response, "ID de producto inválido");
            }
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al obtener el producto.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Producto producto = obtenerProductoDesdeRequest(request);

            productoService.updateProducto(producto);

            enviarRespuestaExitosa(response, "Producto actualizado con éxito");
        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al actualizar el producto.");
        }
    }

    private Producto obtenerProductoDesdeRequest(HttpServletRequest request) {
        int idProducto = parseInt(request.getParameter("productoId"), -1);
        Producto producto = productoService.getProductoById(idProducto);

        producto.setCodigo(request.getParameter("codigo"));
        producto.setNombre(request.getParameter("nombre"));
        producto.setTalle(request.getParameter("talle"));
        producto.setCantidad(parseInt(request.getParameter("cantidad"), 0));
        producto.setPrecioVenta(parseDouble(request.getParameter("precioVenta"), 0.0));
        producto.setPrecioCompra(parseDouble(request.getParameter("precioCompra"), 0.0));
        producto.setActivo(Boolean.parseBoolean(request.getParameter("activo")));

        producto.setCategoria(categoriaService.getCategoriaById(parseInt(request.getParameter("idCategoria"), 0)));
        producto.setMarca(marcaService.getMarcaById(parseInt(request.getParameter("idMarca"), 0)));
        producto.setProveedor(proveedorService.getProveedorById(parseInt(request.getParameter("idProveedor"), 0)));
        producto.setDeposito(depositoService.getDepositoById(parseInt(request.getParameter("idDeposito"), 0)));

        return producto;
    }

    private void cargarListasEnSesion(HttpSession session) {
        session.setAttribute("listaCategorias", categoriaService.getAllCategorias());
        session.setAttribute("listaMarcas", marcaService.getAllMarcas());
        session.setAttribute("listaProveedores", proveedorService.getAllProveedores());
        session.setAttribute("listaDepositos", depositoService.getDepositosActivos());
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        enviarRespuestaError(response, mensaje);
    }

    private void enviarRespuestaExitosa(HttpServletResponse response, Object data) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("data", data);  // Asegúrate de usar la clave "data" aquí.

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(responseData));
    }

    private void enviarRespuestaError(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("status", "error");
        responseData.put("message", mensaje);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(new Gson().toJson(responseData));
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
