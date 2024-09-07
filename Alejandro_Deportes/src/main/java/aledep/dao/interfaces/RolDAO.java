package aledep.dao.interfaces;

import aledep.model.Rol;
import java.util.List;

public interface RolDAO {
	void saveRol(Rol rol);

	void updateRol(Rol rol);

	void deleteRol(Integer id);

	void desactivarRol(Integer id);

	List<Rol> getAllRoles();

	Rol getRolById(Integer id);

	List<Rol> getActivosRoles();
}
