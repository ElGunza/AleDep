package aledep.dao.interfaces;

import aledep.model.Compra;
import java.util.List;

public interface CompraDAO {
    void saveCompra(Compra Compra);
    void updateCompra(Compra Compra);
    void deleteCompra(Integer id);
    void desactivarCompra(Integer id);
    List<Compra> getAllCompras();
    Compra getCompraById(Integer id);
    List<Compra> getComprasActivas();
}
