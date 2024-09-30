package aledep.service;

import aledep.dao.interfaces.PermisoDAO;
import aledep.dao.impl.PermisoDAOImpl;
import aledep.model.Permiso;

import java.util.List;

public class PermisoService {
    private final PermisoDAO permisoDAO;

    public PermisoService() {
        this.permisoDAO = new PermisoDAOImpl();
    }

    public void savePermiso(Permiso permiso) {
        permisoDAO.savePermiso(permiso);
    }

    public void updatePermiso(Permiso permiso) {
        permisoDAO.updatePermiso(permiso);
    }

    public void deletePermiso(Integer id) {
        permisoDAO.deletePermiso(id);
    }

    public void desactivarPermiso(Integer id) {
        permisoDAO.desactivarPermiso(id);
    }
    
    public Permiso getPermisoById(Integer id) {
        return permisoDAO.getPermisoById(id);
    }

    public List<Permiso> getAllPermisos() {
        return permisoDAO.getAllPermisos();
    }

    public List<Permiso> getActivosPermisos() {
        return permisoDAO.getActivosPermisos();
    }
}
