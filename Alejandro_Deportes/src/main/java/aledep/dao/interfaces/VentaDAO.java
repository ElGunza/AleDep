package aledep.dao.interfaces;

import aledep.model.Venta;

import java.util.Date;
import java.util.List;

public interface VentaDAO {
    void saveVenta(Venta Venta);
    void updateVenta(Venta Venta);
    void deleteVenta(Integer id);
    void desactivarVenta(Integer id);
    List<Venta> getAllVentas();
    Venta getVentaById(Integer id);
    List<Venta> getVentasActivas();
    
    List<Venta> obtenerReporteVentas(Date fechaInicio, Date fechaFin);
    
}
