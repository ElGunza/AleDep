package aledep.dao.interfaces;

import aledep.model.CompraDetalle;
import java.util.List;

public interface CompraDetalleDAO {
    void saveCompraDetalle(CompraDetalle compraDetalle);
    void updateCompraDetalle(CompraDetalle compraDetalle);
    void deleteCompraDetalle(Integer id);
    List<CompraDetalle> getCompraDetallesByCompraId(Integer compraId);
}
