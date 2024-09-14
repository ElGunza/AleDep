package aledep.dao.interfaces;

import aledep.model.VentaDetalle;

import java.util.List;

public interface VentaDetalleDAO {
    void saveVentaDetalle(VentaDetalle ventaDetalle);

    void updateVentaDetalle(VentaDetalle ventaDetalle);

    void deleteVentaDetalle(Integer id);

    List<VentaDetalle> getVentaDetallesByVentaId(Integer ventaId);
}
