package aledep.controller.usuario;

import com.google.gson.Gson;
import aledep.model.Usuario;
import aledep.service.RolService;
import aledep.service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/editarUsuario")
public class EditarUsuarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final UsuarioService usuarioService = new UsuarioService();
    private final RolService rolService = new RolService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	
        	cargarListaEnSesion(request.getSession());
        	
            int usuarioId = Integer.parseInt(request.getParameter("id"));
            Usuario usuario = usuarioService.getUsuarioById(usuarioId);

            if (usuario != null) {
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(usuario));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener el usuario");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Usuario usuario = obtenerUsuarioDesdeRequest(request);
            usuarioService.updateUsuario(usuario);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Usuario actualizado con éxito");

            response.setContentType("application/json");
            response.getWriter().write(new Gson().toJson(responseData));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el usuario");
        }
    }

    private Usuario obtenerUsuarioDesdeRequest(HttpServletRequest request) {
        int idUsuario = Integer.parseInt(request.getParameter("usuarioId"));
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);

        usuario.setNombre(request.getParameter("nombre"));
        usuario.setPassword(request.getParameter("password"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setActivo(Boolean.parseBoolean(request.getParameter("activo")));
        
        // Aquí también debes manejar la actualización del rol, si es necesario
        // Por ejemplo:
        // int idRol = Integer.parseInt(request.getParameter("idRol"));
        // Rol rol = usuarioService.getRolById(idRol); // Método que debes tener en UsuarioService
        // usuario.setRol(rol);

        return usuario;
    }
    
    private void cargarListaEnSesion(HttpSession session) {
    	session.setAttribute("listaRoles", rolService.getActivosRoles());
    }
    
}
