package aledep.controller.categoria;

import aledep.model.Categoria;
import aledep.service.CategoriaService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categorias")
public class CategoriaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CategoriaService categoriaService = new CategoriaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String filtro = request.getParameter("filtro");

            List<Categoria> categorias;

            if ("todos".equalsIgnoreCase(filtro)) {
                categorias = categoriaService.getAllCategorias();
            } else {
                categorias = categoriaService.getCategoriasActivas();
            }

            // Inicializa la lista de productos para cada categoría si es lazy-loaded
//            categorias.forEach(categoria -> categoria.getProductos().size());

            // Guardar la lista de categorías en el request (no en la sesión)
            request.setAttribute("listaCategorias", categorias);

            RequestDispatcher dispatcher = request.getRequestDispatcher("categorias_out.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener las categorías");
        }
    }
}
