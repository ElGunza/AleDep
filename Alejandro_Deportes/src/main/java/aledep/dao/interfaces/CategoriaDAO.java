package aledep.dao.interfaces;

import aledep.model.Categoria;
import java.util.List;

public interface CategoriaDAO {
    void saveCategoria(Categoria categoria);
    void updateCategoria(Categoria categoria);
    void deleteCategoria(Integer id);
    void desactivarCategoria(Integer id);
    List<Categoria> getAllCategorias();
    Categoria getCategoriaById(Integer id);
    List<Categoria> getCategoriasActivas();
}
