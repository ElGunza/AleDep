package aledep.service;

import aledep.dao.interfaces.ProductoDAO;
import aledep.dao.impl.ProductoDAOImpl;
import aledep.dto.ProductoDTO;
import aledep.model.Producto;

import java.util.List;

public class ProductoService {
	private final ProductoDAO productoDAO;

	public ProductoService() {
		this.productoDAO = new ProductoDAOImpl();
	}

	public void saveProducto(Producto producto) {
		
	System.out.println("SAVE PRODUCTO");
		
		if (producto.getCodigo() == null || producto.getCodigo().isEmpty()) {
			int idProducto = obtenerSiguienteIdProducto(); 
			String codigoGenerado = generarCodigoProducto(idProducto);
			producto.setCodigo(codigoGenerado);
		}

		validarProducto(producto);
		productoDAO.saveProducto(producto);
	}

	public void updateProducto(Producto producto) {
		validarProducto(producto);
		productoDAO.updateProducto(producto);
	}

	public void deleteProducto(Integer id) {
		productoDAO.deleteProducto(id);
	}

	public void actualizarStock(Integer id_producto, int nuevaCantidad) {
		productoDAO.actualizarStock(id_producto, nuevaCantidad);
	}

	public void desactivarProducto(Integer id) {
		productoDAO.desactivarProducto(id);
	}

	public List<Producto> getAllProductos() {
		return productoDAO.getAllProductos();
	}

	public Producto getProductoById(Integer id) {
		return productoDAO.getProductoById(id);
	}

	public List<Producto> getProductosActivos() {
		return productoDAO.getProductosActivos();
	}

	public ProductoDTO convertirAProductoDTO(Producto producto) {
		return convertirProductoAProductoDTO(producto);
	}

	public String generarCodigoProducto(int idProducto) {
		return String.format("P%03d", idProducto); 
	}

	public int obtenerSiguienteIdProducto() {
		return productoDAO.obtenerUltimoIdProducto() + 1;
	}

	private void validarProducto(Producto producto) {
		if (producto.getCodigo() == null || producto.getCodigo().isEmpty()) {
			throw new IllegalArgumentException("El código del producto no puede estar vacío.");
		}
		if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
			throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
		}
	}

	private ProductoDTO convertirProductoAProductoDTO(Producto producto) {
		ProductoDTO dto = new ProductoDTO();
		dto.setIdProducto(producto.getIdProducto());
		dto.setCodigo(producto.getCodigo());
		dto.setNombre(producto.getNombre());
		dto.setTalle(producto.getTalle());
		dto.setCantidad(producto.getCantidad());
		dto.setPrecioVenta(producto.getPrecioVenta());
		dto.setPrecioCompra(producto.getPrecioCompra());

		// Validación de Categoria
		if (producto.getCategoria() != null) {
			dto.setIdCategoria(producto.getCategoria().getIdCategoria());
			dto.setCategoria(producto.getCategoria().getNombre());
		} else {
			dto.setIdCategoria(null);
			dto.setCategoria("Sin categoría");
		}

		// Validación de Marca
		if (producto.getMarca() != null) {
			dto.setIdMarca(producto.getMarca().getIdMarca());
			dto.setMarca(producto.getMarca().getNombre());
		} else {
			dto.setIdMarca(null);
			dto.setMarca("Sin marca");
		}

		// Validación de Proveedor
		if (producto.getProveedor() != null) {
			dto.setIdProveedor(producto.getProveedor().getIdProveedor());
			dto.setProveedor(producto.getProveedor().getNombre());
		} else {
			dto.setIdProveedor(null);
			dto.setProveedor("Sin proveedor");
		}

		// Validación de Deposito
		if (producto.getDeposito() != null) {
			dto.setIdDeposito(producto.getDeposito().getIdDeposito());
			dto.setDeposito(producto.getDeposito().getNombre());
		} else {
			dto.setIdDeposito(null);
			dto.setDeposito("Sin depósito");
		}

		dto.setActivo(producto.getActivo());
		return dto;
	}
}
