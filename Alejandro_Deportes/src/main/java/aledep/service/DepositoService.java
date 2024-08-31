package aledep.service;

import aledep.dao.interfaces.DepositoDAO;
import aledep.dao.impl.DepositoDAOImpl;
import aledep.model.Deposito;

import java.util.List;

public class DepositoService {

    private final DepositoDAO depositoDAO;

    // Constructor que inyecta la implementación de DepositoDAO
    public DepositoService() {
        this.depositoDAO = new DepositoDAOImpl(); // O puedes usar inyección de dependencias
    }

    public void saveDeposito(Deposito deposito) {
        // AGREGAR VALIDACIONES
        depositoDAO.saveDeposito(deposito);
    }

    public void updateDeposito(Deposito deposito) {
        // AGREGAR VALIDACIONES
        depositoDAO.updateDeposito(deposito);
    }

    public void deleteDeposito(Integer id) {
        depositoDAO.deleteDeposito(id);
    }
    
    public void desactivarDeposito(Integer id) {
        depositoDAO.desactivarDeposito(id);
    }

    public List<Deposito> getAllDepositos() {
        return depositoDAO.getAllDepositos();
    }

    public Deposito getDepositoById(Integer id) {
        return depositoDAO.getDepositoById(id);
    }

    public List<Deposito> getDepositosActivos() {
        return depositoDAO.getDepositosActivos();
    }
}
