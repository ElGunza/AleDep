package aledep.dao.impl;

import aledep.dao.interfaces.EmpresaDAO;
import aledep.model.Empresa;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmpresaDAOImpl implements EmpresaDAO {

    @Override
    public void saveEmpresa(Empresa empresa) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(empresa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando la empresa", e);
        }
    }

    @Override
    public void updateEmpresa(Empresa empresa) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(empresa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando la empresa", e);
        }
    }

    @Override
    public Empresa obtenerEmpresaUnica() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Empresa", Empresa.class).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo la configuración de la empresa", e);
        }
    }
}
