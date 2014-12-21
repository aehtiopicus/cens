package com.aehtiopicus.cens.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.domain.InformeConsolidadoStub;
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.dto.ExcelInformeConsolidadoDetalleDataDto;
import com.aehtiopicus.cens.dto.InformeConsolidadoAdicionalDetalleDto;
import com.aehtiopicus.cens.dto.InformeConsolidadoHeaderDto;
import com.aehtiopicus.cens.dto.InformeIntermedioDto;
import com.aehtiopicus.cens.dto.InformeMensualHeaderNuevoDto;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoTipoEnum;
import com.aehtiopicus.cens.enumeration.InformeIntermedioEstadoEnum;
import com.aehtiopicus.cens.enumeration.PerfilEnum;
import com.aehtiopicus.cens.mapper.ClienteMapper;
import com.aehtiopicus.cens.mapper.ExcelMapper;
import com.aehtiopicus.cens.mapper.InformeConsolidadoMapper;
import com.aehtiopicus.cens.mapper.InformeMensualMapper;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.service.InformeConsolidadoReporteService;
import com.aehtiopicus.cens.service.InformeConsolidadoService;
import com.aehtiopicus.cens.service.InformeMensualService;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.utils.PeriodoUtils;
import com.aehtiopicus.cens.validator.InformeConsolidadoValidator;
import com.aehtiopicus.cens.validator.InformeSacValidator;

@Controller
public class InformeConsolidadoController {
	
	private static final Logger logger = Logger.getLogger(InformeConsolidadoController.class);
	private static final String APPLICATION_MEDIA_TYPE = "application";
	@Autowired
	protected InformeConsolidadoService informeConsolidadoService;

	@Autowired
	protected InformeMensualService informeMensualService;

	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected InformeConsolidadoValidator informeConsolidadoValidator;
	
	@Autowired
	protected InformeSacValidator informeSacValidator;
	
	@Autowired
	protected InformeConsolidadoReporteService reporteService;
	
	@Value("${periodo.dias}") 
	protected Integer diasPeriodo;
	
	@Value("${periodo.horas}") 
	protected Integer horasPeriodo;

	@Value("${mes.liquidacion.vacaciones}") 
	protected Integer mesLiquidacionVacaciones;

	@Value("${retenciones.tope}") 
	protected Double topeRetenciones;
	
	@Value("${retenciones.os.porcentaje}") 
	protected Double retOSPorcentaje;

	@Value("${retenciones.11y3.porcentaje1}") 
	protected Double ret11Porcentaje;
	@Value("${retenciones.11y3.porcentaje2}") 
	protected Double ret3Porcentaje;

	@Value("${divisor.vacaciones}") 
	protected Double divisorVacaciones;
	
	@Value("${contribucion.codigos}") 
	protected String contCodigos;
	
