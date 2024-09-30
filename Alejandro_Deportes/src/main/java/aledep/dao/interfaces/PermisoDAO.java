package aledep.dao.interfaces;

import aledep.model.Permiso;
import java.util.List;

public interface PermisoDAO {
    void savePermiso(Permiso permiso);

    void updatePermiso(Permiso permiso);

    void deletePermiso(Integer id);

    void desactivarPermiso(Integer id);
    
    Permiso getPermisoById(Integer id);

    List<Permiso> getAllPermisos();

    List<Permiso> getActivosPermisos();
}
