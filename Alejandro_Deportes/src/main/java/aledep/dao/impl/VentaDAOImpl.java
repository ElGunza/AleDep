package aledep.dao.impl;

import aledep.dao.interfaces.VentaDAO;
import aledep.model.Venta;
import aledep.model.VentaDetalle;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VentaDAOImpl implements VentaDAO {

	@Override
	public void saveVenta(Venta venta) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			// Guardar la venta
			session.save(venta);

			// Guardar los detalles
			for (VentaDetalle detalle : venta.getDetalles()) {
				detalle.setVenta(venta); // Establecer la relación correcta
				session.save(detalle);
			}

			// Commit de la transacción
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error guardando la venta: " + e.getMessage(), e);
		}
	}

	@Override
	public void updateVenta(Venta venta) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			Venta ventaExistente = session.get(Venta.class, venta.getIdVenta());

			if (ventaExistente == null) {
				throw new RuntimeException("Venta no encontrada con ID: " + venta.getIdVenta());
			}

			// 2. Eliminar los detalles antiguos que ya no están en la nueva lista
			for (VentaDetalle detalleExistente : ventaExistente.getDetalles()) {
				if (!venta.getDetalles().contains(detalleExistente)) {
					session.delete(detalleExistente);
				}
			}

			// 3. Actualizar o añadir los nuevos detalles
			for (VentaDetalle nuevoDetalle : venta.getDetalles()) {
				nuevoDetalle.setVenta(venta);
				session.saveOrUpdate(nuevoDetalle);
			}

			// 4. Actualizar la venta
			session.merge(venta);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error actualizando la venta: " + e.getMessage(), e);
		}
	}

	@Override
	public void deleteVenta(Integer id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Venta venta = session.get(Venta.class, id);
			if (venta != null) {
				session.delete(venta);
				transaction.commit();
			} else {
				throw new RuntimeException("Venta no encontrada para eliminar con ID: " + id);
			}
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error eliminando la venta: " + e.getMessage(), e);
		}
	}

	@Override
	public void desactivarVenta(Integer id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			int updatedEntities = session.createQuery("update Venta set activo = false where idVenta = :id")
					.setParameter("id", id).executeUpdate();
			if (updatedEntities == 0) {
				throw new RuntimeException("Venta no encontrada para desactivar con ID: " + id);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error desactivando la venta: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Venta> getAllVentas() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// Usamos DISTINCT para evitar duplicados al hacer JOIN FETCH con detalles
			return session.createQuery("SELECT DISTINCT v FROM Venta v LEFT JOIN FETCH v.detalles", Venta.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo todas las ventas", e);
		}
	}

	@Override
	public Venta getVentaById(Integer id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Venta venta = session
					.createQuery("FROM Venta v LEFT JOIN FETCH v.detalles WHERE v.idVenta = :id", Venta.class)
					.setParameter("id", id).uniqueResult();
			if (venta == null) {
				throw new RuntimeException("Venta no encontrada con ID: " + id);
			}
			return venta;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo la venta por ID: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Venta> getVentasActivas() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Venta WHERE activo = true", Venta.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo ventas activas: " + e.getMessage(), e);
		}
	}
}
