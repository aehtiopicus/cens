package com.aehtiopicus.cens.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.domain.InformeMensualDetalle;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.ExcelInformeMensualDetalleDataDto;
import com.aehtiopicus.cens.dto.InformeMensualHeaderDto;
import com.aehtiopicus.cens.dto.InformeMensualHeaderNuevoDto;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;
import com.aehtiopicus.cens.enumeration.PerfilEnum;
import com.aehtiopicus.cens.mapper.ClienteMapper;
import com.aehtiopicus.cens.mapper.ExcelMapper;
import com.aehtiopicus.cens.mapper.InformeMensualMapper;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.service.InformeMensualReporteService;
import com.aehtiopicus.cens.service.InformeMensualService;
import com.aehtiopicus.cens.service.UsuarioService;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.utils.PeriodoUtils;
import com.aehtiopicus.cens.validator.InformeMensualValidator;

@Controller
public class InformeMensualController extends AbstractController{

	private static final Logger logger = Logger.getLogger(InformeMensualController.class);
	
	@Autowired
	protected InformeMensualService informeMensualService;

	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected UsuarioService usuarioService;
	
	@Autowired
	protected InformeMensualValidator informeMensualValidator;
	
	@Autowired
	protected InformeMensualReporteService reporteService;
	
	public void setInformeMensualService(InformeMensualService informeMensualService) {
		this.informeMensualService = informeMensualService;
	}
	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	public void setInformeMensualValidator(InformeMensualValidator informeMensualValidator) {
		this.informeMensualValidator = informeMensualValidator;
	}
	public void setReporteService(InformeMensualReporteService reporteService) {
		this.reporteService = reporteService;
	}

	private Usuario getUsuarioAutenticado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = usuarioService.getUsuarioByUsername(username);

