package aledep.controller.login;

import aledep.model.Usuario;
import aledep.service.UsuarioService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Usuario usuario = usuarioService.getUsuarioByEmail(email);

            if (usuario != null && password.equals(usuario.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", usuario);

                response.sendRedirect("menu.jsp");
            } else {
                request.setAttribute("error", "Usuario o contraseña incorrectos.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al autenticar el usuario");
        }
    }
}
