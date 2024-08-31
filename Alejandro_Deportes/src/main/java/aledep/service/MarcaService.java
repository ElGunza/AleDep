package aledep.service;

import aledep.dao.interfaces.MarcaDAO;
import aledep.dao.impl.MarcaDAOImpl;
import aledep.dto.MarcaDTO;
import aledep.model.Marca;

import java.util.List;

public class MarcaService {
    private final MarcaDAO marcaDAO;

    public MarcaService() {
        this.marcaDAO = new MarcaDAOImpl(); // Inyección de la implementación concreta
    }

    public void saveMarca(Marca marca) {
        validarMarca(marca);
        marcaDAO.saveMarca(marca);
    }

    public void updateMarca(Marca marca) {
        validarMarca(marca);
        marcaDAO.updateMarca(marca);
    }

    public void deleteMarca(Integer id) {
        marcaDAO.deleteMarca(id);
    }

    public void desactivarMarca(Integer id) {
        marcaDAO.desactivarMarca(id);
    }

    public List<Marca> getAllMarcas() {
        return marcaDAO.getAllMarcas();
    }

    public Marca getMarcaById(Integer id) {
        return marcaDAO.getMarcaById(id);
    }

    public List<Marca> getMarcasActivas() {
        return marcaDAO.getMarcasActivas();
    }

    public MarcaDTO convertirAMarcaDTO(Marca marca) {
        return convertirMarcaAMarcaDTO(marca);
    }

    private void validarMarca(Marca marca) {
        if (marca.getNombre() == null || marca.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca no puede estar vacío.");
        }
    }

    // Método privado para convertir Marca a MarcaDTO
    private MarcaDTO convertirMarcaAMarcaDTO(Marca marca) {
        MarcaDTO dto = new MarcaDTO();
        dto.setIdMarca(marca.getIdMarca());
        dto.setNombre(marca.getNombre());
        dto.setDescripcion(marca.getDescripcion());
        dto.setActivo(marca.getActivo());
        return dto;
    }
}
