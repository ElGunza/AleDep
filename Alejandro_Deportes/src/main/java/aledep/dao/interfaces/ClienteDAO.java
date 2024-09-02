package aledep.dao.interfaces;

import aledep.model.Cliente;
import java.util.List;

public interface ClienteDAO {
    void saveCliente(Cliente cliente);
    void updateCliente(Cliente cliente);
    void deleteCliente(Integer id);
    void desactivarCliente(Integer id);
    List<Cliente> getAllClientes();
    Cliente getClienteById(Integer id);
    List<Cliente> getClientesActivos();
    
}
