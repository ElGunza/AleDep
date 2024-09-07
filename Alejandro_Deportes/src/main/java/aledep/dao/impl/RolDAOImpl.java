package aledep.dao.impl;

import aledep.dao.interfaces.RolDAO;
import aledep.model.Rol;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RolDAOImpl implements RolDAO {

	@Override
	public void saveRol(Rol rol) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(rol);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error guardando el rol", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void updateRol(Rol rol) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.update(rol);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error actualizando el rol", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public void deleteRol(Integer id) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Rol rol = session.get(Rol.class, id);
			if (rol != null) {
				session.delete(rol);
				transaction.commit();
			} else {
				throw new RuntimeException("Rol no encontrado para eliminar");
			}
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error eliminando el rol", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<Rol> getAllRoles() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Rol", Rol.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo todos los roles", e);
		}
	}

	@Override
	public Rol getRolById(Integer id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Rol.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo el rol por ID", e);
		}
	}

	@Override
	public List<Rol> getActivosRoles() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Rol WHERE activo = true", Rol.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo Rols activos", e);
		}
	}

	@Override
	public void desactivarRol(Integer id) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			int updatedEntities = session.createQuery("update Rol set activo = false where idRol = :id")
					.setParameter("id", id).executeUpdate();
			if (updatedEntities == 0) {
				throw new RuntimeException("Rol no encontrado para desactivar");
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error desactivando el rol", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
