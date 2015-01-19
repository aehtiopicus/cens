package ar.com.midasconsultores.catalogodefiltros.mappers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.FiltroParaQueriesOptimizadas;
import ar.com.midasconsultores.catalogodefiltros.dto.FiltroCantidadDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.FiltroDTO;
import ar.com.midasconsultores.catalogodefiltros.repository.FiltroRepository;
import ar.com.midasconsultores.catalogodefiltros.service.PrecioAjusteService;
import ar.com.midasconsultores.catalogodefiltros.service.PrecioVentaService;
import ar.com.midasconsultores.catalogodefiltros.utils.RegularExpressionValidator;

/**
 * 
 * @author cgaia
 */
@Component
public class FiltrosMapper {

	private static final String PERCENTAGE_SIMBOL = "%";
	private static final String PEDIDO_NULO = "0";
	private static final String DEFAULT_IMG = "image_not_available.jpg";
	private static final String EMPTY_TEXT = " - ";
	private static final String TREE_DOTS = "...";

	@Autowired
	FiltroRepository filtroRepository;

	@Autowired
	private PrecioAjusteService precioAjuste;
	
	@Autowired
	private PrecioVentaService precioVenta;

	@Autowired
	@Qualifier("hibernateStringZeroSaltEncryptor")
	private HibernatePBEStringEncryptor hibernateStringEncryptor;
	
	public FiltroDTO map(Filtro filtro) {
		return map(filtro, FiltroMode.FILTRO);
	}

	public FiltroDTO map(Filtro filtro, FiltroMode fm) {
		FiltroDTO filtroDTO = null;
		switch (fm) {

		case FILTRO_PRECIO:
		case FILTRO:
			filtroDTO = new FiltroDTO();
			break;
		case FILTRO_CANTIDAD:
			filtroDTO = new FiltroCantidadDTO();
			break;
		}

		filtroDTO.setId(filtro.getId());

		if(filtro.getCodigoCorto() != null && !filtro.getCodigoCorto().trim().isEmpty()){
			filtroDTO.setCodigoCorto(getStringValue(filtro.getCodigoCorto()));
		} else {
			filtroDTO.setCodigoCorto(EMPTY_TEXT);
		}
		
		if(filtro.getCodigoLargo() != null && !filtro.getCodigoLargo().trim().isEmpty()){
			filtroDTO.setCodigoLargo(getStringValue(filtro.getCodigoLargo()));
		} else {
			filtroDTO.setCodigoLargo(EMPTY_TEXT);
		}

		if(filtro.getDescripcion() != null && !filtro.getDescripcion().trim().isEmpty()){
			filtroDTO.setDescripcion(getStringValue(filtro.getDescripcion()));
		} else {
			filtroDTO.setDescripcion(EMPTY_TEXT);
		}

		if(filtro.getMarca() != null && !filtro.getMarca().trim().isEmpty()){
			filtroDTO.setMarca(getStringValue(filtro.getMarca()));
		} else {
			filtroDTO.setMarca(EMPTY_TEXT);
		}

		if(filtro.getMedidas() != null && !filtro.getMedidas().trim().isEmpty()){
			filtroDTO.setMedidas(getStringValue(filtro.getMedidas()));
		} else {
			filtroDTO.setMedidas(EMPTY_TEXT);
		}

		if(filtro.getLargoFiltro() != null && !filtro.getLargoFiltro().trim().isEmpty()){
			filtroDTO.setLargoFiltro(getStringValue(filtro.getLargoFiltro()));
		} else {
			filtroDTO.setLargoFiltro(EMPTY_TEXT);
		}

		if(filtro.getAnchoFiltro() != null && !filtro.getAnchoFiltro().trim().isEmpty()){
			filtroDTO.setAnchoFiltro(getStringValue(filtro.getAnchoFiltro()));
		} else {
			filtroDTO.setAnchoFiltro(EMPTY_TEXT);
		}

		if(filtro.getRoscaFiltro() != null && !filtro.getRoscaFiltro().trim().isEmpty()){
			filtroDTO.setRoscaFiltro(getStringValue(filtro.getRoscaFiltro()));
		} else {
			filtroDTO.setRoscaFiltro(EMPTY_TEXT);
		}

		if(filtro.getAlturaFiltro() != null && !filtro.getAlturaFiltro().trim().isEmpty()){
			filtroDTO.setAlturaFiltro(getStringValue(filtro.getAlturaFiltro()));
		} else {
			filtroDTO.setAlturaFiltro(EMPTY_TEXT);
		}

		if(filtro.getRoscaSensorFiltro() != null && !filtro.getRoscaSensorFiltro().trim().isEmpty()){
			filtroDTO.setRoscaSensorFiltro(getStringValue(filtro.getRoscaSensorFiltro()));
		} else {
			filtroDTO.setRoscaSensorFiltro(EMPTY_TEXT);
		}

		if(filtro.getTipo() != null && !filtro.getTipo().trim().isEmpty()){
			filtroDTO.setTipo(getStringValue(filtro.getTipo()));
		} else {
			filtroDTO.setTipo(EMPTY_TEXT);
		}

		if(filtro.getSubTipo() != null && !filtro.getSubTipo().trim().isEmpty()){
			filtroDTO.setSubTipo(getStringValue(filtro.getSubTipo()));
		} else {
			filtroDTO.setSubTipo(EMPTY_TEXT);
		}

		if (fm.equals(FiltroMode.FILTRO_PRECIO)) {
			
			//Calculamos el precio final al publico
			double ajusteEmpresa = precioAjuste.obtenerPorcentajeAjuste();
			double ajusteVenta = 0;
			if(precioVenta.obtenerPrecioVenta() != null){
				ajusteVenta = precioVenta.obtenerPrecioVenta().getPorcentaje();
			}
			try {
				double precioConAjuste;
				precioConAjuste = precioAjuste.calcularPrecioAjustado(Double.parseDouble(filtro
						.getPrecioBase().getPrecio()), ajusteEmpresa);
				precioConAjuste = precioAjuste.calcularPrecioAjustado(precioConAjuste, ajusteVenta);
				filtroDTO.setPrecioBase(String.valueOf(precioConAjuste));
			} catch (Exception e) {
				filtroDTO.setPrecioBase(PEDIDO_NULO);
			}
			
		} else {
			if (filtro.getPrecioBase() != null
					&& !StringUtils.isEmpty(filtro.getPrecioBase())) {
				filtroDTO.setPrecioBase(filtro.getPrecioBase().getPrecio());
			}
		}
		
		if (filtro.getFoto() == null || filtro.getFoto().isEmpty()) {
			String nameFoto = filtro.getCodigoCorto();
			if(nameFoto.contains("/")){
				nameFoto = nameFoto.replace("/","").trim();
				
			}
			filtroDTO.setFoto(nameFoto+".jpg");
		} else {
			filtroDTO.setFoto(getStringValue(filtro.getFoto()));
		}
		return filtroDTO;

	}

