package aledep.controller.reportes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import aledep.dto.ProductoVentaDTO;
import aledep.service.VentaService;

@WebServlet("/reporteProductosVendidosSearch")
public class BuscarProductosVendidosServlet extends HttpServlet {
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

            List<ProductoVentaDTO> rankingProductosVendidos = ventaService.obtenerRankingProductosVendidosPorPeriodo(fechaInicio, fechaFin, periodo);

            System.out.println("Productos enviados al cliente (JSON): " + new Gson().toJson(rankingProductosVendidos));
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(rankingProductosVendidos));
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
