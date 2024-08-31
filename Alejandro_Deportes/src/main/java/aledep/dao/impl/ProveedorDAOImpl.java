package aledep.dao.impl;

import aledep.dao.interfaces.ProveedorDAO;
import aledep.model.Proveedor;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProveedorDAOImpl implements ProveedorDAO {

    @Override
    public void saveProveedor(Proveedor proveedor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(proveedor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando el proveedor", e);
        }
    }

    @Override
    public void updateProveedor(Proveedor proveedor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(proveedor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando el proveedor", e);
        }
    }

    @Override
    public void deleteProveedor(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Proveedor proveedor = session.get(Proveedor.class, id);
            if (proveedor != null) {
                session.delete(proveedor);
                transaction.commit();
            } else {
                throw new RuntimeException("Proveedor no encontrado para eliminar");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando el proveedor", e);
        }
    }

    @Override
    public void desactivarProveedor(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int updatedEntities = session.createQuery("update Proveedor set activo = false where idProveedor = :id")
                    .setParameter("id", id).executeUpdate();
            if (updatedEntities == 0) {
                throw new RuntimeException("Proveedor no encontrado para desactivar");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error desactivando el proveedor", e);
        }
    }

    @Override
    public List<Proveedor> getAllProveedores() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Proveedor", Proveedor.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo todos los proveedores", e);
        }
    }

    @Override
    public Proveedor getProveedorById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Proveedor.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo el proveedor por ID", e);
        }
    }

    @Override
    public List<Proveedor> getProveedoresActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Proveedor WHERE activo = true", Proveedor.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo proveedores activos", e);
        }
    }
}
