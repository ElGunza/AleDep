package aledep.service;

import aledep.dao.impl.ClienteDAOImpl;
import aledep.dao.interfaces.ClienteDAO;
import aledep.model.Cliente;

import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    // Constructor que inyecta la implementación de ClienteDAO
    public ClienteService() {
        this.clienteDAO = new ClienteDAOImpl(); // O puedes usar inyección de dependencias
    }

    public void saveCliente(Cliente cliente) {
        // AGREGAR VALIDACIONES
        clienteDAO.saveCliente(cliente);
    }

    public void updateCliente(Cliente cliente) {
        // AGREGAR VALIDACIONES
        clienteDAO.updateCliente(cliente);
    }

    public void deleteCliente(Integer id) {
        clienteDAO.deleteCliente(id);
    }
    
    public void desactivarCliente(Integer id) {
        clienteDAO.desactivarCliente(id);
    }

    public List<Cliente> getAllClientes() {
        return clienteDAO.getAllClientes();
    }

    public Cliente getClienteById(Integer id) {
        return clienteDAO.getClienteById(id);
    }

    public List<Cliente> getClientesActivos() {
        return clienteDAO.getClientesActivos();
    }
}
