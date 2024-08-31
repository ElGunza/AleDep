package aledep.dao.interfaces;

import aledep.model.Deposito;
import java.util.List;

public interface DepositoDAO {
    void saveDeposito(Deposito deposito);
    void updateDeposito(Deposito deposito);
    void deleteDeposito(Integer id);
    void desactivarDeposito(Integer id);
    List<Deposito> getAllDepositos();
    Deposito getDepositoById(Integer id);
    List<Deposito> getDepositosActivos();
}
