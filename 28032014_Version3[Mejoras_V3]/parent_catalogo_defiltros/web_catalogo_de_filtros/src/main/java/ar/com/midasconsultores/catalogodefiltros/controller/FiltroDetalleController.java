package ar.com.midasconsultores.catalogodefiltros.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.com.midasconsultores.catalogodefiltros.configuration.UrlConstants;
import ar.com.midasconsultores.catalogodefiltros.configuration.ViewConstants;
import ar.com.midasconsultores.catalogodefiltros.dto.FiltroDTO;
import ar.com.midasconsultores.catalogodefiltros.mappers.FiltroMode;
import ar.com.midasconsultores.catalogodefiltros.mappers.FiltrosMapper;
import ar.com.midasconsultores.catalogodefiltros.service.FiltroService;
import ar.com.midasconsultores.utils.extjs.ResponseMap;

@Controller
@RequestMapping(value=UrlConstants.DETALLE_FILTRO_URL)
public class FiltroDetalleController {

	private static final Logger logger = LoggerFactory.getLogger(FiltroDetalleController.class);
	
	@Autowired
	private ResponseMap<FiltroDTO> extJS;

	@Autowired
	FiltroService filtroService;

	@Autowired
	FiltrosMapper filtroMapper;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView getDetallePedidoPage(@PathVariable("id") long id){
		FiltroDTO dto = filtroMapper.map(filtroService.get(id), FiltroMode.FILTRO_PRECIO);
		ModelAndView mav = new ModelAndView(ViewConstants.DETALLE_FILTRO_VIEW);
		mav.addObject("filtro", extJS.mapOK(Arrays.asList(dto), 1l));
		return mav;
	}
	
}