	public FiltroDTO mapFOPT(FiltroParaQueriesOptimizadas filtro,
			String busqueda) {

		FiltroDTO filtroDTO = new FiltroDTO();

		filtroDTO.setId(filtro.getId_filtro());

		filtroDTO.setCodigoCorto(getStringValue(filtro.getCodigoCorto()));

		filtroDTO.setCodigoLargo(getStringValue(filtro.getCodigoLargo()));

		filtroDTO.setDescripcion(getStringValue(filtro.getDescripcion()));

		filtroDTO.setMarca(getStringValue(filtro.getMarca()));

		filtroDTO.setMedidas(getStringValue(filtro.getMedidas()));

		filtroDTO.setLargoFiltro(getStringValue(filtro.getLargoFiltro()));

		filtroDTO.setAnchoFiltro(getStringValue(filtro.getAnchoFiltro()));

		filtroDTO.setRoscaFiltro(getStringValue(filtro.getRoscaFiltro()));

		filtroDTO.setAlturaFiltro(getStringValue(filtro.getAlturaFiltro()));

		filtroDTO.setRoscaSensorFiltro(getStringValue(filtro
				.getRoscaSensorFiltro()));

		filtroDTO.setTipo(getStringValue(filtro.getTipo()));

		filtroDTO.setSubTipo(getStringValue(filtro.getSubTipo()));

                if(busqueda!=null && busqueda.length()>0){
                    busqueda = RegularExpressionValidator.removeAllWhiteSpaces(busqueda);
                }
		List<Object[]> result = filtroRepository
				.listarCodigosOEMParaBusquedaParcial(filtro.getId_filtro(),
						PERCENTAGE_SIMBOL + busqueda + PERCENTAGE_SIMBOL);
		
		String codigoOEM;

		if (result.size() > 0) {
			codigoOEM = (String) result.get(0)[0];
			codigoOEM = result.size() > 1 ? codigoOEM + TREE_DOTS : codigoOEM;
			filtroDTO.setCodigoOEM(codigoOEM);
			filtroDTO.setMarcaOEM(getStringValue((String)result.get(0)[1]));
		} else {
			filtroDTO.setCodigoOEM(EMPTY_TEXT);
			filtroDTO.setMarcaOEM(EMPTY_TEXT);
		}
		
		if (filtro.getFoto() == null || filtro.getFoto().isEmpty()) {
			String nameFoto = filtro.getCodigoCorto();
			if(nameFoto.contains("/")){
				nameFoto = nameFoto.replace("/","").trim();
				
			}
			filtroDTO.setFoto(nameFoto+".jpg");
		} else {
			filtroDTO.setFoto(getStringValue(filtro.getFoto()));
		}
		
		//Calculamos el precio final al publico
		double ajusteEmpresa = precioAjuste.obtenerPorcentajeAjuste();
		double ajusteVenta = 0;
		if(precioVenta.obtenerPrecioVenta() != null){
			ajusteVenta = precioVenta.obtenerPrecioVenta().getPorcentaje();
		}
		
		try {
			double precioConAjuste;
			precioConAjuste = precioAjuste.calcularPrecioAjustado(Double.parseDouble(filtro.getPrecio()), ajusteEmpresa);
			precioConAjuste = precioAjuste.calcularPrecioAjustado(precioConAjuste, ajusteVenta);
			filtroDTO.setPrecioBase(String.valueOf(precioConAjuste));
		} catch (Exception e) {
			filtroDTO.setPrecioBase(PEDIDO_NULO);
		}
		
		return filtroDTO;

	}

