package ar.com.midasconsultores.catalogodefiltros.service;

public interface PrecioAjusteService {

	public double obtenerPorcentajeAjuste();
	
	public double calcularPrecioAjustado(double precio,double ajuste);
	
	public double obtenerPorcentajeAjusteByCliente(String codigoCliente);
}
