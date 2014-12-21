package com.aehtiopicus.cens.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.RelacionPuestoEmpleado;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.ComboStrDto;
import com.aehtiopicus.cens.dto.HistorialNominaDto;
import com.aehtiopicus.cens.dto.IncrementoSalarialDto;
import com.aehtiopicus.cens.dto.IncrementoSalarialEmpleadoDto;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.dto.RelacionLaboralDto;
import com.aehtiopicus.cens.dto.RelacionPuestoEmpleadoDto;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;
import com.aehtiopicus.cens.enumeration.PerfilEnum;
import com.aehtiopicus.cens.enumeration.TipoBeneficioEnum;
import com.aehtiopicus.cens.helper.EmpleadoSecurityHelper;
import com.aehtiopicus.cens.helper.RelacionLaboralSecurityHelper;
import com.aehtiopicus.cens.mapper.ClienteMapper;
import com.aehtiopicus.cens.mapper.EmpleadoMapper;
import com.aehtiopicus.cens.mapper.RelacionLaboralMapper;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.service.EmpleadoService;
import com.aehtiopicus.cens.service.RelacionLaboralService;
import com.aehtiopicus.cens.service.UsuarioService;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.validator.IncrementoSalarialValidator;
import com.aehtiopicus.cens.validator.RelacionLaboralValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RelacionLaboralController extends AbstractController{

	 private static final Logger logger = Logger.getLogger(RelacionLaboralController.class);

    @Autowired
    protected RelacionLaboralService relacionLaboralService;

    @Autowired
    protected ClienteService clienteService;
    
    @Autowired
    protected EmpleadoService empleadoService;

    @Autowired
    protected RelacionLaboralValidator relacionLaboralValidator;

    @Autowired
    protected IncrementoSalarialValidator incrementoSalarialValidator;
    
    @Autowired
    protected UsuarioService usuarioService;
    
    @Autowired
    protected RelacionLaboralSecurityHelper relacionLaboralSecurityHelper;
    
    
    public void setRelacionLaboralSecurityHelper(
			RelacionLaboralSecurityHelper relacionLaboralSecurityHelper) {
		this.relacionLaboralSecurityHelper = relacionLaboralSecurityHelper;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setRelacionLaboralService(RelacionLaboralService relacionLaboralService) {
		this.relacionLaboralService = relacionLaboralService;
	}
    
	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	public void setRelacionLaboralValidator(
			RelacionLaboralValidator relacionLaboralValidator) {
		this.relacionLaboralValidator = relacionLaboralValidator;
	}
	public void setIncrementoSalarialValidator(
			IncrementoSalarialValidator incrementoSalarialValidator) {
		this.incrementoSalarialValidator = incrementoSalarialValidator;
	}

	@RequestMapping(value = UrlConstant.ASIGNACION_EMPLEADOS_URL, method = RequestMethod.GET)
    public ModelAndView usuariosGrilla(Locale locale, Model model, Principal principal) {
        logger.info("Grilla asignacion de empleados");
        
        ModelAndView mav = new ModelAndView(VistasConstant.ASIGNACION_EMPLEADOS_VIEW);
        
        mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesOrderByNombre()));
        return mav;
    }


	@RequestMapping(value = UrlConstant.ASIGNACION_EMPLEADOS_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> usuariosGrilla(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		   logger.info("obtener informacion para llenado de grilla usuarios");
		   
	       // recuperar valores de paginado
	        Integer rows = Integer.parseInt(request.getParameter("rows"));
	        Integer page = Integer.parseInt(request.getParameter("page"));
	        logger.info("recuperando "+rows+" relaciones laborales de la pagina "+page);
	        
	        String cliente = Utils.getParameterFromRequest("cliente", request);
	        String nombre = Utils.getParameterFromRequest("nombre", request);
	        String apellido = Utils.getParameterFromRequest("apellido", request);
	        
	        List<RelacionLaboral> relacionesLaborales = relacionLaboralService.search(cliente, apellido, nombre,  page, rows);
	        
	        int pageNumber = page; 
	        int totalRecords = relacionLaboralService.getTotalRelacionesLaboralesFilterByCliente(cliente, apellido, nombre);
	        int pageTotal = Utils.getNumberOfPages(rows,totalRecords);
	        
	        List<RelacionLaboralDto> relacionesLaboralesDto = RelacionLaboralMapper.getRelacionesLaboralesDtoFromRelacionesLaborales(relacionesLaborales);
	        relacionesLaboralesDto = relacionLaboralSecurityHelper.addSecurityToActionsGrillaRelacionesLaborales(request, relacionesLaboralesDto);
	        JqGridData<RelacionLaboralDto> gridData = new JqGridData<RelacionLaboralDto>(pageTotal, pageNumber,totalRecords , relacionesLaboralesDto);
	        
	        return gridData.getJsonObject();
	    }
	
	
	
	@RequestMapping(value = UrlConstant.ASIGNACION_EMPLEADO_NEW_URL + "/{empleadoId}", method = RequestMethod.GET)
    public ModelAndView usuarioFormNew(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("empleadoId") Long empleadoId) {

		logger.info("Relacion Laboral Form -> Nuevo");
        
        ModelAndView mav = new ModelAndView(VistasConstant.ASIGNACION_EMPLEADO_NEW_VIEW);
        
        Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(empleadoId);
        RelacionLaboralDto relacionLaboral = RelacionLaboralMapper.createRelacionLaboralDtoForEmpleado(empleado);
        mav.addObject("relacionLaboralDto", relacionLaboral);
        this.loadModelForAsignacionEmpleadoForm(mav, request);

        return mav;
    }

	@RequestMapping(value = UrlConstant.ASIGNACION_EMPLEADO_NEW_URL + "/{empleadoId}", method = RequestMethod.POST)
    public ModelAndView usuarioFormNewSubmit(HttpServletRequest request, Locale locale, Model model, Principal principal, @Valid RelacionLaboralDto relacionLaboralDto, BindingResult result) {

		logger.info("Relacion Laboral Form -> Nuevo (Submit)");
        ModelAndView mav = null;
        relacionLaboralValidator.validateFechas(relacionLaboralDto,result);
		 //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.ASIGNACION_EMPLEADO_NEW_VIEW);
			Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(relacionLaboralDto.getEmpleadoId());
			relacionLaboralDto.setNombreEmpleado(empleado.getApellidos() + ", " + empleado.getNombres());
		    mav.addObject("relacionLaboralDto", relacionLaboralDto);
			this.loadModelForAsignacionEmpleadoForm(mav, request);
	        
	        return mav;
		}
        
		RelacionLaboral relacionLaboral = RelacionLaboralMapper.getRelacionLaboralFromRelacionLaboralDto(relacionLaboralDto);
		relacionLaboralService.createAndSave(relacionLaboral);
		
		mav = new ModelAndView("redirect:" + UrlConstant.ASIGNACION_EMPLEADOS_URL);
        return mav;
    }
	
	private void loadModelForAsignacionEmpleadoForm(ModelAndView mav, HttpServletRequest request){
		
		if(request.isUserInRole(PerfilEnum.GTE_OPERACION.getNombre())) {
			Usuario gerente = getUsuarioAutenticado();
			mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesByGteOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, gerente)));
		
		}else if(request.isUserInRole(PerfilEnum.JEFE_OPERACION.getNombre())) {
			Usuario gerente = getUsuarioAutenticado();
			mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesByJefeOperacionOrderByRazonSocial(EstadoClienteEnum.Vigente, gerente)));
		
		}else{
	        mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesOrderByNombre()));			
		}
        
        mav.addObject("puestosDto", RelacionLaboralMapper.getComboDtosFromPuestos(relacionLaboralService.getPuestos()));
	}
	

	@RequestMapping(value = UrlConstant.ASIGNACION_EMPLEADO_EDIT_URL + "/{relacionLaboralId}", method = RequestMethod.GET)
    public ModelAndView usuarioFormEdit(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("relacionLaboralId") Long relacionLaboralId) {

		logger.info("Relacion Laboral Form -> Edit");
        
	    ModelAndView mav = new ModelAndView(VistasConstant.ASIGNACION_EMPLEADO_EDIT_VIEW);
	      
        RelacionLaboral relacionLaboral = relacionLaboralService.getRelacionLaboralById(relacionLaboralId);
        if(!relacionLaboralSecurityHelper.elUsuarioTieneAcceso(request, relacionLaboral, RelacionLaboralSecurityHelper.ACCION_DESASIGNAR)) {
        	return Utils.getUnauthorizedActionModelAndView();
        }
        
        RelacionLaboralDto relacionLaboralDto = RelacionLaboralMapper.getRelacionLaboralDtoFromRelacionLaboral(relacionLaboral);

        mav.addObject("relacionLaboralDto", relacionLaboralDto);

        return mav;
    }
	
	@RequestMapping(value = UrlConstant.ASIGNACION_PUESTO_URL + "/{relacionLaboralId}", method = RequestMethod.GET)
    public ModelAndView editPuesto(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("relacionLaboralId") Long relacionLaboralId) {

		logger.info("Relacion Laboral Editar Puesto de Trabajo");
        
        ModelAndView mav = new ModelAndView(VistasConstant.EDIT_PUESTO_EMPLEADO);
        
        RelacionPuestoEmpleado relacionPuesto = relacionLaboralService.getRelacionPuestoEmpleado(relacionLaboralId);
        if(!relacionLaboralSecurityHelper.elUsuarioTieneAcceso(request, relacionPuesto.getRelacionLaboral(), RelacionLaboralSecurityHelper.ACCION_PUESTO)) {
        	return Utils.getUnauthorizedActionModelAndView();
        }
        
        RelacionPuestoEmpleadoDto relacionPuestoEmpleado = RelacionLaboralMapper.getRelacionPuestoEmpleadoDtoFromRelacionPuestoEmpleado(relacionPuesto);

        mav.addObject("relacionPuestoEmpleadoDto", relacionPuestoEmpleado);
        mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesOrderByNombre()));
	    mav.addObject("puestosDto", RelacionLaboralMapper.getComboDtosFromPuestos(relacionLaboralService.getPuestos()));
        return mav;
    }
	
	@RequestMapping(value = UrlConstant.ASIGNACION_PUESTO_URL + "/{relacionLaboralId}", method = RequestMethod.POST)
    public ModelAndView submitEditPuesto(Locale locale, Model model, Principal principal, RelacionPuestoEmpleadoDto relacionPuestoEmpleadoDto, BindingResult result, @PathVariable("relacionLaboralId") Long relacionLaboralId) {

		logger.info("Relacion Laboral Editar Puesto de Trabajo");
		ModelAndView mav;
		relacionLaboralValidator.validatePuestosLaborales(relacionPuestoEmpleadoDto, result);
		if(result.hasErrors()){
		    mav = new ModelAndView(VistasConstant.EDIT_PUESTO_EMPLEADO);
			RelacionPuestoEmpleado relacionPuesto = relacionLaboralService.getRelacionPuestoEmpleado(relacionLaboralId);
			RelacionPuestoEmpleadoDto relacionPuestoEmpleado = RelacionLaboralMapper.getRelacionPuestoEmpleadoDtoFromRelacionPuestoEmpleado(relacionPuesto);
			relacionPuestoEmpleado.setFechaInicio(null);
	        String empleado = relacionPuesto.getRelacionLaboral().getEmpleado().getNombres() + " " +relacionPuesto.getRelacionLaboral().getEmpleado().getApellidos();
	        relacionPuestoEmpleadoDto.setNombreEmpleado(empleado);
	        mav.addObject("relacionPuestoEmpleadoDto", relacionPuestoEmpleadoDto); 
	        mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesOrderByNombre()));
	        mav.addObject("puestosDto", RelacionLaboralMapper.getComboDtosFromPuestos(relacionLaboralService.getPuestos()));
	        return mav;
		}
		mav = new ModelAndView(VistasConstant.ASIGNACION_EMPLEADO_NEW_VIEW);
        RelacionPuestoEmpleado relacion = RelacionLaboralMapper.updateRelacionPuestoEmpleadoFromRelacionPuestoEmpleadoDto(relacionPuestoEmpleadoDto);
		relacionLaboralService.updateRelacionLaboralAndPuesto(relacion);
		
        mav = new ModelAndView("redirect:" + UrlConstant.ASIGNACION_EMPLEADOS_URL);
        return mav;
    }
	
	

	@RequestMapping(value = UrlConstant.ASIGNACION_EMPLEADO_EDIT_URL + "/{empleadoId}", method = RequestMethod.POST)
    public ModelAndView usuarioFormEditSubmit(Locale locale, Model model, Principal principal, RelacionLaboralDto relacionLaboralDto, BindingResult result) {

		logger.info("Relacion Laboral Form -> Edit (Submit)");
        ModelAndView mav = null;
        
        RelacionLaboral relacionLaboral = relacionLaboralService.getRelacionLaboralById(relacionLaboralDto.getRelacionLaboralId());
        relacionLaboralDto.setFechaInicio(Utils.sdf.format(relacionLaboral.getFechaInicio()));
        
        relacionLaboralValidator.validate(relacionLaboralDto, result);
        
		 //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.ASIGNACION_EMPLEADO_EDIT_VIEW);
			relacionLaboralDto.setNombreEmpleado(relacionLaboral.getEmpleado().getApellidos() + ", " + relacionLaboral.getEmpleado().getNombres());
			relacionLaboralDto.setRazonSocialCliente(relacionLaboral.getCliente().getRazonSocial());
		    mav.addObject("relacionLaboralDto", relacionLaboralDto);
	        
	        return mav;
		}
        
		relacionLaboral = RelacionLaboralMapper.updateRelacionLaboralFromRelacionLaboralDto(relacionLaboral, relacionLaboralDto);
		relacionLaboralService.update(relacionLaboral);
		
		mav = new ModelAndView("redirect:" + UrlConstant.ASIGNACION_EMPLEADOS_URL);
        return mav;
    }	
	
	private Usuario getUsuarioAutenticado(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        String username = auth.getName();
        Usuario usuario = usuarioService.getUsuarioByUsername(username);
        
        return usuario;
	}
	
		
	@RequestMapping(value = UrlConstant.INCREMENTO_MASIVO_POR_CLIENTE_NEW_URL, method = RequestMethod.GET)
    public ModelAndView incrementMasivoPorCliente(HttpServletRequest request, Locale locale, Model model, Principal principal) {
        logger.info("Incremengo masivo por cliente form -> Nuevo");
        
        ModelAndView mav = new ModelAndView(VistasConstant.INCREMENTO_MASIVO_POR_CLIENTE_VIEW);
 
		List<Cliente> clientes = this.getClientesForUsuario(request);
		
		mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clientes));
        mav.addObject("incrementoSalarialDto", new IncrementoSalarialDto()); 
        mav.addObject("tiposDto", this.getTiposIncrmento());
        return mav;
    }
	
	private List<ComboStrDto> getTiposIncrmento(){
        List<ComboStrDto> tipos = new ArrayList<ComboStrDto>();
        ComboStrDto tipo = new ComboStrDto();
        tipo.setId("$");
        tipo.setValue("$");
        tipos.add(tipo);
        tipo = new ComboStrDto();
        tipo.setId("%");
        tipo.setValue("%");
        tipos.add(tipo);
        return tipos;
	}
	
	@RequestMapping(value = UrlConstant.INCREMENTO_MASIVO_POR_CLIENTE_NEW_URL, method = RequestMethod.POST)
    public ModelAndView incrementMasivoPorClienteSubmit(HttpServletRequest request, Locale locale, Model model, Principal principal, @Valid IncrementoSalarialDto incrementoSalarialDto, BindingResult result) throws ParseException {
		ModelAndView mav;
		
		incrementoSalarialValidator.validate(incrementoSalarialDto, result);
		
		if(result.hasErrors()) {
			mav = new ModelAndView(VistasConstant.INCREMENTO_MASIVO_POR_CLIENTE_VIEW);
			List<Cliente> clientes = this.getClientesForUsuario(request);
			
			mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clientes));
	        mav.addObject("incrementoSalarialDto", incrementoSalarialDto); 
	        mav.addObject("tiposDto", this.getTiposIncrmento());
	        return mav;
		}
		
		Double incBasico = 0d;
		Double incPresentismo = 0d;
		Date fechaInicio = Utils.sdf.parse(incrementoSalarialDto.getFechaInicio());
		Long clienteId = incrementoSalarialDto.getClienteId();
		
		if(StringUtils.isNotEmpty(incrementoSalarialDto.getIncrementoBasico())){
			incBasico = Double.valueOf(incrementoSalarialDto.getIncrementoBasico());	
		}
		if(StringUtils.isNotEmpty(incrementoSalarialDto.getIncrementoPresentismo())){
			incPresentismo = Double.valueOf(incrementoSalarialDto.getIncrementoPresentismo());	
		}
		
		relacionLaboralService.aplicarIncrementoMasivo(clienteId, fechaInicio, incBasico, incPresentismo, incrementoSalarialDto.getTipoIncremento());
		
		DecimalFormat df = new DecimalFormat("#0.00");
		incrementoSalarialDto.setIncrementoBasico(df.format(incBasico));
		incrementoSalarialDto.setIncrementoPresentismo(df.format(incPresentismo));
		
		mav = new ModelAndView(VistasConstant.INCREMENTO_MASIVO_POR_CLIENTE_RESULT_VIEW);
		mav.addObject("incrementoSalarialDto", incrementoSalarialDto);
		mav.addObject("datetime", fechaInicio.getTime());
		mav.addObject("cliente", clienteService.getClienteById(clienteId).getNombre());
        return mav;
	}   

	@RequestMapping(value = UrlConstant.INCREMENTO_MASIVO_POR_CLIENTE_RESULT_GRILLA_URL, method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> incrementMasivoPorClienteResultGrilla(HttpServletRequest request,HttpServletResponse response) {
		logger.info("obtener informacion para llenado de grilla resultado incremento");
		Long clienteId = Long.parseLong(request.getParameter("clienteId"));
		Date fechaInicio = new Date(Long.parseLong(request.getParameter("dt")));
		
		List<RelacionLaboral> relaciones = relacionLaboralService.getRelacionesLaboralesVigentesByCliente(clienteId);
		
		List<IncrementoSalarialEmpleadoDto> incrementos = RelacionLaboralMapper.getIncrementosDtoFromRelacionesLaborales(relaciones, fechaInicio);
		 
        JqGridData<IncrementoSalarialEmpleadoDto> gridData = new JqGridData<IncrementoSalarialEmpleadoDto>(1, 1,incrementos.size() , incrementos);
        return gridData.getJsonObject();
	}
	
	
	private List<Cliente> getClientesForUsuario(HttpServletRequest request){
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
		
		return clientes;
	}
 }
