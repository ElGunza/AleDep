package aledep.service;

import aledep.dao.impl.EmpresaDAOImpl;
import aledep.dao.interfaces.EmpresaDAO;
import aledep.model.Empresa;

public class EmpresaService {
    private final EmpresaDAO empresaDAO;

    public EmpresaService() {
        this.empresaDAO = new EmpresaDAOImpl();
    }

    public Empresa obtenerEmpresaUnica() {
        return empresaDAO.obtenerEmpresaUnica();
    }

    public void saveOrUpdateEmpresa(Empresa empresa) {
        if (empresa.getIdConfig() == null) {
            empresaDAO.saveEmpresa(empresa);
        } else {
            empresaDAO.updateEmpresa(empresa);
        }
    }
}
