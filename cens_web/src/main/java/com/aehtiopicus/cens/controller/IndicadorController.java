
package com.aehtiopicus.cens.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.dto.DetalleIndicadoresDto;
import com.aehtiopicus.cens.dto.IndicadorDto;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoTipoEnum;
import com.aehtiopicus.cens.mapper.IndicadorMapper;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.service.InformeConsolidadoService;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.utils.PeriodoUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndicadorController extends AbstractController{

	private static final Logger logger = Logger.getLogger(IndicadorController.class);
	@Autowired
	private InformeConsolidadoService informeConsolidadoService;
	
	private ClienteService clienteService;
	
	@Autowired
	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public void setInformeConsolidadoService(
			InformeConsolidadoService informeConsolidadoService) {
		this.informeConsolidadoService = informeConsolidadoService;
	}

	@RequestMapping(value = UrlConstant.INDICADORES_GRILLA_URL , method = RequestMethod.GET)
    public  @ResponseBody Map<String, Object>    getIndicadoresGrilla(HttpServletRequest request,HttpServletResponse response) {
		logger.info("obteniendo indicadores paragrilla");

		String periodo1 = Utils.getParameterFromRequest("periodo1", request);
		String periodo2 = Utils.getParameterFromRequest("periodo2", request);
		if(periodo1 == null || periodo2 == null){
			periodo1 = PeriodoUtils.getPeriodoFromDate(new Date());
			periodo2 = PeriodoUtils.getPeriodoFromDate(new Date());
		}
		InformeConsolidado informeConsolidados1 = informeConsolidadoService.getByPeriodoAndTipo(PeriodoUtils.getDateFormPeriodo(periodo1), InformeConsolidadoTipoEnum.COMUN);
		InformeConsolidado informeConsolidados2 = informeConsolidadoService.getByPeriodoAndTipo(PeriodoUtils.getDateFormPeriodo(periodo2), InformeConsolidadoTipoEnum.COMUN);
		List<IndicadorDto> indicadoresDto = IndicadorMapper.getIndicadorDtoFromEmpleados(informeConsolidados1,informeConsolidados2);
        Collections.sort(indicadoresDto);
        ModelAndView mav = new ModelAndView(VistasConstant.INDICADORES_VIEW);
        mav.addObject("indicadorDto", indicadoresDto);
        JqGridData<IndicadorDto> gridData = new JqGridData<IndicadorDto>(1, 1,indicadoresDto.size() , indicadoresDto);
        return gridData.getJsonObject();
		
    }	
	
	@RequestMapping(value = UrlConstant.INDICADORES, method = RequestMethod.GET)
    public ModelAndView getIndicadores(Locale locale, Model model, Principal principal) {

		logger.info("Obteniendo vista indicadores");
		
		ModelAndView mav = new ModelAndView(VistasConstant.INDICADORES_VIEW);
		mav.addObject("periodosDto", PeriodoUtils.generarPeriodos(new Date(), 20));
        return mav;
    }	
	
	@RequestMapping(value = UrlConstant.INDICADORES_DETALLE, method = RequestMethod.GET)
    public ModelAndView getIndicadoresDetalleView(HttpServletRequest request,Locale locale, Model model, Principal principal) {

		logger.info("Obteniendo vista indicadores detalle");
		String periodo1 = Utils.getParameterFromRequest("periodo1", request);
		String periodo2 = Utils.getParameterFromRequest("periodo2", request);
		
		if(periodo1 == null || periodo2 == null){
			periodo1 = PeriodoUtils.getPeriodoFromDate(new Date());
			periodo2 = PeriodoUtils.getPeriodoFromDate(new Date());
		}
		Long clienteId = Long.parseLong(request.getParameter("clienteId"));
		Cliente cliente = clienteService.getClienteById(clienteId);
		ModelAndView mav = new ModelAndView(VistasConstant.INDICADORES_DETALLE_VIEW);
		if(cliente != null){
			mav.addObject("cliente",cliente.getRazonSocial());
		}
		mav.addObject("clienteId",clienteId);
		mav.addObject("periodo1",periodo1);
		mav.addObject("periodo2",periodo2);
	    return mav;
    }	
	
	@RequestMapping(value = UrlConstant.INDICADORES_DETALLE_GRILLA_URL, method = RequestMethod.GET)
    public  @ResponseBody Map<String, Object>  getIndicadoresDetalle(HttpServletRequest request,Locale locale, Model model, Principal principal) {

		logger.info("Obteniendo datos indicadores detalle");
		String periodo1 = Utils.getParameterFromRequest("periodo1", request);
		String periodo2 = Utils.getParameterFromRequest("periodo2", request);
		Long clienteId = Long.parseLong(request.getParameter("clienteId"));
		List<InformeConsolidadoDetalle> informeConsolidados1 = informeConsolidadoService.searchInformesConsolidadosByCliente(periodo1,clienteId);
		List<InformeConsolidadoDetalle> informeConsolidados2 = informeConsolidadoService.searchInformesConsolidadosByCliente(periodo2,clienteId);
		
		List<DetalleIndicadoresDto> detalleIndicadores = IndicadorMapper.getInformeDetalle(informeConsolidados1,informeConsolidados2);
	
		JqGridData<DetalleIndicadoresDto> gridData = new JqGridData<DetalleIndicadoresDto>(1, 1,1 ,detalleIndicadores );
	    return gridData.getJsonObject();
    }	
	
	
}
