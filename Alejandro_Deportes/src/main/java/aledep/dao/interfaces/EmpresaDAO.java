package aledep.dao.interfaces;

import aledep.model.Empresa;

public interface EmpresaDAO {
    void saveEmpresa(Empresa empresa);

    void updateEmpresa(Empresa empresa);

    Empresa obtenerEmpresaUnica();
}
