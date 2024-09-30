package aledep.dao.impl;

import aledep.dao.interfaces.CompraDetalleDAO;
import aledep.model.CompraDetalle;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompraDetalleDAOImpl implements CompraDetalleDAO {

    @Override
    public void saveCompraDetalle(CompraDetalle compraDetalle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(compraDetalle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando el detalle de compra: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateCompraDetalle(CompraDetalle compraDetalle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(compraDetalle);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando el detalle de compra: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCompraDetalle(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CompraDetalle compraDetalle = session.get(CompraDetalle.class, id);
            if (compraDetalle != null) {
                session.delete(compraDetalle);
                transaction.commit();
            } else {
                throw new RuntimeException("Detalle de compra no encontrado para eliminar con ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando el detalle de compra: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CompraDetalle> getCompraDetallesByCompraId(Integer compraId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM CompraDetalle WHERE compra.idCompra = :compraId", CompraDetalle.class)
                    .setParameter("compraId", compraId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo los detalles de la compra con ID: " + compraId, e);
        }
    }
}
