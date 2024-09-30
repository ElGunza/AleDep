package aledep.service;

import aledep.dao.impl.CompraDAOImpl;
import aledep.dao.interfaces.CompraDAO;
import aledep.dto.CompraDTO;
import aledep.dto.CompraDetalleDTO;
import aledep.model.Compra;
import aledep.model.CompraDetalle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

public class CompraService {
	private final CompraDAO compraDAO;

	public CompraService() {
		this.compraDAO = new CompraDAOImpl();
	}

	public void saveCompra(Compra compra) {
		
		System.out.println("SERVICE COMPRA");
		compraDAO.saveCompra(compra);
	}

	public void updateCompra(Compra compra) {
		Compra compraExistente = compraDAO.getCompraById(compra.getIdCompra());
		compraExistente.getDetalles().removeIf(detalle -> !compra.getDetalles().contains(detalle));
		compraDAO.updateCompra(compra);
	}

	public void deleteCompra(Integer id) {
		compraDAO.deleteCompra(id);
	}

	public void desactivarCompra(Integer id) {
		compraDAO.desactivarCompra(id);
	}

	public List<Compra> getAllCompras() {
		return compraDAO.getAllCompras();
	}

	public Compra getCompraById(Integer id) {
		Compra Compra = compraDAO.getCompraById(id);
		if (Compra != null) {
			Hibernate.initialize(Compra.getDetalles());
		}
		return Compra;
	}

	public CompraDTO convertirACompraDTO(Compra compra) {
		CompraDTO dto = new CompraDTO();
		dto.setIdCompra(compra.getIdCompra());

		if (compra.getFechaCreacion() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			dto.setFechaCreacionStr(formatter.format(compra.getFechaCreacion()));
		}

		dto.setPrecioTotal(compra.getPrecioTotal());

		if (compra.getProveedor() != null) {
			dto.setIdProveedor(compra.getProveedor().getIdProveedor());
			dto.setProveedor(compra.getProveedor().getNombre());
		}

		if (compra.getMetodoPago() != null) {
			dto.setIdMetodoPago(compra.getMetodoPago().getIdMetPago());
			dto.setMetodoPago(compra.getMetodoPago().getDescripcion());
		}

		if (compra.getUsuario() != null) {
			dto.setIdUsuario(compra.getUsuario().getIdUsuario());
			dto.setUsuario(compra.getUsuario().getNombre());
		}

		List<CompraDetalleDTO> detallesDTO = new ArrayList<>();
		for (CompraDetalle detalle : compra.getDetalles()) {
			CompraDetalleDTO detalleDTO = new CompraDetalleDTO();
			detalleDTO.setIdCompraDetalle(detalle.getIdCompraDetalle());
			detalleDTO.setCantidad(detalle.getCantidad());
			detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
			if (detalle.getProducto() != null) {
				detalleDTO.setIdProducto(detalle.getProducto().getIdProducto());
				detalleDTO.setProducto(detalle.getProducto().getNombre());
			}
			detallesDTO.add(detalleDTO);
		}
		dto.setDetalles(detallesDTO);

		return dto;
	}
}
