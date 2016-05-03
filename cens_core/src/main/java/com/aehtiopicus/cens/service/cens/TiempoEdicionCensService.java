package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.TiempoEdicion;
import com.aehtiopicus.cens.utils.CensException;

public interface TiempoEdicionCensService {

	public List<TiempoEdicion> ensamblarEntradasTiempoEdicion();
	
	public void guardarEntradasTiempoEdicion(List<TiempoEdicion> tiempoEdicionList) throws CensException;

	public void invalidateEntries(Long id) throws CensException;

}
