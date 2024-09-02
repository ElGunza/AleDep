package aledep.dao.impl;

import aledep.dao.interfaces.MetodoPagoDAO;
import aledep.model.MetodoPago;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MetodoPagoDAOImpl implements MetodoPagoDAO {

    @Override
    public void saveMetodoPago(MetodoPago metodoPago) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(metodoPago);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando el método de pago", e);
        }
    }

    @Override
    public void updateMetodoPago(MetodoPago metodoPago) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(metodoPago);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando el método de pago", e);
        }
    }

    @Override
    public void deleteMetodoPago(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            MetodoPago metodoPago = session.get(MetodoPago.class, id);
            if (metodoPago != null) {
                session.delete(metodoPago);
                transaction.commit();
            } else {
                throw new RuntimeException("Método de pago no encontrado para eliminar");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando el método de pago", e);
        }
    }

    @Override
    public void desactivarMetodoPago(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int updatedEntities = session.createQuery("update MetodoPago set activo = false where idMetPago = :id")
                    .setParameter("id", id).executeUpdate();
            if (updatedEntities == 0) {
                throw new RuntimeException("Método de pago no encontrado para desactivar");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error desactivando el método de pago", e);
        }
    }

    @Override
    public List<MetodoPago> getAllMetodosPago() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from MetodoPago", MetodoPago.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo todos los métodos de pago", e);
        }
    }

    @Override
    public MetodoPago getMetodoPagoById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(MetodoPago.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo el método de pago por ID", e);
        }
    }

    @Override
    public List<MetodoPago> getMetodosPagoActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MetodoPago WHERE activo = true", MetodoPago.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo métodos de pago activos", e);
        }
    }
}
