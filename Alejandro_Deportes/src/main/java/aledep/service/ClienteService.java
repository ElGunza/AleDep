package aledep.service;

import aledep.dao.impl.ClienteDAOImpl;
import aledep.dao.interfaces.ClienteDAO;
import aledep.dto.ClienteDTO;
import aledep.model.Cliente;

import java.text.SimpleDateFormat;
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
    
    public ClienteDTO convertirAClienteDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setCuitDni(cliente.getCuitDni());
        dto.setTipoCuitDni(cliente.getTipoCuitDni());
        dto.setNombre(cliente.getNombre());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        dto.setPreferencias(cliente.getPreferencias());
        
        if (cliente.getFechaIngreso() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			dto.setFechaIngreso(formatter.format(cliente.getFechaIngreso()));
		} else {
			dto.setFechaIngreso("Fecha no disponible");
		}
        
        if (cliente.getFechaUltimaCompra() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			dto.setFechaUltimaCompra(formatter.format(cliente.getFechaUltimaCompra()));
		} else {
			dto.setFechaUltimaCompra("Fecha no disponible");
		}
        
        dto.setCantidadCompras(cliente.getCantidadCompras());
        dto.setDeuda(cliente.getDeuda());
        dto.setActivo(cliente.getActivo());
        return dto;
    }
    
}
