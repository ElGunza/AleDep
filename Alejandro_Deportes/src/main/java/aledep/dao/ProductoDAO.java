package aledep.dao;

import aledep.model.Producto;
import aledep.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductoDAO {

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
		}
	}

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
		}
	}

	public void deleteProducto(Integer id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Producto producto = session.get(Producto.class, id);
			if (producto != null) {
				session.delete(producto);
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void desactivarProducto(Integer id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Producto producto = session.get(Producto.class, id);
			if (producto != null) {
				producto.setActivo(false); // Realiza la baja lógica marcando como inactivo
				session.update(producto);
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public List<Producto> getAllProductos() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Producto", Producto.class).list();
		}
	}

	public Producto getProductoById(Integer id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Producto.class, id);
		}
	}
	
	public List<Producto> getProductosActivos() {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        return session.createQuery("FROM Producto WHERE activo = true", Producto.class).list();
	    }
	}
	
	
	

}
