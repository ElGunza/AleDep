package aledep.service;

import aledep.dao.interfaces.VentaDAO;
import aledep.dao.impl.VentaDAOImpl;
import aledep.dto.ClienteRankingDTO;
import aledep.dto.MetodoPagoDTO;
import aledep.dto.ProductoVentaDTO;
import aledep.dto.UsuarioDTO;
import aledep.dto.VentaDTO;
import aledep.dto.VentaDetalleDTO;
import aledep.model.Cliente;
import aledep.model.MetodoPago;
import aledep.model.Producto;
import aledep.model.Venta;
import aledep.model.VentaDetalle;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	// Método para convertir una venta a VentaDTO
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

	// Método para obtener ventas por período
	public List<VentaDTO> obtenerVentasPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin, String periodo) {
		Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date fechaFinDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<Venta> ventas = ventaDAO.obtenerReporteVentas(fechaInicioDate, fechaFinDate);
		if (ventas == null || ventas.isEmpty()) {
			return new ArrayList<>();
		}
		return agruparVentas(ventas, periodo);
	}

	// Agrupar ventas por período (diario, semanal, mensual, anual)
	@SuppressWarnings("unchecked")
	private List<VentaDTO> agruparVentas(List<Venta> ventas, String periodo) {
		Map<String, List<Object>> ventasAgrupadas = new LinkedHashMap<>();

		DateTimeFormatter diarioFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter mensualFormatter = DateTimeFormatter.ofPattern("MM/yyyy");
		DateTimeFormatter anualFormatter = DateTimeFormatter.ofPattern("yyyy");
		ventas.sort(Comparator.comparing(Venta::getFechaCreacion));

		for (Venta venta : ventas) {
			try {
				if (venta.getFechaCreacion() instanceof java.sql.Date) {
					java.sql.Date fechaSql = (java.sql.Date) venta.getFechaCreacion();
					LocalDate fechaVenta = fechaSql.toLocalDate();
					String claveAgrupada = obtenerClaveAgrupada(fechaVenta, periodo, diarioFormatter, mensualFormatter,
							anualFormatter);
					ventasAgrupadas.putIfAbsent(claveAgrupada,
							new ArrayList<>(Arrays.asList(0.0, new ArrayList<Integer>())));
					Double precioTotal = (Double) ventasAgrupadas.get(claveAgrupada).get(0) + venta.getPrecioTotal();
					List<Integer> idsVentas = (List<Integer>) ventasAgrupadas.get(claveAgrupada).get(1);

					ventasAgrupadas.get(claveAgrupada).set(0, precioTotal);
					idsVentas.add(venta.getIdVenta());

				} else {
					System.out.println("La venta con ID: " + venta.getIdVenta() + " tiene una fecha incompatible.");
				}
			} catch (Exception e) {
				System.out.println("Error procesando la venta con ID: " + venta.getIdVenta());
				e.printStackTrace();
			}
		}

		return ventasAgrupadas.entrySet().stream().map(entry -> new VentaDTO(entry.getKey(),
				(List<Integer>) entry.getValue().get(1), (Double) entry.getValue().get(0)))
				.collect(Collectors.toList());
	}

	private String obtenerClaveAgrupada(LocalDate fechaVenta, String periodo, DateTimeFormatter diarioFormatter,
			DateTimeFormatter mensualFormatter, DateTimeFormatter anualFormatter) {
		String clave = "";
		switch (periodo.toLowerCase()) {
		case "mensual":
			clave = fechaVenta.format(mensualFormatter);
			break;
		case "anual":
			clave = fechaVenta.format(anualFormatter);
			break;
		case "semanal":
			int semanaDelAno = fechaVenta.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
			int ano = fechaVenta.getYear();
			clave = "Semana " + semanaDelAno + " del " + ano;
			break;

		default:
			clave = fechaVenta.format(diarioFormatter);
			break;
		}
		return clave;
	}

	public List<UsuarioDTO> obtenerRankingVendedores(LocalDate fechaInicio, LocalDate fechaFin, String periodo) {
		Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date fechaFinDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

		List<Venta> ventas = ventaDAO.obtenerReporteVentas(fechaInicioDate, fechaFinDate);

		if (ventas == null || ventas.isEmpty()) {
			return new ArrayList<>();
		}

		Map<String, UsuarioDTO> ranking = new LinkedHashMap<>();
		for (Venta venta : ventas) {
			if (venta.getUsuario() != null) {
				String nombreVendedor = venta.getUsuario().getNombre();
				Integer idVendedor = venta.getUsuario().getIdUsuario();

				String claveAgrupada = idVendedor + "-"
						+ obtenerClaveAgrupada(((java.sql.Date) venta.getFechaCreacion()).toLocalDate(), periodo,
								DateTimeFormatter.ofPattern("dd/MM/yyyy"), DateTimeFormatter.ofPattern("MM/yyyy"),
								DateTimeFormatter.ofPattern("yyyy"));

				UsuarioDTO vendedor = ranking.getOrDefault(claveAgrupada, new UsuarioDTO());
				vendedor.setIdUsuario(idVendedor);
				vendedor.setNombre(nombreVendedor);

				vendedor.incrementarVentas(venta.getPrecioTotal());
				ranking.put(claveAgrupada, vendedor);
			}
		}

		// Ordenar por ventas
		return ranking.values().stream().sorted(Comparator.comparingDouble(UsuarioDTO::getTotalVentas).reversed())
				.collect(Collectors.toList());
	}

	// Ranking de Productos Vendidos agrupado por período
	public List<ProductoVentaDTO> obtenerRankingProductosVendidosPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin,
			String periodo) {
		Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date fechaFinDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

		List<Venta> ventas = ventaDAO.obtenerReporteVentas(fechaInicioDate, fechaFinDate);

		// Cambiamos el tipo de clave a Integer, que será el ID único del producto
		Map<Integer, ProductoVentaDTO> rankingProductos = new LinkedHashMap<>();

		for (Venta venta : ventas) {
			for (VentaDetalle detalle : venta.getDetalles()) {
				Producto producto = detalle.getProducto();

				if (producto == null) {
					continue; // Si el producto es nulo, se omite
				}

				// Usamos el ID del producto como clave de agrupación
				ProductoVentaDTO productoDTO = rankingProductos.getOrDefault(producto.getIdProducto(),
						new ProductoVentaDTO());
				productoDTO.setNombreProducto(producto.getNombre());

				// Acumular la cantidad vendida
				productoDTO.setCantidadVendida(productoDTO.getCantidadVendida() == null ? detalle.getCantidad()
						: productoDTO.getCantidadVendida() + detalle.getCantidad());

				productoDTO.setPrecioVenta(producto.getPrecioVenta());
				productoDTO.setStockActual(producto.getCantidad());

				// Guardar o actualizar en el mapa
				rankingProductos.put(producto.getIdProducto(), productoDTO);
			}
		}

		// Devolver la lista ordenada por cantidad vendida
		return rankingProductos.values().stream()
				.sorted(Comparator.comparing(ProductoVentaDTO::getCantidadVendida).reversed())
				.collect(Collectors.toList());
	}

	// Ranking de Clientes agrupado por período
	public List<ClienteRankingDTO> obtenerRankingClientesPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin,
			String periodo) {
		Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date fechaFinDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

		List<Venta> ventas = ventaDAO.obtenerReporteVentas(fechaInicioDate, fechaFinDate);

		// Cambiamos el tipo de clave a Integer, que será el ID único del cliente
		Map<Integer, ClienteRankingDTO> rankingClientes = new LinkedHashMap<>();

		for (Venta venta : ventas) {
			Cliente cliente = venta.getCliente();
			if (cliente == null) {
				continue; // Si el cliente es nulo, se omite
			}

			// Usamos el ID del cliente como clave de agrupación
			ClienteRankingDTO clienteDTO = rankingClientes.getOrDefault(cliente.getIdCliente(),
					new ClienteRankingDTO());
			clienteDTO.setNombreCliente(cliente.getNombre());
			clienteDTO
					.setCantidadVentas(clienteDTO.getCantidadVentas() == null ? 1 : clienteDTO.getCantidadVentas() + 1);
			clienteDTO.setTotalVendido(clienteDTO.getTotalVendido() == null ? venta.getPrecioTotal()
					: clienteDTO.getTotalVendido() + venta.getPrecioTotal());

			rankingClientes.put(cliente.getIdCliente(), clienteDTO);
		}

		return rankingClientes.values().stream()
				.sorted(Comparator.comparing(ClienteRankingDTO::getCantidadVentas).reversed())
				.collect(Collectors.toList());
	}

	public List<MetodoPagoDTO> obtenerMetodosPagoUtilizados(LocalDate fechaInicio, LocalDate fechaFin) {
		Date fechaInicioDate = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date fechaFinDate = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

		List<Venta> ventas = ventaDAO.obtenerReporteVentas(fechaInicioDate, fechaFinDate);
		Map<Integer, MetodoPagoDTO> rankingMetodosPago = new LinkedHashMap<>();

		for (Venta venta : ventas) {
			MetodoPago metodo = venta.getMetodoPago();
			if (metodo == null) {
				continue;
			}

			MetodoPagoDTO metodoDTO = rankingMetodosPago.getOrDefault(metodo.getIdMetPago(), new MetodoPagoDTO());
			metodoDTO.setNombre(metodo.getNombre());
			metodoDTO.setCantidadUsos(metodoDTO.getCantidadUsos() == null ? 1 : metodoDTO.getCantidadUsos() + 1);

			rankingMetodosPago.put(metodo.getIdMetPago(), metodoDTO);
		}

		return rankingMetodosPago.values().stream()
				.sorted(Comparator.comparing(MetodoPagoDTO::getCantidadUsos).reversed()).collect(Collectors.toList());
	}

}