	@Value("${contribucion.os.porcentaje}") 
	protected Double contOSPorcentaje;
	
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	
	public void setReporteService(InformeConsolidadoReporteService reporteService) {
		this.reporteService = reporteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public void setInformeMensualService(InformeMensualService informeMensualService) {
		this.informeMensualService = informeMensualService;
	}

	public void setInformeConsolidadoValidator(InformeConsolidadoValidator informeConsolidadoValidator) {
		this.informeConsolidadoValidator = informeConsolidadoValidator;
	}

	public void setInformeConsolidadoService(InformeConsolidadoService informeConsolidadoService) {
		this.informeConsolidadoService = informeConsolidadoService;
	}

	public void setDiasPeriodo(Integer diasPeriodo) {
		this.diasPeriodo = diasPeriodo;
	}

	public void setHorasPeriodo(Integer horasPeriodo) {
		this.horasPeriodo = horasPeriodo;
	}

	public void setMesLiquidacionVacaciones(Integer mesLiquidacionVacaciones) {
		this.mesLiquidacionVacaciones = mesLiquidacionVacaciones;
	}

	public void setTopeRetenciones(Double topeRetenciones) {
		this.topeRetenciones = topeRetenciones;
	}

	public void setRetOSPorcentaje(Double retOSPorcentaje) {
		this.retOSPorcentaje = retOSPorcentaje;
	}

	public void setRet11Porcentaje(Double ret11Porcentaje) {
		this.ret11Porcentaje = ret11Porcentaje;
	}

	public void setRet3Porcentaje(Double ret3Porcentaje) {
		this.ret3Porcentaje = ret3Porcentaje;
	}

	public void setDivisorVacaciones(Double divisorVacaciones) {
		this.divisorVacaciones = divisorVacaciones;
	}

	public void setInformeSacValidator(InformeSacValidator informeSacValidator) {
		this.informeSacValidator = informeSacValidator;
	}
	public void setContCodigos(String contCodigos) {
		this.contCodigos = contCodigos;
	}

	public void setContOSPorcentaje(Double contOSPorcentaje) {
		this.contOSPorcentaje = contOSPorcentaje;
	}

	
	@RequestMapping(value = UrlConstant.INFORME_MENSUAL_NO_CONSOLIDADO_URL, method = RequestMethod.GET)
	public ModelAndView getListInformeIntermedio(Locale locale, Model model, Principal principal, HttpServletRequest request) {
		logger.info("obteniendo lista de informes intermedios");
		List<Cliente> clientes = clienteService.getClientesVigentesOrderByNombre();
		ModelAndView mav = new ModelAndView(VistasConstant.INFORME_INTERVIEW_VIEW);
		mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clientes));
		mav.addObject("estadosDto", InformeMensualMapper.getComboDtoFromEstadoInforme());
		mav.addObject("periodosDto", PeriodoUtils.generarPeriodos(new Date(), 20));
		return mav;
	}
	
	@RequestMapping(value = UrlConstant.INFORME_MENSUAL_NO_CONSOLIDADO_GRILLA_URL, method = RequestMethod.GET)
	 public @ResponseBody Map<String, Object>   informeNoConsolidadoGrilla(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		   logger.info("obtener informacion para llenado de grilla infomres intermedios");
		    String cliente = request.getParameter("cliente");
	        String periodo = request.getParameter("periodo");
	        String gerente = request.getParameter("gerente");
	        String estado = request.getParameter("estado");
	        
	        
	     
	        List<InformeIntermedioDto> informeIntermedioDto = this.getInformesPorConsolidarDto(cliente, periodo, gerente, estado);
	        
	        JqGridData<InformeIntermedioDto> gridData = new JqGridData<InformeIntermedioDto>(1,1,1 , informeIntermedioDto);
	        return gridData.getJsonObject();
	}
	
    @RequestMapping(value = UrlConstant.INFORME_MENSUAL_RECHAZAR_URL, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> rechazarInformeMensual(HttpServletRequest request, Long informeMensualId) {
    	logger.info("Rechazar informe");
    	
    	boolean success = false;
    	if(informeMensualId != null) {
    		success = informeMensualService.rechazarInforme(informeMensualId);
    	}
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("success", success);
    	return result;
	}
   
	
	@RequestMapping(value = UrlConstant.INFORMES_CONSOLIDADOS_URL, method = RequestMethod.GET)
	public ModelAndView informesConsolidados(Locale locale, Model model, Principal principal, HttpServletRequest request) {
		logger.info("Grilla Informes Consolidados");

		ModelAndView mav = new ModelAndView(VistasConstant.INFORMES_CONSOLIDADOS_VIEW);
		mav.addObject("periodosDto", PeriodoUtils.generarPeriodos(new Date(), 20));
		return mav;
	}

	@RequestMapping(value = UrlConstant.INFORMES_CONSOLIDADOS_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> grilla(HttpServletRequest request,	HttpServletResponse response) throws IOException {

		logger.info("obtener informacion para llenado de grilla informes consolidados");

		
		// recuperar valores de paginado
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Integer page = Integer.parseInt(request.getParameter("page"));

		String periodo = Utils.getParameterFromRequest("periodo", request);

		List<InformeConsolidadoStub> informes = informeConsolidadoService.searchInformesConsolidados(periodo, page, rows);

		int pageNumber = page;
		int totalRecords = informeConsolidadoService.getNroTotalInformesConsolidados(periodo);
		int pageTotal = Utils.getNumberOfPages(rows, totalRecords);
		List<InformeConsolidadoHeaderDto> informesDto = InformeConsolidadoMapper.getInformesConsolidadosHeaderDtoFromInformesConsolidadosStub(informes);
		JqGridData<InformeConsolidadoHeaderDto> gridData = new JqGridData<InformeConsolidadoHeaderDto>(pageTotal, pageNumber, totalRecords, informesDto);

		return gridData.getJsonObject();
	}
	
	@RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_NEW_URL, method = RequestMethod.GET)
    public ModelAndView nuevoInformeHeader(Locale locale, Model model, Principal principal, HttpServletRequest request) throws Exception {
	logger.info("Nuevo informe consolidado Form");
        
		String periodoStr = Utils.getParameterFromRequest("periodo", request);
		InformeConsolidado informe = null;
		
		if(periodoStr == null) {
			throw new Exception("parametro incorrecto");
		}
		
		Date periodo = PeriodoUtils.getDateFormPeriodo(periodoStr);
		informe = informeConsolidadoService.getByPeriodoAndTipo(periodo, InformeConsolidadoTipoEnum.COMUN);
		
		if(informe == null) {
			informe = new InformeConsolidado();
			informe.setEstado(InformeConsolidadoEstadoEnum.BORRADOR);
			informe.setPeriodo(periodo);
			informe = informeConsolidadoService.crear(informe);
		}
		
		return new ModelAndView("redirect:" + VistasConstant.INFORME_CONSOLIDADO_VIEW + "/" + informe.getId());
    }	
	
	@RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_EDIT_URL + "/{informeConsolidadoId}", method = RequestMethod.GET)
	public ModelAndView informe(Locale locale, Model model, Principal principal, HttpServletRequest request, @PathVariable("informeConsolidadoId") Long informeConsolidadoId) {
		logger.info("Informe Consolidado");

		InformeConsolidado informe = informeConsolidadoService.getById(informeConsolidadoId);
		InformeConsolidadoHeaderDto informeHeaderDto = InformeConsolidadoMapper.getInformeConsolidadoHeaderDtoFromInformeConsolidado(informe);

		ModelAndView mav = new ModelAndView(VistasConstant.INFORME_CONSOLIDADO_VIEW);
		mav.addObject("informeHeaderDto", informeHeaderDto);
		mav.addObject("mesPagoVacaciones", mesLiquidacionVacaciones);
		mav.addObject("topeRetencion", topeRetenciones);
		mav.addObject("retOSPorc", retOSPorcentaje);
		mav.addObject("ret11Porc", ret11Porcentaje);
		mav.addObject("ret3Porc", ret3Porcentaje);
		mav.addObject("diasPeriodo", diasPeriodo);
		mav.addObject("horasPeriodo", horasPeriodo);
		mav.addObject("divisorVacaciones", divisorVacaciones);
		mav.addObject("contCodigos", contCodigos);
		mav.addObject("contOsPorc", contOSPorcentaje);
		return mav;
	}

	@RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_EXCEL_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getInformeData(HttpServletRequest request) {
		logger.info("Recuperando informacion para informe consolidado (grilla excel)");
		Map<String, Object> result = new HashMap<String, Object>();
		
		Long informeId = null;
		try {
			informeId = Long.parseLong(request.getParameter("idInforme"));
		}catch(Exception e) {
			//TODO agregar algun success=false y hacer algo en el cliente con eso
			return result;
		}
		
		InformeConsolidado informe = informeConsolidadoService.getById(informeId);
		if(informe == null) {
			//TODO agregar algun success=false y hacer algo en el cliente con eso
			return result;
		}
		
		boolean showVacaciones = false;
		boolean showSac = false;
		if(informe.getPeriodo().getMonth()+1 == mesLiquidacionVacaciones) {
			showVacaciones = true;
		}
		if(informe.getPeriodo().getMonth()+1 == 6 || informe.getPeriodo().getMonth()+1 == 12) {
			showSac = true;
		}
		
		
		result = ExcelMapper.getMapFromInformeConsolidado(informe, showVacaciones, showSac);

		return result;
	}
	
	@RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_VERIFICAR_EXISTENCIA, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> verificarExistencia(HttpServletRequest request, String periodo) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		InformeConsolidado informe = informeConsolidadoService.getByPeriodoAndTipo(PeriodoUtils.getDateFormPeriodo(periodo), InformeConsolidadoTipoEnum.COMUN);
		if(informe == null) {
			result.put("existe", false);
		}else {
			result.put("existe", true);
		}

		return result;
	}	
	
	@RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_GET_DETALLE_ADICIONALES, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> obtenerdetalleadicionales(HttpServletRequest request, Long detalleInformeId) {
		logger.info("Recuperando detalle de adicionales");
		Map<String, Object> result = new HashMap<String, Object>();
		
		InformeConsolidadoDetalle detalle = informeConsolidadoService.getDetalleById(detalleInformeId);
		if(detalle == null) {
			result.put("success", false);
			result.put("adicionales", null);
			return result;
		}
		
		List<InformeConsolidadoAdicionalDetalleDto> adicionales = InformeConsolidadoMapper.getInformesConsolidadosAdicionalesDetalleFromBeneficiosCliente(detalle.getInformeMensualDetalle().getBeneficios());
		Collections.sort(adicionales);
		
		result.put("success", true);
		result.put("adicionales", adicionales);
		return result;
	}	
	
    @RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_EXCEL_UPDATE_URL, method = RequestMethod.POST,headers = { "content-type=application/json" })
    public  @ResponseBody Map<String, Object>  save(@RequestBody Map<String,List<ExcelInformeConsolidadoDetalleDataDto>> data) {
    	logger.info("Actualizando Informe");
    	
    	List<ExcelInformeConsolidadoDetalleDataDto> informesDetalleDto = data.get("registros");
    	
    	List<InformeConsolidadoDetalle> detalles = new ArrayList<InformeConsolidadoDetalle>();
    	InformeConsolidadoDetalle detalle;
    	
    	for (ExcelInformeConsolidadoDetalleDataDto detalleDto : informesDetalleDto) {
			detalle = informeConsolidadoService.getDetalleById(detalleDto.getId());
			detalle = InformeConsolidadoMapper.updateDetalleFromDetalleDto(detalle, detalleDto);
			detalles.add(detalle);
		}
    	
    	if(detalles.size() > 0) {
    		detalles = informeConsolidadoService.updateDetalles(detalles);
    	}
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("success", true);
    	result.put("message", "Informe actualizado.");
    	return result;
    }
    
    @RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_FINALIZAR_URL, method = RequestMethod.POST)
    public  @ResponseBody Map<String, Object>  enviarInforme(Long informeConsolidadoId) {
    	logger.info("Finalizar informe");
    	
    	boolean success = false;
    	if(informeConsolidadoId != null) {
    		success = informeConsolidadoService.finalizarInforme(informeConsolidadoId);
    	}
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("success", success);
    	return result;
    }
    
    @RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_DELETE_URL, method = RequestMethod.POST)
    public  @ResponseBody Map<String, Object>  elminarInforme(Long informeConsolidadoId, HttpServletRequest request) {
    	logger.info("Eliminando informe");
    	boolean success = false;
    	String message;
    	
    	if(request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre()) || request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre()) || request.isUserInRole(PerfilEnum.RRHH.getNombre())){
    		 return Utils.getUnauthorizedActionMap();
    	}
    	
		if(informeConsolidadoId == null) {
			success = false;
			message = "No se pudo eliminar el informe";
		}
    	
    	try {
    		success = informeConsolidadoService.eliminarInforme(informeConsolidadoId);
    		message = "Informe eliminado correctamente.";
    	}catch(Exception e) {
    		e.printStackTrace();
    		success = false;
    		message = "Error";
    	}
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("success", success);
    	result.put("message", message);
    	
    	return result;
    }
    
	@RequestMapping(value = UrlConstant.INFORME_CONSOLIDADO_EXCEL_EXPORT_URL, method = RequestMethod.GET)
	public HttpEntity<byte[]> getInformeConsolidadoExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("obtener excel");
		Long idInforme = Long.valueOf(request.getParameter("idInforme"));
		InformeConsolidado informeConsolidado = informeConsolidadoService.getById(idInforme);
		List<InformeConsolidadoDetalle> detalles = informeConsolidado.getDetalle();
		Collections.sort(detalles);

		HSSFWorkbook excel = reporteService.getInformeExcel(detalles,informeConsolidado.getPeriodo());

		File archivoDumpTemporal = File.createTempFile("temp", "xls");
		archivoDumpTemporal.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(archivoDumpTemporal);
		excel.write(fos);
		byte[] encoded = Files.readAllBytes(archivoDumpTemporal.toPath());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType(APPLICATION_MEDIA_TYPE, ".xls"));
		header.set(CONTENT_DISPOSITION, "attachment; filename=\"" + "InformeConsolidado.xls" + "\"");
		header.setContentLength(encoded.length);
		return new HttpEntity<byte[]>(encoded, header);
	}
	 
	
	@RequestMapping(value = UrlConstant.INFORME_MENSUAL_NO_CONSOLIDADO_EXPORT, method = RequestMethod.GET)
	public HttpEntity<byte[]> exportListInformeIntermedio(Locale locale, Model model, Principal principal, HttpServletRequest request) throws ParseException, IOException {
		logger.info("exportando listado Informes menusales por consolidar");
	    String cliente = request.getParameter("cliente");
        String periodo = request.getParameter("periodo");
        String gerente = request.getParameter("gerente");
        String estado = request.getParameter("estado");
        
        List<InformeIntermedioDto> informesDto = this.getInformesPorConsolidarDto(cliente, periodo, gerente, estado);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet worksheet = workbook.createSheet("Estado Informes Mensuales");
		reporteService.crearEncabezadoEstadoInformesMensuales(worksheet, workbook);
		if(!CollectionUtils.isEmpty(informesDto)) {
			int column = 1;
			for (InformeIntermedioDto dto : informesDto) {
				String[] data = {dto.getPeriodo(), dto.getClienteNombre(),dto.getGerenteNombre(),dto.getEstado()};
				reporteService.addRegistroEstadoInformesMensuales(worksheet, workbook, data, column++);
			}
		}

		File archivoDumpTemporal = File.createTempFile("temp", "xls");
		archivoDumpTemporal.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(archivoDumpTemporal);
		workbook.write(fos);
		byte[] encoded = Files.readAllBytes(archivoDumpTemporal.toPath());
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType(APPLICATION_MEDIA_TYPE, ".xls"));
		header.set(CONTENT_DISPOSITION, "attachment; filename=\"" + "EstadoInformesMensuales.xls" + "\"");
		header.setContentLength(encoded.length);
		return new HttpEntity<byte[]>(encoded, header);
			
	}
	
	private List<InformeIntermedioDto> getInformesPorConsolidarDto(String cliente, String periodo, String gerente, String estado) throws ParseException{
        List<Cliente> clientes = new ArrayList<Cliente>();

        if(StringUtils.isEmpty(cliente)){
//        	clientes = clienteService.getClientes();	
        	clientes = clienteService.getClientesVigentesHastaPeriodo(periodo);	
        }else{
        	try{
        		Long clienteId = Long.parseLong(cliente);
        		clientes.add(clienteService.getClienteById(clienteId));	
        	}catch(NumberFormatException e){
        		logger.error("formato incorrecto id cliente");
        		clientes = clienteService.getClientes();	
        	}
        	
        }
        
        List<InformeMensual> informes = informeMensualService.getInformesNoConsolidadosPaginado(cliente,periodo,gerente,estado,clientes);
        

        if(!StringUtils.isEmpty(estado) && InformeIntermedioEstadoEnum.getInformeMensualEstadoEnumFromString(estado) != null && InformeIntermedioEstadoEnum.getInformeMensualEstadoEnumFromString(estado).equals(InformeIntermedioEstadoEnum.PENDIENTE)){
        	   clientes = informeMensualService.getClientesSinInformes(clientes,periodo);
        }
     
        List<InformeIntermedioDto> informeIntermedioDto = InformeMensualMapper.getInformeIntermedioDtoFromInformeMensual(informes,clientes,estado,periodo);
        return informeIntermedioDto;
	}
	
	 
	@RequestMapping(value = UrlConstant.INFORME_SAC_NEW_URL, method = RequestMethod.GET)
    public ModelAndView nuevoInformeSac(Locale locale, Model model, Principal principal, HttpServletRequest request) {
		logger.info("Nuevo informe SAC form");
		
        ModelAndView mav = new ModelAndView(VistasConstant.INFORME_SAC_NEW_VIEW);
		mav.addObject("periodosDto", PeriodoUtils.generarPeriodosSAC(new Date().getYear()+1900, 6));
        mav.addObject("informeConsolidadoHeaderDto", new InformeConsolidadoHeaderDto());
        return mav;
    }	
	
	@RequestMapping(value = UrlConstant.INFORME_SAC_NEW_URL, method = RequestMethod.POST)
    public ModelAndView nuevoInformeSacSubmit(Locale locale, Model model, Principal principal, InformeConsolidadoHeaderDto informeConsolidadoHeaderDto, HttpServletRequest request, BindingResult result) {
		logger.info("Nuevo informe SAC form submit");
		logger.info("Periodo seleccionado: " + informeConsolidadoHeaderDto.getPeriodo());
		
		ModelAndView mav;
        
		informeSacValidator.validate(informeConsolidadoHeaderDto, result);
        
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.INFORME_SAC_NEW_VIEW);
			mav.addObject("periodosDto", PeriodoUtils.generarPeriodosSAC(new Date().getYear()+1900, 6));
	        mav.addObject("informeConsolidadoHeaderDto", informeConsolidadoHeaderDto);
	        return mav;
		}
		
		InformeConsolidado informe = InformeConsolidadoMapper.getInformeConsolidadoFromInformeConsolidadoHeaderDto(informeConsolidadoHeaderDto);
		informe.setTipo(InformeConsolidadoTipoEnum.SAC);

		informe = informeConsolidadoService.crearInformeSac(informe);
		
		return new ModelAndView("redirect:" + VistasConstant.INFORME_CONSOLIDADO_VIEW + "/" + informe.getId());
    }	
	
}
