package aledep.controller.empresa;

import aledep.model.Empresa;
import aledep.service.EmpresaService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/configuracionEmpresa")
public class EmpresaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final EmpresaService empresaService = new EmpresaService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Empresa empresa = empresaService.obtenerEmpresaUnica();

        request.setAttribute("empresa", empresa);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("configuracion_empresa.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Empresa empresa = empresaService.obtenerEmpresaUnica();

        if (empresa == null) {
            empresa = new Empresa(); 
        }

        empresa.setNombre(request.getParameter("nombre"));
        empresa.setRazonSocial(request.getParameter("razonSocial"));
        empresa.setCuit(request.getParameter("cuit"));
        empresa.setDireccion(request.getParameter("direccion"));
        empresa.setTelefono(request.getParameter("telefono"));
        empresa.setEmail(request.getParameter("email"));
        empresa.setLogoPath(request.getParameter("logoPath"));
        empresa.setMonedaLegal(request.getParameter("monedaLegal"));
        empresa.setAccessHistory(Boolean.parseBoolean(request.getParameter("accessHistory")));
        empresa.setModificationHistory(Boolean.parseBoolean(request.getParameter("modificationHistory")));
        empresa.setDeletionHistory(Boolean.parseBoolean(request.getParameter("deletionHistory")));

        String alertaMinimoStockStr = request.getParameter("alertaMinimoStock");
        if (alertaMinimoStockStr != null && !alertaMinimoStockStr.isEmpty()) {
            empresa.setAlertaMinimoStock(Float.parseFloat(alertaMinimoStockStr));
        } else {
            empresa.setAlertaMinimoStock(null); 
        }

        empresaService.saveOrUpdateEmpresa(empresa);

      
        response.sendRedirect("configuracionEmpresa?success=true");
    }


}
