package aledep.service;

import aledep.dao.ProductoDAO;
import aledep.dto.ProductoDTO;
import aledep.model.Producto;

import java.util.List;

public class ProductoService {
	private ProductoDAO productoDAO = new ProductoDAO();

	public void saveProducto(Producto producto) {
		// AGREGAR VALIDACIONES
		productoDAO.saveProducto(producto);
	}

	public void updateProducto(Producto producto) {
		// AGREGAR VALIDACIONES
		productoDAO.updateProducto(producto);
	}

	public void deleteProducto(Integer id) {
		productoDAO.deleteProducto(id);
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
		ProductoDTO dto = new ProductoDTO();
		dto.setIdProducto(producto.getIdProducto());
		dto.setCodigo(producto.getCodigo());
		dto.setNombre(producto.getNombre());
		dto.setTalle(producto.getTalle());
		dto.setCantidad(producto.getCantidad());
		dto.setPrecioVenta(producto.getPrecioVenta());
		dto.setPrecioCompra(producto.getPrecioCompra());
		dto.setCategoria(producto.getCategoria() != null ? producto.getCategoria().getNombre() : "Sin categoría");
		dto.setMarca(producto.getMarca() != null ? producto.getMarca().getNombre() : "Sin marca");
		dto.setProveedor(producto.getProveedor() != null ? producto.getProveedor().getNombre() : "Sin proveedor");
		dto.setDeposito(producto.getDeposito() != null ? producto.getDeposito().getNombre() : "Sin depósito");
		dto.setActivo(producto.getActivo());
		
		
		
		return dto;
	}

}
