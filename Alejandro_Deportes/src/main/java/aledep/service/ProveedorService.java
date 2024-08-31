package aledep.service;

import aledep.dao.interfaces.ProveedorDAO;
import aledep.dao.impl.ProveedorDAOImpl;
import aledep.dto.ProveedorDTO;
import aledep.model.Proveedor;

import java.util.List;

public class ProveedorService {
	private final ProveedorDAO proveedorDAO;

	public ProveedorService() {
		this.proveedorDAO = new ProveedorDAOImpl(); // Inyección de la implementación concreta
	}

	public void saveProveedor(Proveedor proveedor) {
		validarProveedor(proveedor);
		proveedorDAO.saveProveedor(proveedor);
	}

	public void updateProveedor(Proveedor proveedor) {
		validarProveedor(proveedor);
		proveedorDAO.updateProveedor(proveedor);
	}

	public void deleteProveedor(Integer id) {
		proveedorDAO.deleteProveedor(id);
	}

	public void desactivarProveedor(Integer id) {
		proveedorDAO.desactivarProveedor(id);
	}

	public List<Proveedor> getAllProveedores() {
		return proveedorDAO.getAllProveedores();
	}

	public Proveedor getProveedorById(Integer id) {
		return proveedorDAO.getProveedorById(id);
	}

	public List<Proveedor> getProveedoresActivos() {
		return proveedorDAO.getProveedoresActivos();
	}

	public ProveedorDTO convertirAProveedorDTO(Proveedor proveedor) {
		return convertirProveedorAProveedorDTO(proveedor);
	}

	private void validarProveedor(Proveedor proveedor) {
		if (proveedor.getNombre() == null || proveedor.getNombre().isEmpty()) {
			throw new IllegalArgumentException("El nombre del proveedor no puede estar vacío.");
		}
	}

	// Método privado para convertir Proveedor a ProveedorDTO
	private ProveedorDTO convertirProveedorAProveedorDTO(Proveedor proveedor) {
		ProveedorDTO dto = new ProveedorDTO();
		dto.setIdProveedor(proveedor.getIdProveedor());
		dto.setNombre(proveedor.getNombre());
		dto.setContacto(proveedor.getContacto());
		dto.setCuitDni(proveedor.getCuitDni());
		dto.setTipoCuitDni(proveedor.getTipoCuitDni());
		dto.setActivo(proveedor.getActivo());
		return dto;
	}
}
