package aledep.dao.impl;

import aledep.dao.interfaces.CategoriaDAO;
import aledep.model.Categoria;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public void saveCategoria(Categoria categoria) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(categoria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error guardando la categoría", e);
        }
    }

    @Override
    public void updateCategoria(Categoria categoria) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(categoria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error actualizando la categoría", e);
        }
    }

    @Override
    public void deleteCategoria(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Categoria categoria = session.get(Categoria.class, id);
            if (categoria != null) {
                session.delete(categoria);
                transaction.commit();
            } else {
                throw new RuntimeException("Categoría no encontrada para eliminar");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error eliminando la categoría", e);
        }
    }

    @Override
    public void desactivarCategoria(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int updatedEntities = session.createQuery("update Categoria set activo = false where idCategoria = :id")
                    .setParameter("id", id).executeUpdate();
            if (updatedEntities == 0) {
                throw new RuntimeException("Categoría no encontrada para desactivar");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error desactivando la categoría", e);
        }
    }

    @Override
    public List<Categoria> getAllCategorias() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Categoria", Categoria.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo todas las categorías", e);
        }
    }

    @Override
    public Categoria getCategoriaById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Categoria.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo la categoría por ID", e);
        }
    }

    @Override
    public List<Categoria> getCategoriasActivas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Categoria WHERE activo = true", Categoria.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo categorías activas", e);
        }
    }
}
