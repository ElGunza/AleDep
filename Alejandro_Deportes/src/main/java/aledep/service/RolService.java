package aledep.service;

import aledep.dao.interfaces.RolDAO;
import aledep.dto.RolDTO;
import aledep.dao.impl.RolDAOImpl;
import aledep.model.Permiso;
import aledep.model.Rol;
//import aledep.dto.RolDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	public void desactivarRol(Integer id) {
		rolDAO.desactivarRol(id);
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

	public void actualizarPermisos(Rol rol, Set<Permiso> nuevosPermisos) {
		rol.setPermisos(nuevosPermisos);
		rolDAO.updateRol(rol);
	}

	public RolDTO convertirArolDTO(Rol rol) {
		RolDTO rolDTO = new RolDTO();

		rolDTO.setIdRol(rol.getIdRol());
		rolDTO.setNombre(rol.getNombre());
		rolDTO.setDescripcion(rol.getDescripcion());

		Set<String> permisosDTO = rol.getPermisos().stream().map(Permiso::getNombre) 
				.collect(Collectors.toSet());
		rolDTO.setPermisos(permisosDTO);

		return rolDTO;
	}

}
