package aledep.dto;

import java.util.List;

public class ReporteDashboardDTO {
	private List<ProductoVentaDTO> rankingProductosVendidos;
	private List<UsuarioDTO> rankingVendedores;
	private List<VentaDTO> listaVentas;
	private List<ClienteRankingDTO> rankingClientes;

	public ReporteDashboardDTO(List<ProductoVentaDTO> rankingProductosVendidos, List<UsuarioDTO> rankingVendedores,
			List<VentaDTO> listaVentas, List<ClienteRankingDTO> rankingClientes) {
		this.rankingProductosVendidos = rankingProductosVendidos;
		this.rankingVendedores = rankingVendedores;
		this.listaVentas = listaVentas;
		this.rankingClientes = rankingClientes;
	}

	// Getters y Setters para los atributos
	public List<ProductoVentaDTO> getRankingProductosVendidos() {
		return rankingProductosVendidos;
	}

	public void setRankingProductosVendidos(List<ProductoVentaDTO> rankingProductosVendidos) {
		this.rankingProductosVendidos = rankingProductosVendidos;
	}

	public List<UsuarioDTO> getRankingVendedores() {
		return rankingVendedores;
	}

	public void setRankingVendedores(List<UsuarioDTO> rankingVendedores) {
		this.rankingVendedores = rankingVendedores;
	}

	public List<VentaDTO> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(List<VentaDTO> listaVentas) {
		this.listaVentas = listaVentas;
	}

	public List<ClienteRankingDTO> getRankingClientes() {
		return rankingClientes;
	}

	public void setRankingClientes(List<ClienteRankingDTO> rankingClientes) {
		this.rankingClientes = rankingClientes;
	}
}
