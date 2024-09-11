package aledep.controller.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener la sesión actual, si existe
        HttpSession session = request.getSession(false); // false evita crear una nueva sesión si no existe

        if (session != null) {
            session.invalidate(); // Invalida la sesión actual
        }

        // Redirigir al login después de cerrar la sesión
        response.sendRedirect("login.jsp");
    }
}
