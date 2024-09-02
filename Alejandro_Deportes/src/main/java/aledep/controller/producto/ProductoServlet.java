package aledep.controller.producto;

import aledep.model.Producto;
import aledep.service.ProductoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProductoService productoService = new ProductoService();

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filtro = request.getParameter("filtro");
            List<Producto> productos;

            if ("todos".equalsIgnoreCase(filtro)) {
                productos = productoService.getAllProductos();
            } else {
                productos = productoService.getProductosActivos();
            }

            guardarProductosEnSesion(request.getSession(), productos);

            RequestDispatcher dispatcher = request.getRequestDispatcher("productos_out.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al obtener la lista de productos.");
        }
    }

    /**
     * Guarda la lista de productos en la sesión.
     *
     * @param session   La sesión HTTP donde se guardará la lista.
     * @param productos La lista de productos a guardar.
     */
    private void guardarProductosEnSesion(HttpSession session, List<Producto> productos) {
        session.setAttribute("listaProductos", productos);
    }

    /**
     * Maneja las excepciones y responde con un mensaje de error.
     *
     * @param response El objeto HttpServletResponse.
     * @param e        La excepción capturada.
     * @param mensaje  El mensaje de error a enviar al cliente.
     * @throws IOException Si ocurre un error al escribir la respuesta.
     */
    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }
}
