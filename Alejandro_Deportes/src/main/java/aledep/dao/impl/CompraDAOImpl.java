package aledep.dao.impl;

import aledep.dao.interfaces.CompraDAO;
import aledep.model.Compra;
import aledep.model.CompraDetalle;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompraDAOImpl implements CompraDAO {

	@Override
	public void saveCompra(Compra compra) {
		Transaction transaction = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			session.save(compra);
			System.out.println("SAVE COMPRA");

			for (CompraDetalle detalle : compra.getDetalles()) {
				detalle.setCompra(compra);
				System.out.println("SAVE DETALLE");
				session.save(detalle);
			}

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				try {
					transaction.rollback();
				} catch (Exception rollbackException) {
					rollbackException.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new RuntimeException("Error guardando la compra: " + e.getMessage(), e);
		}

	}

	@Override
	public void updateCompra(Compra compraExistente) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();

	        for (CompraDetalle detalleExistente : compraExistente.getDetalles()) {
	            if (!compraExistente.getDetalles().contains(detalleExistente)) {
	                session.delete(detalleExistente);
	            }
	        }

	        for (CompraDetalle nuevoDetalle : compraExistente.getDetalles()) {
	            nuevoDetalle.setCompra(compraExistente);
	            session.saveOrUpdate(nuevoDetalle);
	        }

	        session.merge(compraExistente);  

	        transaction.commit(); 
	    } catch (Exception e) {
	        if (transaction != null && transaction.isActive()) {
	            transaction.rollback();  // Realizamos rollback si ocurre algún error
	        }
	        e.printStackTrace();
	        throw new RuntimeException("Error actualizando la compra: " + e.getMessage(), e);
	    }
	}


	@Override
	public void deleteCompra(Integer id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Compra compra = session.get(Compra.class, id);
			if (compra != null) {
				session.delete(compra);
				transaction.commit();
			} else {
				throw new RuntimeException("Compra no encontrada para eliminar con ID: " + id);
			}
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error eliminando la compra: " + e.getMessage(), e);
		}
	}

	@Override
	public void desactivarCompra(Integer id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			int updatedEntities = session.createQuery("update Compra set activo = false where idCompra = :id")
					.setParameter("id", id).executeUpdate();
			if (updatedEntities == 0) {
				throw new RuntimeException("Compra no encontrada para desactivar con ID: " + id);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error desactivando la compra: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Compra> getAllCompras() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("SELECT DISTINCT c FROM Compra c LEFT JOIN FETCH c.detalles", Compra.class)
					.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo todas las compras", e);
		}
	}

	@Override
	public Compra getCompraById(Integer id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Compra compra = session
					.createQuery("FROM Compra c LEFT JOIN FETCH c.detalles WHERE c.idCompra = :id", Compra.class)
					.setParameter("id", id).uniqueResult();
			if (compra == null) {
				throw new RuntimeException("Compra no encontrada con ID: " + id);
			}
			return compra;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo la compra por ID: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Compra> getComprasActivas() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Compra WHERE activo = true", Compra.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo compras activas: " + e.getMessage(), e);
		}
	}
}
