package aledep.controller.reportes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import aledep.dto.MetodoPagoDTO;
import aledep.dto.VentaDTO;
import aledep.service.VentaService;

/**
 * Servlet implementation class BuscarVentasServlet
 */
@WebServlet("/reporteVentasSearch")
public class BuscarVentasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VentaService ventaService = new VentaService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String periodo = request.getParameter("periodo");
	        String fechaDesdeStr = request.getParameter("fechaDesde");
	        String fechaHastaStr = request.getParameter("fechaHasta");

	        LocalDate fechaInicio = convertirFecha(fechaDesdeStr, LocalDate.now().minusDays(1));
	        LocalDate fechaFin = convertirFecha(fechaHastaStr, LocalDate.now());

	        List<VentaDTO> listaVentas = ventaService.obtenerVentasPorPeriodo(fechaInicio, fechaFin, periodo);
	        List<MetodoPagoDTO> metodosPago = ventaService.obtenerMetodosPagoUtilizados(fechaInicio, fechaFin);

	        Map<String, Object> responseData = new HashMap<>();
	        responseData.put("ventas", listaVentas);
	        responseData.put("metodosPago", metodosPago);

	        String json = new Gson().toJson(responseData);
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json);
	    } catch (Exception e) {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud.");
	    }
	}


	private LocalDate convertirFecha(String fechaStr, LocalDate fechaPorDefecto) {
		if (fechaStr != null && !fechaStr.isEmpty()) {
			return LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return fechaPorDefecto;
	}
}
