package com.aehtiopicus.cens.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.dto.PagoHeaderDto;
import com.aehtiopicus.cens.service.CompresorService;
import com.aehtiopicus.cens.service.InformeConsolidadoService;
import com.aehtiopicus.cens.service.LiquidacionService;
import com.aehtiopicus.cens.service.LiquidacionTxtServiceImpl;
import com.aehtiopicus.cens.util.Utils;

@Controller
public class LiquidacionReporteController {

	private static final Logger logger = Logger.getLogger(LiquidacionReporteController.class);
	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	private InformeConsolidadoService informeConsolidadoService;
	private CompresorService compresor;
	private static final String APPLICATION_MEDIA_TYPE = "application";
    
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
	@Autowired
	@Qualifier("liquidacionTxtService")
	private LiquidacionService liquidacionTxtService;

	@Autowired
	@Qualifier("liquidacionExcelService")
	private LiquidacionService liquidacionExcelService;
	
	@Autowired
	public void setCompresor(CompresorService compresor) {
		this.compresor = compresor;
	}

	
	@Autowired
	public void setInformeConsolidadoService(
			InformeConsolidadoService informeConsolidadoService) {
		this.informeConsolidadoService = informeConsolidadoService;
	}


	public void setLiquidacionTxtService(LiquidacionService liquidacionTxtService) {
		this.liquidacionTxtService = liquidacionTxtService;
	}

	public void setLiquidacionExcelService(
			LiquidacionService liquidacionExcelService) {
		this.liquidacionExcelService = liquidacionExcelService;
	}


	@RequestMapping(value = UrlConstant.PAGO_URL, method = RequestMethod.GET)
    public ModelAndView getPagos(Locale locale, Model model, Principal principal,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		ModelAndView mav = new ModelAndView(VistasConstant.PAGO_VIEW);
		String periodo = request.getParameter("periodo");
		mav.addObject("periodo", periodo);
		mav.addObject("pagoHeaderDto",new PagoHeaderDto());
	
		return mav;
	}
	@RequestMapping(value = UrlConstant.PAGO_VALIDACION_URL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> validarPagos( Locale locale, Model model, Principal principal,HttpServletRequest request,HttpServletResponse response) throws IOException {
    	Map<String, Object> data  = new HashMap<String, Object>();
    	boolean success = true;
    	String message = "";
    	String referencia = request.getParameter("concepto");
		String periodo =  request.getParameter("periodo");
		String fechaAcr =  request.getParameter("fechaAcr");
		if(StringUtils.isEmpty(periodo) ){
			success = false;
		}
		if(StringUtils.isEmpty(referencia) ){
			success = false;
		}
		if(StringUtils.isEmpty(fechaAcr) ){
			success = false;
		}
		try{
			Utils.sdf.parse(fechaAcr);
		}catch(Exception e){
			success= false;
		}
		if(success == true){
			List<InformeConsolidado> informes = informeConsolidadoService.searchInformesConsolidados(periodo);
			List<InformeConsolidadoDetalle> detalle = 	getDetalleInforme(informes);
			if(CollectionUtils.isEmpty(detalle)){
				message = mensajes.getString("ERR032");
				success = false;
				data.put("success", success);
		  	    data.put("message",message);	
				return data;
			}else {
				List<String> empleados = new ArrayList<String>();
				Empleado e = null;
				for (InformeConsolidadoDetalle det : detalle) {
					e = null;
					if(det.getInformeMensualDetalle() != null && det.getInformeMensualDetalle().getRelacionLaboral().getEmpleado().getBanco() == null) {
						e = det.getInformeMensualDetalle().getRelacionLaboral().getEmpleado();
					}
					else if(det.getEmpleado() != null && det.getEmpleado().getBanco() == null) {
						e = det.getEmpleado();
					}

					if(e != null) {
						empleados.add(e.getApellidos() + ", " + e.getNombres() + " - Leg: " + e.getLegajo());

					}
				}
				if(empleados.size() > 0) {
					success = false;
					message = "Empleados sin bancos";
					data.put("empleados", empleados);
				}
			}
		}
		if(success == false){
			message = mensajes.getString("ERR031");
		}
		
    	data.put("success", success);
  	    data.put("message",message);	
		return data;
	}
	

	/**
	 * retorna un zip con todos los archivos generados para el pago
	 * de sueldos en bancos que no son galicia
	 * @param locale
	 * @param model
	 * @param principal
	 * @param request
	 * @param response
	 * @return 
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping(value = UrlConstant.CREAR_PAGO_URL, method = RequestMethod.GET)	
    private HttpEntity<byte[]> crearPago(@Valid PagoHeaderDto pagoDto,Locale locale, Model model, Principal principal,HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		logger.info("Creando txt pago bancos que no son galicia");
		List<String> filenames = new ArrayList<String>();
		String referencia = pagoDto.getConcepto();
		String periodo =  pagoDto.getPeriodo();
		String fechaAcr =  request.getParameter("fechaAcr");
		List<InformeConsolidado> informes = informeConsolidadoService.searchInformesConsolidados(periodo);
		List<InformeConsolidadoDetalle> detalle = 	getDetalleInforme(informes);
		if(CollectionUtils.isNotEmpty(detalle)){
			Date fechaAcreditacion = Utils.sdf.parse(fechaAcr);
			filenames = liquidacionTxtService.generarArchivosLiquidacion(fechaAcreditacion, detalle, referencia,periodo);
			Map<Long, InformeConsolidadoDetalle> galiciaDetalle = LiquidacionTxtServiceImpl.getGalicia();
			if(!galiciaDetalle.isEmpty()){
				List<String> filenamesExcel = liquidacionExcelService.generarArchivosLiquidacion(fechaAcreditacion,getInformesConsolidadosGalicia(galiciaDetalle) , referencia,periodo);
				filenames.addAll(filenamesExcel);
					
			}
		}
		ByteArrayOutputStream baos = compresor.comprimirPagos(filenames);
	    HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType(APPLICATION_MEDIA_TYPE, ".zip"));
	    header.set(CONTENT_DISPOSITION, "attachment; filename=\""+"Pagos.zip"+"\"");
	    header.setContentLength(baos.toByteArray().length);
	    deleteFiles(filenames);
	    return new HttpEntity<byte[]>(baos.toByteArray(), header);
	 }



	private List<InformeConsolidadoDetalle> getInformesConsolidadosGalicia(
			Map<Long, InformeConsolidadoDetalle> galiciaDetalle) {
		List<InformeConsolidadoDetalle> detalles = new ArrayList<InformeConsolidadoDetalle>();
		for(InformeConsolidadoDetalle ic : galiciaDetalle.values()){
			detalles.add(ic);
		}
		return detalles;
	}


	private List<InformeConsolidadoDetalle> getDetalleInforme(List<InformeConsolidado> informes) {
		List<InformeConsolidadoDetalle> detalle =null;
		if(CollectionUtils.isNotEmpty(informes)){
			detalle= informes.get(0).getDetalle();
		}
		return detalle;
	}
	

	private void deleteFiles(List<String> filenames) {
		for(String namefile : filenames){
			File file = new File(namefile);
			file.delete();
		}
		
	}

	
}
