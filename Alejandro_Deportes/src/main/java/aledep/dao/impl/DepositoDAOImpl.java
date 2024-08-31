package aledep.dao.impl;

import aledep.dao.interfaces.DepositoDAO;
import aledep.model.Deposito;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DepositoDAOImpl implements DepositoDAO {

    @Override
    public void saveDeposito(Deposito deposito) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(deposito);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando el depósito", e);
        }
    }

    @Override
    public void updateDeposito(Deposito deposito) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(deposito);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando el depósito", e);
        }
    }

    @Override
    public void deleteDeposito(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Deposito deposito = session.get(Deposito.class, id);
            if (deposito != null) {
                session.delete(deposito);
                transaction.commit();
            } else {
                throw new RuntimeException("Depósito no encontrado para eliminar");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando el depósito", e);
        }
    }

    @Override
    public void desactivarDeposito(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            int updatedEntities = session.createQuery("update Deposito set activo = false where idDeposito = :id")
                    .setParameter("id", id).executeUpdate();

            if (updatedEntities == 0) {
                throw new RuntimeException("Depósito no encontrado para desactivar");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error desactivando el depósito", e);
        }
    }

    @Override
    public List<Deposito> getAllDepositos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Deposito", Deposito.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo todos los depósitos", e);
        }
    }

    @Override
    public Deposito getDepositoById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Deposito.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo el depósito por ID", e);
        }
    }

    @Override
    public List<Deposito> getDepositosActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Deposito WHERE activo = true", Deposito.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo depósitos activos", e);
        }
    }
}
