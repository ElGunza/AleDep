package aledep.dao.interfaces;

import aledep.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void saveUsuario(Usuario usuario);

    void updateUsuario(Usuario usuario);

    void deleteUsuario(Integer id);

    void desactivarUsuario(Integer id);

    List<Usuario> getAllUsuarios();

    List<Usuario> getUsuariosActivos();
    
    Usuario getUsuarioById(Integer id);

    Usuario getUsuarioByEmail(String email);

}
