package aledep.dao.interfaces;

import aledep.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void saveUsuario(Usuario usuario);

    void updateUsuario(Usuario usuario);

    void deleteUsuario(Integer id);

    void desactivarUsuario(Integer id);

    List<Usuario> getAllUsuarios();

    Usuario getUsuarioById(Integer id);

    List<Usuario> getUsuariosActivos();
}
