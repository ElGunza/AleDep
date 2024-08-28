package aledep.service;

import aledep.dao.ProveedorDAO;
import aledep.model.Proveedor;

import java.util.List;

public class ProveedorService {
    private ProveedorDAO proveedorDAO = new ProveedorDAO();

    public void saveProveedor(Proveedor proveedor) {
        // AGREGAR VALIDACIONES
        proveedorDAO.saveProveedor(proveedor);
    }

    public List<Proveedor> getAllProveedores() {
        return proveedorDAO.getAllProveedores();
    }

    public Proveedor getProveedorById(Integer id) {
        return proveedorDAO.getProveedorById(id);
    }

    public void updateProveedor(Proveedor proveedor) {
        // AGREGAR VALIDACIONES
        proveedorDAO.updateProveedor(proveedor);
    }

    public void deleteProveedor(Integer id) {
        proveedorDAO.deleteProveedor(id);
    }
}