	private String getStringValue(String value) {
		return value == null ? EMPTY_TEXT : value.trim();
	}

	public List<FiltroDTO> mapToDTOList(List<Filtro> filtros) {
		List<FiltroDTO> filtroDTOs = new ArrayList<FiltroDTO>();
		for (Filtro f : filtros) {
			filtroDTOs.add(this.map(f));
		}
		return filtroDTOs;
	}

	public List<FiltroDTO> mapToDTOListFOPT(
			List<FiltroParaQueriesOptimizadas> filtros, String busqueda) {
		List<FiltroDTO> filtroDTOs = new ArrayList<FiltroDTO>();
		for (FiltroParaQueriesOptimizadas f : filtros) {
			filtroDTOs.add(this.mapFOPT(f, busqueda));
		}
		return filtroDTOs;
	}
	
	public List<FiltroDTO> mapToDTOListFOPT(
			List<FiltroParaQueriesOptimizadas> filtros, String busqueda,String url) {
		List<FiltroDTO> filtroDTOs = new ArrayList<FiltroDTO>();
		List<FiltroDTO> filtrosSinFoto = new ArrayList<FiltroDTO>();
		for (FiltroParaQueriesOptimizadas f : filtros) {
			String nameFoto = f.getFoto();
			if (StringUtils.isEmpty(nameFoto)) {
				nameFoto=f.getCodigoCorto()+".jpg";
			} else {
				nameFoto = f.getFoto().trim();
			}
			if(nameFoto.contains("/")){
				nameFoto = nameFoto.replace("/","").trim();
				f.setFoto(nameFoto);
			}
			File file = new File(url + nameFoto);
			if(file.exists()){
				filtroDTOs.add(this.mapFOPT(f, busqueda));	
			}else{
				filtrosSinFoto.add(this.mapFOPT(f, busqueda));
			}
			
		}
		filtroDTOs.addAll(filtrosSinFoto);
		return filtroDTOs;
	}

	public boolean checkUpdateNeeded(Long idFiltro, String tipoAplicacion,
			String marcaAplicacion, String modeloAplicacion,
			String marcaFiltro, String tipoFiltro, String subTipoFiltro,
			String codigoFiltro) {
		return idFiltro != null || tipoAplicacion != null
				|| marcaAplicacion != null || modeloAplicacion != null
				|| marcaFiltro != null || tipoFiltro != null
				|| subTipoFiltro != null || codigoFiltro != null;
	}

	public Filtro map(FiltroDTO filtroDTO) {

		Filtro filtro = new Filtro();

		filtro.setId(filtroDTO.getId());

		filtro.setCodigoCorto(filtroDTO.getCodigoCorto());

		filtro.setCodigoLargo(filtroDTO.getCodigoLargo());

		filtro.setDescripcion(filtroDTO.getDescripcion());

		filtro.setMedidas(filtroDTO.getMedidas());

		filtro.setTipo(filtroDTO.getTipo());

		filtro.setSubTipo(filtroDTO.getSubTipo());

		filtro.setFoto(filtroDTO.getFoto());

		return filtro;

	}

	public List<Filtro> mapToObjList(List<FiltroDTO> usuarioDTOs) {
		List<Filtro> users = new ArrayList<Filtro>();
		for (FiltroDTO udto : usuarioDTOs) {
			users.add(this.map(udto));
		}
		return users;
	}
	

	public List<String> mapToFotosName(List<FiltroDTO> filtrosDTOs) {
		List<String> namesOfExistentFotos = new ArrayList<String>();
		for (FiltroDTO filtro : filtrosDTOs) {
			filtro.getFoto();
		}
		return namesOfExistentFotos;
	}

}
