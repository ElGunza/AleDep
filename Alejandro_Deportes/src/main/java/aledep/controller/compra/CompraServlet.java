package aledep.controller.compra;

import aledep.model.Compra;
import aledep.model.Producto;
import aledep.model.Usuario;
import aledep.dto.CompraDTO;
import aledep.model.Proveedor;
import aledep.model.MetodoPago;
import aledep.service.CompraService;
import aledep.service.ProductoService;
import aledep.service.UsuarioService;
import aledep.service.ProveedorService;
import aledep.service.MetodoPagoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/compras")
public class CompraServlet extends HttpServlet {

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
            request.getSession().removeAttribute("listaComprasDTO");

            List<Compra> compras = compraService.getAllCompras();
            List<CompraDTO> comprasDTO = new ArrayList<>();
            for (Compra compra : compras) {
                CompraDTO compraDTO = compraService.convertirACompraDTO(compra);
                comprasDTO.add(compraDTO);
            }

            request.getSession().setAttribute("listaComprasDTO", comprasDTO);

            System.out.println("Compras cargadas: " + comprasDTO.size());

            List<Producto> productos = productoService.getProductosActivos();
            guardarProductosEnSesion(request.getSession(), productos);

            List<Proveedor> proveedores = proveedorService.getProveedoresActivos();
            guardarProveedoresEnSesion(request.getSession(), proveedores);

            List<Usuario> usuarios = usuarioService.getUsuariosActivos();
            guardarUsuariosEnSesion(request.getSession(), usuarios);

            List<MetodoPago> metpagos = metodoPagoService.getMetodosPagoActivos();
            guardarMetPagoEnSesion(request.getSession(), metpagos);

            RequestDispatcher dispatcher = request.getRequestDispatcher("compras_out.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            manejarExcepcion(response, e, "Error al obtener la lista de compras.");
        }
    }

    private void guardarUsuariosEnSesion(HttpSession session, List<Usuario> usuarios) {
        session.setAttribute("listaUsuarios", usuarios);
    }

    private void guardarProductosEnSesion(HttpSession session, List<Producto> productos) {
        session.setAttribute("listaProductos", productos);
    }

    private void guardarProveedoresEnSesion(HttpSession session, List<Proveedor> proveedores) {
        session.setAttribute("listaProveedores", proveedores);
    }

    private void guardarMetPagoEnSesion(HttpSession session, List<MetodoPago> metPago) {
        session.setAttribute("listaMetodosPago", metPago);
    }

    private void manejarExcepcion(HttpServletResponse response, Exception e, String mensaje) throws IOException {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mensaje);
    }
}