		return usuario;
	}

	@RequestMapping(value = UrlConstant.INFORMES_MENSUALES_URL, method = RequestMethod.GET)
	public ModelAndView informesMensuales(Locale locale, Model model, Principal principal, HttpServletRequest request) {
		logger.info("Grilla Informes Mensuales");

		// obtengo el usuario logueado:
		Usuario usuario = getUsuarioAutenticado();

		// obtengo lista de clientes
		List<Cliente> clientes = null;
		// si el usuario es administrador me tengo que traer todos los clientes
		if (request.isUserInRole(PerfilEnum.ADMINISTRADOR.getNombre())) {
			clientes = clienteService.getClientesVigentesOrderByNombre();
			
		// si el usuario es un gte de operaciones solo debo traer sus clientes
		} else if (request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre())) {
			clientes = clienteService.getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);

		// si el usuario es un jefe de operaciones solo debo traer sus clientes
		} else if (request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
			clientes = clienteService.getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
		}
		
		ModelAndView mav = new ModelAndView(VistasConstant.INFORMES_MENSUALES_VIEW);
		mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clientes));
		mav.addObject("periodosDto", PeriodoUtils.generarPeriodos(new Date(), 20));
		return mav;
	}

	@RequestMapping(value = UrlConstant.INFORMES_MENSUALES_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> grilla(HttpServletRequest request,	HttpServletResponse response) throws IOException {

		logger.info("obtener informacion para llenado de grilla informes mensuales");

		// obtengo el usuario logueado:
		Usuario usuario = null;
		if (request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre()) || request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
			usuario = getUsuarioAutenticado();
		}		
			
		
		// recuperar valores de paginado
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Integer page = Integer.parseInt(request.getParameter("page"));

		String cliente = Utils.getParameterFromRequest("cliente", request);
		String periodo = Utils.getParameterFromRequest("periodo", request);

		List<InformeMensual> informes = informeMensualService.searchInformesMensuales(usuario, cliente, periodo, page, rows);

		int pageNumber = page;
		int totalRecords = informeMensualService.getNroTotalInformesMensuales(usuario, cliente, periodo);
		int pageTotal = Utils.getNumberOfPages(rows, totalRecords);
		List<InformeMensualHeaderDto> informesDto = InformeMensualMapper.getInformesMensualesHeaderDtoFromInformesMensuales(informes);
		JqGridData<InformeMensualHeaderDto> gridData = new JqGridData<InformeMensualHeaderDto>(pageTotal, pageNumber, totalRecords, informesDto);

		return gridData.getJsonObject();
	}
	
	@RequestMapping(value = UrlConstant.INFORME_MENSUAL_NEW_URL, method = RequestMethod.GET)
    public ModelAndView nuevoInformeHeader(Locale locale, Model model, Principal principal, HttpServletRequest request) {
		logger.info("Nuevo informe mensual Form");
        
		// obtengo el usuario logueado:
		Usuario usuario = getUsuarioAutenticado();
		
		// obtengo lista de clientes
		List<Cliente> clientes = null;
		// si el usuario es administrador me tengo que traer todos los clientes
		if (request.isUserInRole(PerfilEnum.ADMINISTRADOR.getNombre())) {
			clientes = clienteService.getClientesVigentesOrderByNombre();
			
		// si el usuario es un gte de operaciones solo debo traer sus clientes
		} else if (request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre())) {
			clientes = clienteService.getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
		
		// si el usuario es un jefe de operaciones solo debo traer sus clientes
		} else if (request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
			clientes = clienteService.getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
		}

		
        ModelAndView mav = new ModelAndView(VistasConstant.INFORME_MENSUAL_NEW_VIEW);
		mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clientes));
		mav.addObject("periodosDto", PeriodoUtils.generarPeriodos(new Date(), 3));
        mav.addObject("informeMensualHeaderNuevoDto", new InformeMensualHeaderNuevoDto());
        return mav;
    }	
	
	@RequestMapping(value = UrlConstant.INFORME_MENSUAL_NEW_URL, method = RequestMethod.POST)
    public ModelAndView nuevoInformeHeaderSubmit(Locale locale, Model model, Principal principal, HttpServletRequest request, @Valid InformeMensualHeaderNuevoDto informeMensualHeaderDto,BindingResult result) {
		logger.info("Nuevo informe mensual Form submit");
        
        Usuario usuario = getUsuarioAutenticado();
        
        informeMensualValidator.validate(informeMensualHeaderDto, result);
        
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			List<Cliente> clientes = new ArrayList<Cliente>();
	        ModelAndView mav = new ModelAndView(VistasConstant.INFORME_MENSUAL_NEW_VIEW);
	        // si el usuario es administrador me tengo que traer todos los clientes
			if (request.isUserInRole(PerfilEnum.ADMINISTRADOR.getNombre())) {
				clientes = clienteService.getClientesVigentesOrderByNombre();
				
			// si el usuario es un gte de operaciones solo debo traer sus clientes
			} else if (request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre())) {
				clientes = clienteService.getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);

			// si el usuario es un jefe de operaciones solo debo traer sus clientes
			} else if (request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
				clientes = clienteService.getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, usuario);
			}

			mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clientes));
			mav.addObject("periodosDto", PeriodoUtils.generarPeriodos(new Date(), 3));
	        mav.addObject("informeMensualHeaderNuevoDto", informeMensualHeaderDto);
	        return mav;
		}
        
		//creo el nuevo informe:
		InformeMensual informeMensual = InformeMensualMapper.getInformeMensualFromInformeMensualHeaderNuevoDto(informeMensualHeaderDto);
		informeMensual = informeMensualService.crear(informeMensual, informeMensualHeaderDto.getUsarInformeAnterior());
		
        ModelAndView mav = new ModelAndView("redirect:" + VistasConstant.INFORME_MENSUAL_VIEW + "/" + informeMensual.getId());
        return mav;
    }
	

	@RequestMapping(value = UrlConstant.INFORME_MENSUAL_EDIT_URL + "/{informeMensualId}", method = RequestMethod.GET)
	public ModelAndView informeMensual(Locale locale, Model model, Principal principal, HttpServletRequest request, @PathVariable("informeMensualId") Long informeMensualId) {
		logger.info("Informe Mensual");

		// obtengo el usuario logueado:
		//Usuario usuario = getUsuarioAutenticado();

		InformeMensual informe = informeMensualService.getById(informeMensualId);
		InformeMensualHeaderDto informeHeaderDto = InformeMensualMapper.getInformeMensualHeaderDtoFromInformeMensual(informe);

		ModelAndView mav = new ModelAndView(VistasConstant.INFORME_MENSUAL_VIEW);
		mav.addObject("informeHeaderDto", informeHeaderDto);
		return mav;
	}
	
	

	@RequestMapping(value = UrlConstant.INFORME_MENSUAL_EXCEL_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getInformeMensualData(HttpServletRequest request) {
		logger.info("Recuperando informacion para informe mensual (grilla excel)");
		Map<String, Object> result = new HashMap<String, Object>();
		
		Long informeId = null;
		try {
			informeId = Long.parseLong(request.getParameter("idInforme"));
		}catch(Exception e) {
			//TODO agregar algun success=false y hacer algo en el cliente con eso
			return result;
		}
		
		InformeMensual informe = informeMensualService.getById(informeId);
		if(informe == null) {
			//TODO agregar algun success=false y hacer algo en el cliente con eso
			return result;
		}
		
		result = ExcelMapper.getMapFromInformeMensual(informe);

		return result;
	}
	
    @RequestMapping(value = UrlConstant.INFORME_MENSUAL_EXCEL_UPDATE_URL, method = RequestMethod.POST,headers = { "content-type=application/json" })
    public  @ResponseBody Map<String, Object>  save(@RequestBody Map<String,List<ExcelInformeMensualDetalleDataDto>> data) {
    	logger.info("Actualizando Informe");
    	
    	List<ExcelInformeMensualDetalleDataDto> informesDetalleDto = data.get("registros");
    	
    	List<InformeMensualDetalle> detalles = new ArrayList<InformeMensualDetalle>();
    	InformeMensualDetalle detalle;
    	
    	for (ExcelInformeMensualDetalleDataDto detalleDto : informesDetalleDto) {
			detalle = informeMensualService.getDetalleById(detalleDto.getId());
			detalle = InformeMensualMapper.updateDetalleFromDetalleDto(detalle, detalleDto);
			detalles.add(detalle);
		}
    	
    	if(detalles.size() > 0) {
    		detalles = informeMensualService.updateDetalles(detalles);
    	}
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("success", true);
    	result.put("message", "Informe actualizado.");
    	return result;
    }
    
    @RequestMapping(value = UrlConstant.INFORME_MENSUAL_ENVIAR_URL, method = RequestMethod.POST)
    public  @ResponseBody Map<String, Object>  enviarInforme(Long informeMensualId) {
    	logger.info("Enviando informe");
    	
    	boolean success = false;
    	if(informeMensualId != null) {
    		success = informeMensualService.enviarInforme(informeMensualId);
    	}
    	
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("success", success);
    	return result;
    }
    
    @RequestMapping(value = UrlConstant.INFORME_MENSUAL_DELETE_URL, method = RequestMethod.POST)
    public  @ResponseBody Map<String, Object>  elminarInforme(Long informeMensualId, HttpServletRequest request) {
    	logger.info("Eliminando informe");
    	boolean success = false;
    	String message;
    	
    	if(request.isUserInRole(PerfilEnum.ADMINISTRACION.getNombre()) || request.isUserInRole(PerfilEnum.RRHH.getNombre())){
    		 return Utils.getUnauthorizedActionMap();
    	}
    	
		if(informeMensualId == null) {
			success = false;
			message = "No se pudo eliminar el informe";
		}
    	
    	try {
    		success = informeMensualService.eliminarInforme(informeMensualId);
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
    
	 @RequestMapping(value = UrlConstant.INFORME_MENSUAL_EXCEL_EXPORT_URL, method = RequestMethod.GET)
	 public HttpEntity<byte[]>   getInformeMensualExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		logger.info("obtener excel - informe operaciones");
		Long idInforme = Long.valueOf(request.getParameter("idInforme"));
		InformeMensual informe = informeMensualService.getById(idInforme);
		List<InformeMensualDetalle> detalles = informe.getDetalle();
		Collections.sort(detalles);
	
		HSSFWorkbook excel = reporteService.getInformeExcel(detalles,informe.getPeriodo(), informe.getCliente());

		File archivoDumpTemporal = File.createTempFile("temp", "xls");
		archivoDumpTemporal.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(archivoDumpTemporal);
		excel.write(fos);
		byte[] encoded = Files.readAllBytes(archivoDumpTemporal.toPath());
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", ".xls"));
		header.set("Content-Disposition", "attachment; filename=\""	+ "InformeMensual.xls" + "\"");
		header.setContentLength(encoded.length);
		
		return new HttpEntity<byte[]>(encoded, header);
	 }
}






