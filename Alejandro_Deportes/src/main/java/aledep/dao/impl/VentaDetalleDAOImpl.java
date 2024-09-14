package aledep.dao.impl;

import aledep.dao.interfaces.VentaDetalleDAO;
import aledep.model.VentaDetalle;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VentaDetalleDAOImpl implements VentaDetalleDAO {

    @Override
    public void saveVentaDetalle(VentaDetalle ventaDetalle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(ventaDetalle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando el detalle de venta: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateVentaDetalle(VentaDetalle ventaDetalle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(ventaDetalle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando el detalle de venta: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteVentaDetalle(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            VentaDetalle ventaDetalle = session.get(VentaDetalle.class, id);
            if (ventaDetalle != null) {
                session.delete(ventaDetalle);
                transaction.commit();
            } else {
                throw new RuntimeException("Detalle de venta no encontrado para eliminar con ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando el detalle de venta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<VentaDetalle> getVentaDetallesByVentaId(Integer ventaId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM VentaDetalle WHERE venta.idVenta = :ventaId", VentaDetalle.class)
                    .setParameter("ventaId", ventaId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo los detalles de la venta con ID: " + ventaId, e);
        }
    }
}
