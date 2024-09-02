package aledep.service;

import aledep.dao.impl.MetodoPagoDAOImpl;
import aledep.dao.interfaces.MetodoPagoDAO;
import aledep.model.MetodoPago;

import java.util.List;

public class MetodoPagoService {

    private final MetodoPagoDAO metodoPagoDAO;

    // Constructor que inyecta la implementación de MetodoPagoDAO
    public MetodoPagoService() {
        this.metodoPagoDAO = new MetodoPagoDAOImpl(); // O puedes usar inyección de dependencias
    }

    public void saveMetodoPago(MetodoPago metodoPago) {
        // AGREGAR VALIDACIONES
        metodoPagoDAO.saveMetodoPago(metodoPago);
    }

    public void updateMetodoPago(MetodoPago metodoPago) {
        // AGREGAR VALIDACIONES
        metodoPagoDAO.updateMetodoPago(metodoPago);
    }

    public void deleteMetodoPago(Integer id) {
        metodoPagoDAO.deleteMetodoPago(id);
    }
    
    public void desactivarMetodoPago(Integer id) {
        metodoPagoDAO.desactivarMetodoPago(id);
    }

    public List<MetodoPago> getAllMetodosPago() {
        return metodoPagoDAO.getAllMetodosPago();
    }

    public MetodoPago getMetodoPagoById(Integer id) {
        return metodoPagoDAO.getMetodoPagoById(id);
    }

    public List<MetodoPago> getMetodosPagoActivos() {
        return metodoPagoDAO.getMetodosPagoActivos();
    }
}
