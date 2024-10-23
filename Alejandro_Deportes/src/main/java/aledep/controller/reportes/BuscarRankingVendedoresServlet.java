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

import aledep.dto.UsuarioDTO;
import aledep.service.VentaService;

@WebServlet("/rankingVendedoresSearch")
public class BuscarRankingVendedoresServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VentaService ventaService = new VentaService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String periodo = "mensual";
        String fechaDesdeStr = request.getParameter("fechaDesde");
        String fechaHastaStr = request.getParameter("fechaHasta");

        LocalDate fechaDesde = LocalDate.parse(fechaDesdeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate fechaHasta = LocalDate.parse(fechaHastaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<UsuarioDTO> rankingVendedores = ventaService.obtenerRankingVendedores(fechaDesde, fechaHasta, periodo);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(rankingVendedores));
    }
}
