package aledep.controller.menu;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import aledep.dto.ProductoVentaDTO;
import aledep.dto.ReporteDashboardDTO;
import aledep.dto.UsuarioDTO;
import aledep.dto.VentaDTO;
import aledep.dto.ClienteRankingDTO;
import aledep.service.VentaService;

@WebServlet("/dashboard")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final VentaService ventaService = new VentaService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String periodo = request.getParameter("periodo");
			LocalDate fechaInicio = LocalDate.now().minusYears(1);
			LocalDate fechaFin = LocalDate.now();

			List<ProductoVentaDTO> rankingProductosVendidos = ventaService
					.obtenerRankingProductosVendidosPorPeriodo(fechaInicio, fechaFin, periodo);
			List<UsuarioDTO> rankingVendedores = ventaService.obtenerRankingVendedores(fechaInicio, fechaFin, periodo);
			List<VentaDTO> listaVentas = ventaService.obtenerVentasPorPeriodo(fechaInicio, fechaFin, periodo);
			List<ClienteRankingDTO> rankingClientes = ventaService.obtenerRankingClientesPorPeriodo(fechaInicio,
					fechaFin, periodo);

			// Crear objeto para contener todos los reportes
			ReporteDashboardDTO dashboardData = new ReporteDashboardDTO(rankingProductosVendidos, rankingVendedores,
					listaVentas, rankingClientes);

			String json = new Gson().toJson(dashboardData);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando la solicitud.");
		}
	}
}
