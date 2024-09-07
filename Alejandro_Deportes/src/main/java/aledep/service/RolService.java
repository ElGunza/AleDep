package aledep.service;

import aledep.dao.interfaces.RolDAO;
import aledep.dao.impl.RolDAOImpl;
import aledep.model.Rol;

import java.util.List;

public class RolService {
	private final RolDAO rolDAO;

	public RolService() {
		this.rolDAO = new RolDAOImpl();
	}

	public void saveRol(Rol rol) {
		validarRol(rol);
		rolDAO.saveRol(rol);
	}

	public void updateRol(Rol rol) {
		validarRol(rol);
		rolDAO.updateRol(rol);
	}

	public void deleteRol(Integer id) {
		rolDAO.deleteRol(id);
	}

	public List<Rol> getAllRoles() {
		return rolDAO.getAllRoles();
	}

	public List<Rol> getActivosRoles() {
		return rolDAO.getActivosRoles();
	}

	public Rol getRolById(Integer id) {
		return rolDAO.getRolById(id);
	}

	private void validarRol(Rol rol) {
		if (rol.getNombre() == null || rol.getNombre().isEmpty()) {
			throw new IllegalArgumentException("El nombre del rol no puede estar vacío.");
		}
	}
}
