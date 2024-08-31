package aledep.dao.impl;

import aledep.dao.interfaces.ProductoDAO;
import aledep.model.Producto;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

	@Override
	public void saveProducto(Producto producto) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(producto);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error guardando el producto", e);
		}
	}

	@Override
	public void updateProducto(Producto producto) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(producto);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error actualizando el producto", e);
		}
	}

	@Override
	public void deleteProducto(Integer id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Producto producto = session.get(Producto.class, id);
			if (producto != null) {
				session.delete(producto);
				transaction.commit();
			} else {
				throw new RuntimeException("Producto no encontrado para eliminar");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error eliminando el producto", e);
		}
	}

	@Override
	public void desactivarProducto(Integer id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			int updatedEntities = session.createQuery("update Producto set activo = false where idProducto = :id")
					.setParameter("id", id).executeUpdate();

			if (updatedEntities == 0) {
				throw new RuntimeException("Producto no encontrado para desactivar");
			}

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Error desactivando el producto", e);
		}
	}

	@Override
	public List<Producto> getAllProductos() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Producto", Producto.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo todos los productos", e);
		}
	}

	@Override
	public Producto getProductoById(Integer id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Producto.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo el producto por ID", e);
		}
	}

	@Override
	public List<Producto> getProductosActivos() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Producto WHERE activo = true", Producto.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error obteniendo productos activos", e);
		}
	}
}