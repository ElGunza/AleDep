package aledep.dao.impl;

import aledep.dao.interfaces.ClienteDAO;
import aledep.model.Cliente;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void saveCliente(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando el cliente", e);
        }
    }

    @Override
    public void updateCliente(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando el cliente", e);
        }
    }

    @Override
    public void deleteCliente(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Cliente cliente = session.get(Cliente.class, id);
            if (cliente != null) {
                session.delete(cliente);
                transaction.commit();
            } else {
                throw new RuntimeException("Cliente no encontrado para eliminar");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando el cliente", e);
        }
    }

    @Override
    public void desactivarCliente(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int updatedEntities = session.createQuery("update Cliente set activo = false where idCliente = :id")
                    .setParameter("id", id).executeUpdate();
            if (updatedEntities == 0) {
                throw new RuntimeException("Cliente no encontrado para desactivar");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error desactivando el cliente", e);
        }
    }

    @Override
    public List<Cliente> getAllClientes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Cliente", Cliente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo todos los clientes", e);
        }
    }

    @Override
    public Cliente getClienteById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Cliente.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo el cliente por ID", e);
        }
    }

    @Override
    public List<Cliente> getClientesActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Cliente WHERE activo = true", Cliente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo clientes activos", e);
        }
    }
}
