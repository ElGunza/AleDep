package aledep.service;

import aledep.dao.DepositoDAO;
import aledep.model.Deposito;

import java.util.List;

public class DepositoService {
    private DepositoDAO depositoDAO = new DepositoDAO();

    public void saveDeposito(Deposito deposito) {
        // AGREGAR VALIDACIONES
        depositoDAO.saveDeposito(deposito);
    }

    public List<Deposito> getAllDepositos() {
        return depositoDAO.getAllDepositos();
    }

    public Deposito getDepositoById(Integer id) {
        return depositoDAO.getDepositoById(id);
    }

    public void updateDeposito(Deposito deposito) {
        // AGREGAR VALIDACIONES
        depositoDAO.updateDeposito(deposito);
    }

    public void deleteDeposito(Integer id) {
        depositoDAO.deleteDeposito(id);
    }
}
