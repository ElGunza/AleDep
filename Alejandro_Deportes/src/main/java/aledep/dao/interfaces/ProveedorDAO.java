package aledep.dao.interfaces;

import aledep.model.Proveedor;
import java.util.List;

public interface ProveedorDAO {
    void saveProveedor(Proveedor proveedor);
    void updateProveedor(Proveedor proveedor);
    void deleteProveedor(Integer id);
    void desactivarProveedor(Integer id);
    List<Proveedor> getAllProveedores();
    Proveedor getProveedorById(Integer id);
    List<Proveedor> getProveedoresActivos();
}
