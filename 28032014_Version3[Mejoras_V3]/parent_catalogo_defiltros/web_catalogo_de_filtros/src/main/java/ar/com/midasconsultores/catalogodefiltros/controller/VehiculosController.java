package ar.com.midasconsultores.catalogodefiltros.controller;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.midasconsultores.catalogodefiltros.configuration.UrlConstants;
import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;
import ar.com.midasconsultores.catalogodefiltros.dto.VehiculoDTO;
import ar.com.midasconsultores.catalogodefiltros.mappers.VehiculosMapper;
import ar.com.midasconsultores.catalogodefiltros.service.FiltroService;
import ar.com.midasconsultores.catalogodefiltros.service.VehiculoService;
import ar.com.midasconsultores.utils.ExcepcionDeNegocios;
import ar.com.midasconsultores.utils.extjs.ResponseMap;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = UrlConstants.APLICACIONES_FILTRO_URL)
public class VehiculosController {

	private static final Logger logger = LoggerFactory
			.getLogger(VehiculosController.class);

	@Autowired
	private ResponseMap<VehiculoDTO> extJS;
	
	@Autowired(required = true)
	VehiculoService vehiculoService;

	@Autowired(required = true)
	FiltroService filtroService;

	@Autowired
	VehiculosMapper vehiculosMapper;

	@RequestMapping(value = UrlConstants.APLICACIONES_FILTRO_LIST_URL)
	public @ResponseBody
	Map<String, ? extends Object> list(Locale locale, 
			@RequestParam int page,
			@RequestParam int rows,
			@RequestParam(value = "idFiltro", required = false) Long idFiltro)
			throws Exception {

		int start = (page-1) * rows;
		int limit = rows;
		
		try {
			Page<VehiculoDTO> vehiculoDTO = null;
			long total = 0;
			if (idFiltro != null) {
				Page<Vehiculo> vehiculos = vehiculoService.list(page - 1, start, limit, filtroService.get(idFiltro));
				vehiculoDTO = new PageImpl<VehiculoDTO>(vehiculosMapper.mapToDTOList(vehiculos.getContent()));
				total = vehiculos.getTotalElements();
			} else {
				vehiculoDTO = new PageImpl<VehiculoDTO>(new ArrayList<VehiculoDTO>());
			}
			logger.debug("products : " + vehiculoDTO.getContent());

			return extJS.mapOK(vehiculoDTO.getContent(), total, page, rows);

		} catch (ExcepcionDeNegocios e) {
			return extJS.mapError(e.getMessage());
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			return extJS.mapError("Ocurri&oacute un error al tratar de listar las aplicaciones.");
		}

	}

}
