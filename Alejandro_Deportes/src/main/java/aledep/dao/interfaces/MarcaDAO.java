package aledep.dao.interfaces;

import aledep.model.Marca;
import java.util.List;

public interface MarcaDAO {
    void saveMarca(Marca marca);
    void updateMarca(Marca marca);
    void deleteMarca(Integer id);
    void desactivarMarca(Integer id);
    List<Marca> getAllMarcas();
    Marca getMarcaById(Integer id);
    List<Marca> getMarcasActivas();
}
