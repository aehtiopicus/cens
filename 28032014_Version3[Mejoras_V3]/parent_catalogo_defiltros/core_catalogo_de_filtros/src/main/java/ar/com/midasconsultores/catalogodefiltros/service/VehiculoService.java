package ar.com.midasconsultores.catalogodefiltros.service;

import java.util.List;

import org.springframework.data.domain.Page;

import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;

public interface VehiculoService {

	public Vehiculo get(Long idVehiculo);
	
	public Page<Vehiculo> list(int page, int start, int limit, Filtro filtro);
	
	public List<Vehiculo> list();
		
	public Page<Vehiculo> list(int page, int start, int limit, String tipo, String marca, String modelo);
}
