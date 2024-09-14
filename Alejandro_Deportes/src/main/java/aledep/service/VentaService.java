package aledep.service;

import aledep.dao.interfaces.VentaDAO;
import aledep.dao.impl.VentaDAOImpl;
import aledep.dto.VentaDTO;
import aledep.dto.VentaDetalleDTO;
import aledep.model.Venta;
import aledep.model.VentaDetalle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

public class VentaService {
	private final VentaDAO ventaDAO;


	public VentaService() {
		this.ventaDAO = new VentaDAOImpl();
	}

	public void saveVenta(Venta venta) {
		ventaDAO.saveVenta(venta);
	}

	public void updateVenta(Venta venta) {
	    Venta ventaExistente = ventaDAO.getVentaById(venta.getIdVenta());
	    ventaExistente.getDetalles().removeIf(detalle -> !venta.getDetalles().contains(detalle));
	    ventaDAO.updateVenta(venta);
	}

	public void deleteVenta(Integer id) {
		ventaDAO.deleteVenta(id);
	}

	public void desactivarVenta(Integer id) {
		ventaDAO.desactivarVenta(id);
	}

	public List<Venta> getAllVentas() {
		return ventaDAO.getAllVentas();
	}

	public Venta getVentaById(Integer id) {
		Venta venta = ventaDAO.getVentaById(id);
		if (venta != null) {
			Hibernate.initialize(venta.getDetalles());
		}
		return venta;
	}

	public VentaDTO convertirAVentaDTO(Venta venta) {
		VentaDTO dto = new VentaDTO();
		dto.setIdVenta(venta.getIdVenta());

		if (venta.getFechaCreacion() != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			dto.setFechaCreacionStr(formatter.format(venta.getFechaCreacion()));
		} else {
			dto.setFechaCreacionStr("Fecha no disponible");
		}

		dto.setPrecioTotal(venta.getPrecioTotal());

		if (venta.getCliente() != null) {
			dto.setIdCliente(venta.getCliente().getIdCliente());
			dto.setCliente(venta.getCliente().getNombre());
		} else {
			dto.setIdCliente(null);
			dto.setCliente("Sin cliente");
		}

		if (venta.getMetodoPago() != null) {
			dto.setIdMetodoPago(venta.getMetodoPago().getIdMetPago());
			dto.setMetodoPago(venta.getMetodoPago().getNombre());
		} else {
			dto.setIdMetodoPago(null);
			dto.setMetodoPago("Sin método de pago");
		}

		if (venta.getUsuario() != null) {
			dto.setIdUsuario(venta.getUsuario().getIdUsuario());
			dto.setUsuario(venta.getUsuario().getNombre());
		} else {
			dto.setIdUsuario(null);
			dto.setUsuario("Sin usuario");
		}

		// Detalles de la venta
		List<VentaDetalleDTO> detallesDTO = new ArrayList<>();
		for (VentaDetalle detalle : venta.getDetalles()) {
			VentaDetalleDTO detalleDTO = new VentaDetalleDTO();
			detalleDTO.setIdVentaDetalle(detalle.getIdVentaDetalle());
			detalleDTO.setCantidad(detalle.getCantidad());
			detalleDTO.setPrecioUnitario(detalle.getPrecioUnitario());
			if (detalle.getProducto() != null) {
				detalleDTO.setIdProducto(detalle.getProducto().getIdProducto());
				detalleDTO.setProducto(detalle.getProducto().getNombre());
			} else {
				detalleDTO.setIdProducto(null);
				detalleDTO.setProducto("Sin producto");
			}
			detallesDTO.add(detalleDTO);
		}
		dto.setDetalles(detallesDTO);

		return dto;
	}

//	private void validarVenta(Venta venta) {
//		if (venta.getCliente() == null) {
//			throw new IllegalArgumentException("El cliente no puede estar vacío.");
//		}
//		if (venta.getMetodoPago() == null) {
//			throw new IllegalArgumentException("El método de pago no puede estar vacío.");
//		}
//		if (venta.getUsuario() == null) {
//			throw new IllegalArgumentException("El usuario no puede estar vacío.");
//		}
//	}
}
