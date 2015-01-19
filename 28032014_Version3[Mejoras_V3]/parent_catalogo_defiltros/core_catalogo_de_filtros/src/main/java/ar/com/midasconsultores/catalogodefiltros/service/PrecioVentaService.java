package ar.com.midasconsultores.catalogodefiltros.service;

import ar.com.midasconsultores.catalogodefiltros.domain.PrecioVenta;

public interface PrecioVentaService {

	public PrecioVenta obtenerPrecioVenta();
	
	public void actualizarPrecioVenta(PrecioVenta pv);
}
