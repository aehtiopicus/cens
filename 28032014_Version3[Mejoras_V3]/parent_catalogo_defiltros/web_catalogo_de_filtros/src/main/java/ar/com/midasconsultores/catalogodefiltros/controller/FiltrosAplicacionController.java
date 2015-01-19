package ar.com.midasconsultores.catalogodefiltros.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.midasconsultores.catalogodefiltros.configuration.UrlConstants;
import ar.com.midasconsultores.catalogodefiltros.configuration.ViewConstants;
import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;
import ar.com.midasconsultores.catalogodefiltros.dto.FiltroDTO;
import ar.com.midasconsultores.catalogodefiltros.dto.VehiculoDTO;
import ar.com.midasconsultores.catalogodefiltros.mappers.FiltrosMapper;
import ar.com.midasconsultores.catalogodefiltros.mappers.VehiculosMapper;
import ar.com.midasconsultores.catalogodefiltros.service.FiltroService;
import ar.com.midasconsultores.catalogodefiltros.service.VehiculoService;
import ar.com.midasconsultores.utils.ExcepcionDeNegocios;
import ar.com.midasconsultores.utils.extjs.ResponseMap;

@Controller
@RequestMapping(value=UrlConstants.FILTRO_APLICACION_URL)
public class FiltrosAplicacionController {

	private static final Logger logger = LoggerFactory.getLogger(FiltrosAplicacionController.class);
	
	private static final String TIPO_FILTRO = "tipoFiltro";
	private static final String ID_APLICACION = "idAplicacion";
	
	@Autowired
	private ResponseMap<VehiculoDTO> extJSVehiculo;
	
	@Autowired
	private ResponseMap<FiltroDTO> extJSFiltro;

	@Autowired
	VehiculoService vehiculoService;

	@Autowired
	VehiculosMapper vehiculosMapper;
	

	@Autowired
	FiltroService filtroService;

	@Autowired
	FiltrosMapper filtroMapper;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView getAplicacionPage(@PathVariable("id") long id){
		VehiculoDTO dto = vehiculosMapper.map(vehiculoService.get(id));
		ModelAndView mav = new ModelAndView(ViewConstants.APLICACION_FILTROS_VIEW);
		mav.addObject("aplicacion", extJSVehiculo.mapOK(Arrays.asList(dto), 1l));
		return mav;
	}
	
	@RequestMapping(value = UrlConstants.APLICACIONES_BY_FILTROS_URL)
	public @ResponseBody Map<String, Object> listAplicacionesByFiltros(
			Locale locale,
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(value = "tipo", required = false) String tipo,
			@RequestParam(value = "marca", required = false) String marca,
			@RequestParam(value = "modelo", required = false) String modelo)
			throws Exception {

		try {
			
			int start = (page-1) * rows;
			int limit = rows;
			Page<VehiculoDTO> vehiculoDTO = null;
			long total = 0;
			
			if (StringUtils.isNotEmpty(tipo) || StringUtils.isNotEmpty(marca) || StringUtils.isNotEmpty(modelo)) {
				
				Page<Vehiculo> vehiculos = vehiculoService.list(page-1, start, limit, tipo, marca, modelo);
						
				vehiculoDTO = new PageImpl<VehiculoDTO>(vehiculosMapper.mapToDTOList(vehiculos.getContent()));

				total = vehiculos.getTotalElements();
			} else {
				vehiculoDTO = new PageImpl<VehiculoDTO>(new ArrayList<VehiculoDTO>());
			}
			
			Map<String, Object> result = extJSVehiculo.mapOK(vehiculoDTO.getContent(), total, page, rows);
			return result;

		} catch (ExcepcionDeNegocios e) {

			return extJSVehiculo.mapError(e.getMessage());

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return extJSVehiculo.mapError("Ocurri&oacute un error al tratar de listar las aplicaciones.");
		}

	}

	
	@RequestMapping(value = UrlConstants.FILTRO_APLICACION_LIST_URL)
	public @ResponseBody Map<String, Object> listAplicaciones(
			Locale locale,
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(value = ID_APLICACION, required = false) Long idAplicacion,
			@RequestParam(value = TIPO_FILTRO, required = false) String tipoFiltro)
			throws Exception {

		try {
			
			int start = (page-1) * rows;
			int limit = rows;
			Page<FiltroDTO> filtroDTO = null;
			long total = 0;
			
			if (idAplicacion != null) {
				Page<Filtro> filtros = filtroService.listFiltrosAplicacion(
						page - 1, start, limit, idAplicacion, tipoFiltro);

				filtroDTO = new PageImpl<FiltroDTO>(
						filtroMapper.mapToDTOList(filtros.getContent()));

				total = filtros.getTotalElements();
			} else {
				filtroDTO = new PageImpl<FiltroDTO>(new ArrayList<FiltroDTO>());
			}
			
			Map<String, Object> result = extJSFiltro.mapOK(filtroDTO.getContent(), total, page, rows);
			return result;

		} catch (ExcepcionDeNegocios e) {

			return extJSFiltro.mapError(e.getMessage());

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return extJSFiltro.mapError("Ocurri&oacute un error al tratar de listar las aplicaciones.");
		}

	}
	
}
