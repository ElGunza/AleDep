package aledep.service;

import aledep.dao.CategoriaDAO;
import aledep.model.Categoria;

import java.util.List;

public class CategoriaService {
    private CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void saveCategoria(Categoria categoria) {
        // AGREGAR VALIDACIONES
        categoriaDAO.saveCategoria(categoria);
    }

    public List<Categoria> getAllCategorias() {
        return categoriaDAO.getAllCategorias();
    }

    public Categoria getCategoriaById(Integer id) {
        return categoriaDAO.getCategoriaById(id);
    }

    public void updateCategoria(Categoria categoria) {
        // AGREGAR VALIDACIONES
        categoriaDAO.updateCategoria(categoria);
    }

    public void deleteCategoria(Integer id) {
        categoriaDAO.deleteCategoria(id);
    }
}
