package ar.com.midasconsultores.catalogodefiltros.service;

import java.util.List;

import org.springframework.data.domain.Page;

import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.FiltroParaQueriesOptimizadas;
import ar.com.midasconsultores.catalogodefiltros.domain.MarcaFiltroPrioridad;

public interface FiltroService {

	public List<Filtro> getFiltros(String filtroName);
	
	public Filtro get(Long idFiltro);

	public Page<FiltroParaQueriesOptimizadas> list(int page, int start, int limit,
			String tipoAplicacion, String marcaAplicacion,
			String modeloAplicacion, 
			String marcaFiltro, String tipoFiltro, String subTipoFiltro,
			String codigoFiltro,
			Boolean esPropio);

	public Page<Filtro> listReemplazos(int page, int start, int limit, Long idFiltro);

	public Page<Filtro> listFiltrosAplicacion(int page, int start, int limit, Long idAplicacion,String filtro);
	
	List<String> listValores(String nombreCombo, String tipoAplicacion,
			String marcaAplicacion, String modeloAplicacion,
			String marcaFiltro, String tipoFiltro, String subTipoFiltro,
			String codigoFiltro);
        
        public void updatePrioridades(List<MarcaFiltroPrioridad> listaMarcaFiltroPrioridad);

}
