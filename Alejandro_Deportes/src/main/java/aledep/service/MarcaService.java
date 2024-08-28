package aledep.service;

import aledep.dao.MarcaDAO;
import aledep.model.Marca;

import java.util.List;

public class MarcaService {
    private MarcaDAO marcaDAO = new MarcaDAO();

    public void saveMarca(Marca marca) {
        // AGREGAR VALIDACIONES
        marcaDAO.saveMarca(marca);
    }

    public List<Marca> getAllMarcas() {
        return marcaDAO.getAllMarcas();
    }

    public Marca getMarcaById(Integer id) {
        return marcaDAO.getMarcaById(id);
    }

    public void updateMarca(Marca marca) {
        // AGREGAR VALIDACIONES
        marcaDAO.updateMarca(marca);
    }

    public void deleteMarca(Integer id) {
        marcaDAO.deleteMarca(id);
    }
}
