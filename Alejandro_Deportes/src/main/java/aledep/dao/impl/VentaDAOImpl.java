package aledep.dao.impl;

import aledep.dao.interfaces.VentaDAO;
import aledep.model.Venta;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VentaDAOImpl implements VentaDAO {

    @Override
    public void saveVenta(Venta venta) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(venta);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando la venta", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateVenta(Venta venta) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(venta);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando la venta", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteVenta(Integer id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Venta venta = session.get(Venta.class, id);
            if (venta != null) {
                session.delete(venta);
                transaction.commit();
            } else {
                throw new RuntimeException("Venta no encontrada para eliminar");
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando la venta", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void desactivarVenta(Integer id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            int updatedEntities = session.createQuery("update Venta set activo = false where idVenta = :id")
                    .setParameter("id", id).executeUpdate();
            if (updatedEntities == 0) {
                throw new RuntimeException("Venta no encontrada para desactivar");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error desactivando la venta", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Venta> getAllVentas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Venta", Venta.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo todas las ventas", e);
        }
    }

    @Override
    public Venta getVentaById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Venta.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo la venta por ID", e);
        }
    }

    @Override
    public List<Venta> getVentasActivas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Venta WHERE activo = true", Venta.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo ventas activas", e);
        }
    }
}
