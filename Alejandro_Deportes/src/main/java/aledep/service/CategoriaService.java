package aledep.service;

import aledep.dao.interfaces.CategoriaDAO;
import aledep.dao.impl.CategoriaDAOImpl;
import aledep.dto.CategoriaDTO;
import aledep.model.Categoria;

import java.util.List;

public class CategoriaService {
	private final CategoriaDAO categoriaDAO;

	public CategoriaService() {
		this.categoriaDAO = new CategoriaDAOImpl(); // Inyección de la implementación concreta
	}

	public void saveCategoria(Categoria categoria) {
		validarCategoria(categoria);
		categoriaDAO.saveCategoria(categoria);
	}

	public void updateCategoria(Categoria categoria) {
		validarCategoria(categoria);
		categoriaDAO.updateCategoria(categoria);
	}

	public void deleteCategoria(Integer id) {
		categoriaDAO.deleteCategoria(id);
	}

	public void desactivarCategoria(Integer id) {
		categoriaDAO.desactivarCategoria(id);
	}

	public List<Categoria> getAllCategorias() {
		return categoriaDAO.getAllCategorias();
	}

	public Categoria getCategoriaById(Integer id) {
		return categoriaDAO.getCategoriaById(id);
	}

	public List<Categoria> getCategoriasActivas() {
		return categoriaDAO.getCategoriasActivas();
	}

	public CategoriaDTO convertirACategoriaDTO(Categoria categoria) {
		return convertirCategoriaACategoriaDTO(categoria);
	}

	private void validarCategoria(Categoria categoria) {
		if (categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
		}
	}

	// Método privado para convertir Categoria a CategoriaDTO
	private CategoriaDTO convertirCategoriaACategoriaDTO(Categoria categoria) {
		CategoriaDTO dto = new CategoriaDTO();
		dto.setIdCategoria(categoria.getIdCategoria());
		dto.setNombre(categoria.getNombre());
		dto.setActivo(categoria.getActivo());
		return dto;
	}
}
