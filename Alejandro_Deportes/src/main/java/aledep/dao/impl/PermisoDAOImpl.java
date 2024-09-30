package aledep.dao.impl;

import aledep.dao.interfaces.PermisoDAO;
import aledep.model.Permiso;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class PermisoDAOImpl implements PermisoDAO {

    @Override
    public void savePermiso(Permiso permiso) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(permiso);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error guardando el permiso", e);
        }
    }

    @Override
    public void updatePermiso(Permiso permiso) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(permiso);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error actualizando el permiso", e);
        }
    }

    @Override
    public void deletePermiso(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Permiso permiso = session.get(Permiso.class, id);
            if (permiso != null) {
                session.delete(permiso);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error eliminando el permiso", e);
        }
    }

    @Override
    public Permiso getPermisoById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Permiso.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo el permiso por ID", e);
        }
    }

    @Override
    public List<Permiso> getAllPermisos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Permiso", Permiso.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo todos los permisos", e);
        }
    }

    @Override
    public List<Permiso> getActivosPermisos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Permiso WHERE activo = true", Permiso.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo permisos activos", e);
        }
    }

	@Override
	public void desactivarPermiso(Integer id) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			int updatedEntities = session.createQuery("update Permiso set activo = false where idPermiso = :id")
					.setParameter("id", id).executeUpdate();
			if (updatedEntities == 0) {
				throw new RuntimeException("Permiso no encontrado para desactivar");
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error desactivando el Permiso", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
