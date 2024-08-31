package aledep.dao.impl;

import aledep.dao.interfaces.MarcaDAO;
import aledep.model.Marca;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MarcaDAOImpl implements MarcaDAO {

    @Override
    public void saveMarca(Marca marca) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(marca);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando la marca", e);
        }
    }

    @Override
    public void updateMarca(Marca marca) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(marca);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando la marca", e);
        }
    }

    @Override
    public void deleteMarca(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Marca marca = session.get(Marca.class, id);
            if (marca != null) {
                session.delete(marca);
                transaction.commit();
            } else {
                throw new RuntimeException("Marca no encontrada para eliminar");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando la marca", e);
        }
    }

    @Override
    public void desactivarMarca(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int updatedEntities = session.createQuery("update Marca set activo = false where idMarca = :id")
                    .setParameter("id", id).executeUpdate();
            if (updatedEntities == 0) {
                throw new RuntimeException("Marca no encontrada para desactivar");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error desactivando la marca", e);
        }
    }

    @Override
    public List<Marca> getAllMarcas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Marca", Marca.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo todas las marcas", e);
        }
    }

    @Override
    public Marca getMarcaById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Marca.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo la marca por ID", e);
        }
    }

    @Override
    public List<Marca> getMarcasActivas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Marca WHERE activo = true", Marca.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo marcas activas", e);
        }
    }
}
