package aledep.service;

import aledep.dao.interfaces.UsuarioDAO;
import aledep.dao.impl.UsuarioDAOImpl;
import aledep.dto.UsuarioDTO;
import aledep.model.Usuario;

import java.util.List;

public class UsuarioService {
	private final UsuarioDAO usuarioDAO;

	public UsuarioService() {
		this.usuarioDAO = new UsuarioDAOImpl();
	}

	public void saveUsuario(Usuario usuario) {
		validarUsuario(usuario);
		usuarioDAO.saveUsuario(usuario);
	}

	public void updateUsuario(Usuario usuario) {
		validarUsuario(usuario);
		usuarioDAO.updateUsuario(usuario);
	}

	public void deleteUsuario(Integer id) {
		usuarioDAO.deleteUsuario(id);
	}

	public void desactivarUsuario(Integer id) {
		usuarioDAO.desactivarUsuario(id);
	}

	public Usuario getUsuarioById(Integer id) {
		return usuarioDAO.getUsuarioById(id);
	}

	public Usuario getUsuarioByEmail(String email) {
		return usuarioDAO.getUsuarioByEmail(email);
	}

	public List<Usuario> getUsuariosActivos() {
		return usuarioDAO.getUsuariosActivos();
	}

	public List<Usuario> getAllUsuarios() {
		return usuarioDAO.getAllUsuarios();
	}

	public UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
		return convertirUsuarioAUsuarioDTO(usuario);
	}

	private void validarUsuario(Usuario usuario) {
		if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
			throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
		}
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			throw new IllegalArgumentException("El email del usuario no puede estar vacío.");
		}
	}

	private UsuarioDTO convertirUsuarioAUsuarioDTO(Usuario usuario) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setIdUsuario(usuario.getIdUsuario());
		dto.setNombre(usuario.getNombre());
		dto.setEmail(usuario.getEmail());
		dto.setActivo(usuario.getActivo());

		if (usuario.getRol() != null) {
			dto.setIdRol(usuario.getRol().getIdRol());
			dto.setRol(usuario.getRol().getNombre());
		} else {
			dto.setIdRol(null);
			dto.setRol("Sin rol");
		}

		return dto;
	}
}
