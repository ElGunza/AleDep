package aledep.dao.interfaces;

import aledep.model.MetodoPago;
import java.util.List;

public interface MetodoPagoDAO {
    void saveMetodoPago(MetodoPago metodoPago);
    void updateMetodoPago(MetodoPago metodoPago);
    void deleteMetodoPago(Integer id);
    void desactivarMetodoPago(Integer id);
    List<MetodoPago> getAllMetodosPago();
    MetodoPago getMetodoPagoById(Integer id);
    List<MetodoPago> getMetodosPagoActivos();
}
