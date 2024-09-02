package aledep.dao.interfaces;

import aledep.model.Producto;
import java.util.List;

public interface ProductoDAO {
	void saveProducto(Producto producto);

	void updateProducto(Producto producto);

	void deleteProducto(Integer id);

	void desactivarProducto(Integer id);

	List<Producto> getAllProductos();

	Producto getProductoById(Integer id);

	List<Producto> getProductosActivos();

	void actualizarStock(Integer id_producto, int nuevaCantidad);

}
