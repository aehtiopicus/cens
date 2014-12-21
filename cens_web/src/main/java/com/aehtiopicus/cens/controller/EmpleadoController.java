
package com.aehtiopicus.cens.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VelocityTemplateName;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Vacaciones;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.dto.EmpleadoDto;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.dto.SueldoDto;
import com.aehtiopicus.cens.dto.VacacionesDto;
import com.aehtiopicus.cens.helper.EmpleadoSecurityHelper;
import com.aehtiopicus.cens.mapper.ClienteMapper;
import com.aehtiopicus.cens.mapper.EmpleadoMapper;
import com.aehtiopicus.cens.mapper.RelacionLaboralMapper;
import com.aehtiopicus.cens.mapper.UtilsMapper;
import com.aehtiopicus.cens.service.BancoService;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.service.EmailService;
import com.aehtiopicus.cens.service.EmpleadoService;
import com.aehtiopicus.cens.service.MotivoBajaService;
import com.aehtiopicus.cens.service.NacionalidadService;
import com.aehtiopicus.cens.service.ObraSocialService;
import com.aehtiopicus.cens.service.PrepagaService;
import com.aehtiopicus.cens.service.RelacionLaboralService;
import com.aehtiopicus.cens.service.ReporteService;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.validator.EmpleadoValidator;
import com.aehtiopicus.cens.validator.SueldoValidator;
import com.aehtiopicus.cens.validator.VacacionesValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EmpleadoController extends AbstractController {

	private static final Logger logger = Logger.getLogger(EmpleadoController.class);
	private static final String APPLICATION_MEDIA_TYPE = "application";
    @Autowired
    protected EmpleadoService empleadoService;
    @Autowired
    protected NacionalidadService nacionalidadService;
    @Autowired
    protected ObraSocialService obraSocialService;
    @Autowired
    protected PrepagaService prepagaService;
    @Autowired
    protected BancoService bancoService;
    @Autowired
    protected ClienteService clienteService;
    @Autowired
    protected EmpleadoValidator empleadoValidator;
    @Autowired
    protected ReporteService reporteService;
    @Autowired
    protected SueldoValidator sueldoValidator;
    @Autowired
    protected VacacionesValidator vacacionesValidator;
    @Autowired
    protected EmailService emailService;
    @Autowired
    protected MotivoBajaService motivoBajaService;
    
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    @Autowired
    protected RelacionLaboralService relacionLaboralService;
    
    @Autowired
    protected EmpleadoSecurityHelper empleadoSecurityHelper;
    
      
	public void setEmpleadoSecurityHelper(
			EmpleadoSecurityHelper empleadoSecurityHelper) {
		this.empleadoSecurityHelper = empleadoSecurityHelper;
	}

	public void setRelacionLaboralService(
			RelacionLaboralService relacionLaboralService) {
		this.relacionLaboralService = relacionLaboralService;
	}

	public void setVacacionesValidator(VacacionesValidator vacacionesValidator) {
		this.vacacionesValidator = vacacionesValidator;
	}

	public void setSueldoValidator(SueldoValidator sueldoValidator) {
		this.sueldoValidator = sueldoValidator;
	}

	public void setReporteService(ReporteService reporteService) {
		this.reporteService = reporteService;
	}

	public void setEmpleadoValidator(EmpleadoValidator empleadoValidator) {
		this.empleadoValidator = empleadoValidator;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public void setBancoService(BancoService bancoService) {
		this.bancoService = bancoService;
	}

	public void setPrepagaService(PrepagaService prepagaService) {
		this.prepagaService = prepagaService;
	}

	public void setObraSocialService(ObraSocialService obraSocialService) {
		this.obraSocialService = obraSocialService;
	}

	public void setNacionalidadService(NacionalidadService nacionalidadService) {
		this.nacionalidadService = nacionalidadService;
	}

	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	public void setMotivoBajaService(MotivoBajaService motivoBajaService) {
		this.motivoBajaService = motivoBajaService;
	}

	
	@RequestMapping(value = UrlConstant.EMPLEADO_URL, method = RequestMethod.GET)
    public ModelAndView empleadoFormNew(Locale locale, Model model, Principal principal) {

		logger.info("Empleado Form -> Nuevo");
        
        ModelAndView mav = new ModelAndView(VistasConstant.EMPLEADO_VIEW);
       
        mav.addObject("estadoCivilDto", UtilsMapper.getCombosDtoEstadoCivil());
        mav.addObject("nacionalidadDto", UtilsMapper.getComboNacionalidad(nacionalidadService.getNacionalidades()));
        mav.addObject("obraSocialDto", UtilsMapper.getComboObraSocial(obraSocialService.getObrasSociales()));
        mav.addObject("prepagaDto", UtilsMapper.getComboPrepaga(prepagaService.getPrepagas()));
        mav.addObject("bancoDto", UtilsMapper.getComboBancos(bancoService.getBancos()));
        mav.addObject("empleadoDto", new EmpleadoDto());
        mav.addObject("empleadoId", null);
        mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesOrderByNombre()));
	    mav.addObject("puestosDto", RelacionLaboralMapper.getComboDtosFromPuestos(relacionLaboralService.getPuestos()));
	    mav.addObject("motivoBajaDto", UtilsMapper.getComboMotivoBaja(motivoBajaService.getMotivosBaja()));
        return mav;
    }

	

	@RequestMapping(value = UrlConstant.EMPLEADO_URL + "/{empleadoId}", method = RequestMethod.GET)
    public ModelAndView empleadoFormEdit(Locale locale, Model model, Principal principal, @PathVariable("empleadoId") Long empleadoId, HttpServletRequest request) {

		logger.info("Empleado Form -> Editar");
		
		Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(empleadoId);

		//Si el usuario logueado es un gerente de operaciones solo puede editar los empleados asignados a sus clientes.
       	if(!empleadoSecurityHelper.elUsuarioTieneAcceso(request, empleado, EmpleadoSecurityHelper.ACCION_EDITAR)) {
       		return Utils.getUnauthorizedActionModelAndView();
       	}
		
        EmpleadoDto empleadoDto = EmpleadoMapper.getEmpleadoDtoFromEmpleadoo(empleado);
        
        ModelAndView mav = new ModelAndView(VistasConstant.EMPLEADO_VIEW);
        mav.addObject("empleadoDto", empleadoDto);
        mav.addObject("empleadoId", empleadoDto.getEmpleadoId());
        mav.addObject("estadoCivilDto", UtilsMapper.getCombosDtoEstadoCivil());
        mav.addObject("nacionalidadDto", UtilsMapper.getComboNacionalidad(nacionalidadService.getNacionalidades()));
        mav.addObject("obraSocialDto", UtilsMapper.getComboObraSocial(obraSocialService.getObrasSociales()));
        mav.addObject("prepagaDto", UtilsMapper.getComboPrepaga(prepagaService.getPrepagas()));
        mav.addObject("bancoDto", UtilsMapper.getComboBancos(bancoService.getBancos()));
        mav.addObject("prepagaDto", UtilsMapper.getComboPrepaga(prepagaService.getPrepagas()));
        mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesOrderByNombre()));
	    mav.addObject("puestosDto", RelacionLaboralMapper.getComboDtosFromPuestos(relacionLaboralService.getPuestos()));
	    mav.addObject("motivoBajaDto", UtilsMapper.getComboMotivoBaja(motivoBajaService.getMotivosBaja()));

        return mav;
    }	
	
	
	@RequestMapping(value = {UrlConstant.EMPLEADO_URL, UrlConstant.EMPLEADO_URL + "/{empleadoId}"}, method = RequestMethod.POST)
    public ModelAndView empleadoFormSubmit(Locale locale, Model model, Principal principal, @Valid EmpleadoDto empleadoDto, BindingResult result) throws ParseException {
       logger.info("Guardando Empleado.");
        ModelAndView mav = null;
        
        if(empleadoDto.getEmpleadoId() != null) {
	        Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(empleadoDto.getEmpleadoId());
	        loadRelacionVigente(empleadoDto, empleado);
        }
        
        empleadoValidator.validate(empleadoDto, result);

        //Si hay errores,  se muestran al usuario
        if(result.hasErrors()){
			    mav = new ModelAndView(VistasConstant.EMPLEADO_VIEW);
		       
		        mav.addObject("estadoCivilDto", UtilsMapper.getCombosDtoEstadoCivil());
		        mav.addObject("nacionalidadDto", UtilsMapper.getComboNacionalidad(nacionalidadService.getNacionalidades()));
		        mav.addObject("obraSocialDto", UtilsMapper.getComboObraSocial(obraSocialService.getObrasSociales()));
		        mav.addObject("prepagaDto", UtilsMapper.getComboPrepaga(prepagaService.getPrepagas()));
		        mav.addObject("bancoDto", UtilsMapper.getComboBancos(bancoService.getBancos()));
		        mav.addObject("prepagaDto", UtilsMapper.getComboPrepaga(prepagaService.getPrepagas()));
		        mav.addObject("empleadoDto", empleadoDto);
		        mav.addObject("empleadoId", empleadoDto.getEmpleadoId());
		        mav.addObject("clientesDto", ClienteMapper.getComboDtoFromCliente(clienteService.getClientesVigentesOrderByNombre()));
			    mav.addObject("puestosDto", RelacionLaboralMapper.getComboDtosFromPuestos(relacionLaboralService.getPuestos()));
			    mav.addObject("motivoBajaDto", UtilsMapper.getComboMotivoBaja(motivoBajaService.getMotivosBaja()));

			    return mav;
		}
		
		Empleado empleadoDb = null;
		Empleado empl = null;
		RelacionLaboral relacionLaboral = null;
		
		//Si no es un empleado viejo que se esta reincorporando lo trabajamos igual que siempre:
		if(!empleadoDto.isOldEmpleado() || empleadoDto.getEmpleadoId() != null) {
			if(empleadoDto.getEmpleadoId() != null){
				empleadoDb = empleadoService.getEmpleadoByEmpleadoId(empleadoDto.getEmpleadoId());
				empl = EmpleadoMapper.getEmpleadoActualizadoFromEmpleadooDto(empleadoDto,empleadoDb);
				empleadoService.saveEmpleado(empl);
			}else{
				empl = EmpleadoMapper.getEmpleadoFromEmpleadooDto(empleadoDto);
				empl = empleadoService.saveEmpleado(empl);
				if(empleadoDto.getClienteId() != null){
					relacionLaboral = RelacionLaboralMapper.getRelacionLaboralFromEmpleadoDto(empleadoDto);
					relacionLaboral.setEmpleado(empl);
					relacionLaboral = relacionLaboralService.createAndSave(relacionLaboral);
				}
				if(StringUtils.isNotEmpty(empleadoDto.getBasico()) || StringUtils.isNotEmpty(empleadoDto.getPresentismo())) {
					Sueldo sueldo = EmpleadoMapper.getSueldoFromEmpleadoDto(empleadoDto);
					empleadoService.saveSueldo(sueldo, empl.getId());
				}
			}	 

		//Si es un empleado viejo que se esta reincorporando obtenemos el registro original del usuario y lo actualizamos:
		}else {
			empleadoDb = empleadoService.getEmpleadoByDni(empleadoDto.getDni());
			empl = EmpleadoMapper.getEmpleadoActualizadoFromEmpleadooDto(empleadoDto, empleadoDb);
			empl.setId(empleadoDb.getId());
			empleadoService.reincorporarEmpleado(empl);			
			if(empleadoDto.getClienteId() != null){
				relacionLaboral = RelacionLaboralMapper.getRelacionLaboralFromEmpleadoDto(empleadoDto);
				relacionLaboral.setEmpleado(empl);
				relacionLaboral = relacionLaboralService.createAndSave(relacionLaboral);
			}
			if(StringUtils.isNotEmpty(empleadoDto.getBasico()) || StringUtils.isNotEmpty(empleadoDto.getPresentismo())) {
				Sueldo sueldo = EmpleadoMapper.getSueldoFromEmpleadoDto(empleadoDto);
				empleadoService.saveSueldo(sueldo, empl.getId());
			}
		}
		//enviamos email solo en el alta de empleados.. no en la edicion..
		//si el dto tiene id es porque se esta editando el empleado.
		if(empleadoDto.getEmpleadoId() == null) {
			sendMailAltaEmpleado(empleadoService.getEmpleadoByEmpleadoId(empl.getId()));
		}
		
        mav = new ModelAndView("redirect:" + UrlConstant.EMPLEADOS_URL);
        return mav;
    }

	private void sendMailAltaEmpleado(Empleado e) {
		//EMAIL
		//Variables para el template del mail
		Map<String, String> modelo = new HashMap<String, String>();
		modelo.put("empleado", e.getApellidos() + ", " + e.getNombres());
		modelo.put("fechaIngresoNovatium", Utils.sdf.format(e.getFechaIngresoNovatium()));
		
		
		if(e.getFechaPreOcupacional() != null) {
			modelo.put("preocupacional", Utils.sdf.format(e.getFechaPreOcupacional()) + " - ");
		}else {
			modelo.put("preocupacional", "");				
		}
		if(e.getResultadoPreOcupacional() != null) {
			modelo.put("preocupacional", modelo.get("preocupacional") + e.getResultadoPreOcupacional());
		}else {
			modelo.put("preocupacional", "");				
		}
		
		if(e.getChomba() != null && e.getChomba()) {
			modelo.put("chomba", "SI");				
		}else {
			modelo.put("chomba", "NO");								
		}
		
		if(e.getObraSocial() != null) {
			modelo.put("obraSocial", e.getObraSocial().getNombre());				
		}else {
			modelo.put("obraSocial", "");				
		}

		Sueldo s = e.getSueldoVigente();
		if(s != null) {
			
			modelo.put("salarioBruto", formatTo2Decimals(s.getBasico()));
			modelo.put("adicionales", formatTo2Decimals(s.getPresentismo()));
		}else {
			modelo.put("salarioBruto", "");
			modelo.put("adicionales", "");
		}

		RelacionLaboral rl = e.getRelacionLaboralVigente();
		if(rl != null && rl.getCliente() != null) {
			Cliente c = rl.getCliente();
			modelo.put("cliente", c.getRazonSocial());
			modelo.put("gerente", c.getGerenteOperacion().getApellido() + ", " + c.getGerenteOperacion().getNombre());
			if(c.getJefeOperacion() != null) {
				modelo.put("jefe", c.getJefeOperacion().getApellido() + ", " + c.getJefeOperacion().getNombre());			
			}else {
				modelo.put("jefe", "");
			}
			if(rl.getPuesto() != null) {
				modelo.put("puesto",  rl.getPuesto().getNombre());					
			}else {
				modelo.put("puesto",  "");					
			}
		}else {
			modelo.put("puesto", "");
			modelo.put("cliente", "");
			modelo.put("gerente", "");
			modelo.put("jefe", "");				
			
		}

        String subjectEmail = "Alta de Empleado";

		//enviar el mail
		emailService.enviarEmail(VelocityTemplateName.EMPLEADO_CREADO_EMAIL_TEMPLATE, modelo, subjectEmail);
	}
	
	private String formatTo2Decimals(Double value) {

		String[] split = value.toString().split("\\.");
		
		if(split.length ==1) {
			return value.toString() + ".00";
			
		}else if(split.length == 2) {
			if(split[1].length() == 1) {
				return split[0] + "." + split[1] + "0";						
			}else if(split[1].length() == 2) {
				return split[0] + "." + split[1];										
			}else if(split[1].length() == 0) {
				return split[0] + ".00";
			}else {
				return value.toString();
			}
			
		}else {
			return value.toString();
		}

	}
	
	private void loadRelacionVigente(EmpleadoDto empleadoDto, Empleado empleado) {

        if(empleado.getRelacionLaboralVigente() != null && empleado.getRelacionLaboralVigente().getCliente() != null && empleado.getRelacionLaboralVigente().getFechaInicio() != null){
    		empleadoDto.setClienteId(empleado.getRelacionLaboralVigente().getCliente().getId());
    		empleadoDto.setClienteNombre(empleado.getRelacionLaboralVigente().getCliente().getRazonSocial());
    		empleadoDto.setFechaInicio(Utils.sdf.format(empleado.getRelacionLaboralVigente().getFechaInicio()));
    		empleadoDto.setPuestoId(empleado.getRelacionLaboralVigente().getPuesto().getId());
    		empleadoDto.setExistRelacion(true);	
    	}
		
	}
	
	
	
	
	@RequestMapping(value = UrlConstant.EMPLEADOS_URL, method = RequestMethod.GET)
    public ModelAndView getEmpleados(Locale locale, Model model, Principal principal) throws Exception {       
		logger.info("getEmpleados.");
		
        List<Cliente> cliente = clienteService.getClientes();
        List<ComboDto> clienteDto = ClienteMapper.getComboDtoFromCliente(cliente);
        List<ComboDto> estadoDto = EmpleadoMapper.getComboEmpleadoEstado();
        ModelAndView mav = new ModelAndView(VistasConstant.EMPLEADOS_VIEW);
        mav.addObject("clienteDto", clienteDto);
        mav.addObject("estadoDto", estadoDto);
        return mav;
    }
	
	@RequestMapping(value = UrlConstant.EMPLEADOS_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> empleadosGrilla(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Date start = new Date();
		
		logger.info("obtener informacion para llenado de grilla empleados");
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		Integer page = Integer.parseInt(request.getParameter("page"));
		String cuil = request.getParameter("cuil");
		String apellido = request.getParameter("apellido");
		String cliente = request.getParameter("cliente");
		String estado = request.getParameter("estado");

		List<Empleado> empleados = empleadoService.getEmpleadosPaginado(page, rows, cuil, apellido, cliente, estado);
		
		int pageNumber = page;
		int totalEmpl = empleadoService.getTotalEmpleados(cuil, apellido, cliente, estado);
		int pageTotal = Utils.getNumberOfPages(rows, totalEmpl);
		
		List<EmpleadoDto> empleadoDto = EmpleadoMapper.getListEmpleadoDtoFromEmpleados(empleados);
		
		empleadoSecurityHelper.addSecurityToActionsEmpleadosGrilla(request, empleadoDto);
		JqGridData<EmpleadoDto> gridData = new JqGridData<EmpleadoDto>(pageTotal, pageNumber, totalEmpl, empleadoDto);
		//Date end = new Date();
		
		//logger.info("Tiempo: " + (end.getTime() - start.getTime()) + " mseg.");
		
		return gridData.getJsonObject();

	}
	
	 @RequestMapping(value = UrlConstant.EMPLEADO_EXCEL, method = RequestMethod.GET)
	 public HttpEntity<byte[]>   getReporte(HttpServletRequest request,HttpServletResponse response) throws IOException {
		   logger.info("obtener informacion para llenado de grilla empleados");
		   Date start = new Date();
		   String cuil = request.getParameter("cuil");
	       String apellido = request.getParameter("apellido");
	       String cliente = request.getParameter("cliente");
	       String estado = request.getParameter("estado");
	       List<Empleado> empleados = empleadoService.getAllEmpleados(cuil,apellido,cliente,estado);
	     
	       HSSFWorkbook excel = reporteService.getReporte(empleados);

	      File archivoDumpTemporal = File.createTempFile("temp","xls");
	      archivoDumpTemporal.deleteOnExit();
   		  FileOutputStream fos = new FileOutputStream(archivoDumpTemporal);
   		  excel.write(fos);
	       byte[] encoded = Files.readAllBytes(archivoDumpTemporal.toPath());
	       HttpHeaders header = new HttpHeaders();
	       header.setContentType(new MediaType(APPLICATION_MEDIA_TYPE, ".xls"));
	       header.set(CONTENT_DISPOSITION, "attachment; filename=\""+"empleados.xls"+"\"");
	       header.setContentLength(encoded.length);

		   Date end = new Date();
		   logger.info("Tiempo: " + (end.getTime() - start.getTime()) + " mseg.");
	       return new HttpEntity<byte[]>(encoded, header);
	    }
	
	 @RequestMapping(value = UrlConstant.EMPLEADO_SUELDO + "/{empleadoId}", method = RequestMethod.GET)
	 public ModelAndView sueldoForm(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("empleadoId") Long empleadoId) {

			logger.info("Empleado Sueldo");
			
			Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(empleadoId);
			if(!empleadoSecurityHelper.elUsuarioTieneAcceso(request, empleado,EmpleadoSecurityHelper.ACCION_SUELDO)) {
				return Utils.getUnauthorizedActionModelAndView();
			}
			
			Sueldo sueldo = empleadoService.getSueldoActualByEmpleadoId(empleadoId);
			SueldoDto sueldoDto = new SueldoDto();
			if(sueldo != null){
				sueldoDto = EmpleadoMapper.getSueldoDtoFromSueldo(sueldo,empleado);	
			}
			sueldoDto.setEmpleadoId(empleadoId);
			sueldoDto.setEmpleado(empleado.getApellidos()+", "+empleado.getNombres());
	        ModelAndView mav = new ModelAndView(VistasConstant.SUELDO_VIEW);
	        mav.addObject("sueldoDto", sueldoDto);
	        return mav;
	    }	
		
	 @RequestMapping(value = {UrlConstant.EMPLEADO_SUELDO + "/{sueldoId}"}, method = RequestMethod.POST)
	 public ModelAndView sueldoFormSubmit(Locale locale, Model model, Principal principal, @Valid SueldoDto sueldoDto, BindingResult result, @PathVariable("sueldoId") Long sueldoId) throws ParseException {
		    logger.info("sueldoFormSubmit.");
		    ModelAndView mav = null;
		    sueldoValidator.validate(sueldoDto, result);
		    
		    if(result.hasErrors()){
		    	   Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(sueldoDto.getEmpleadoId());
		    	   sueldoDto.setEmpleado(empleado.getApellidos()+", "+empleado.getNombres());
				    mav = new ModelAndView(VistasConstant.SUELDO_VIEW);
			        mav.addObject("sueldoDto", sueldoDto);
			        return mav;
			}
			Sueldo sueldo = EmpleadoMapper.getSueldoFromSueldoDto(sueldoDto);			
			empleadoService.saveSueldo(sueldo,sueldoDto.getEmpleadoId());
	        return new ModelAndView("redirect:"+UrlConstant.EMPLEADOS_URL);
	 }
	 
	 /**
	  * Crear nuevas vacaciones
	  * @param locale
	  * @param model
	  * @param principal
	  * @param empleadoId
	  * @return
	  */
	@RequestMapping(value = UrlConstant.EMPLEADO_VACACION_FORM_URL, method = RequestMethod.GET)
	public ModelAndView vacacionFormNew(Locale locale, Model model,	Principal principal, HttpServletRequest request) {
		Long empleadoId = Long.valueOf(request.getParameter("empleadoId"));
		Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(empleadoId);
		//Si el usuario logueado es un gerente de operaciones solo puede editar los empleados asignados a sus clientes.
       	if(!empleadoSecurityHelper.elUsuarioTieneAcceso(request, empleado, EmpleadoSecurityHelper.ACCION_VACACION)) {
       		return Utils.getUnauthorizedActionModelAndView();
       	}
       	
		logger.info("Vacaciones Form -> Nuevo");
		ModelAndView mav = new ModelAndView(VistasConstant.VACACION_VIEW);
		VacacionesDto vacacionesDto = new VacacionesDto();
		vacacionesDto.setEmpleadoId(empleadoId);
		mav.addObject("vacacionesDto", vacacionesDto);
		return mav;
	}
		
	
	    @RequestMapping(value = {UrlConstant.EMPLEADO_VACACION_FORM_URL }, method = RequestMethod.POST)
	    public ModelAndView vacacionFormSubmit(HttpServletRequest request,Locale locale, Model model, Principal principal, @Valid VacacionesDto vacacionDto, BindingResult result) throws ParseException {
	    	  	logger.info("vacacionFormSubmit.");
	    	  	Long empleadoId = Long.valueOf(request.getParameter("empleadoId"));
	    		ModelAndView mav = null;
	    		vacacionesValidator.validate(vacacionDto, result);
	    		if(result.hasErrors()){
	    			    mav = new ModelAndView(VistasConstant.VACACION_VIEW);
	    		        mav.addObject("vacacionesDto", vacacionDto);
	    		        return mav;
	    		}
	    		vacacionDto.setEmpleadoId(empleadoId);
	    		Vacaciones vacacion = EmpleadoMapper.getVacacionFromVacacionDto(vacacionDto);			
	    		empleadoService.saveVacaciones(vacacion,vacacionDto.getEmpleadoId());
	    		return new ModelAndView("redirect:"+UrlConstant.EMPLEADO_VACACION+"/"+vacacionDto.getEmpleadoId());

		 }

	    
	@RequestMapping(value = UrlConstant.EMPLEADO_VACACION+ "/{empleadoId}", method = RequestMethod.GET)
	public ModelAndView getVacaciones(HttpServletRequest request,Locale locale, Model model, Principal principal, @PathVariable("empleadoId") Long empleadoId) {
			logger.info("getVacaciones.");
	       	ModelAndView mav = new ModelAndView(VistasConstant.VACACIONES_VIEW);
	       	Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(empleadoId);
	       	
	       	if(!empleadoSecurityHelper.elUsuarioTieneAcceso(request, empleado, EmpleadoSecurityHelper.ACCION_VACACION)) {
	       		return Utils.getUnauthorizedActionModelAndView();
	       	}
	       	
			mav.addObject("empleadoId",empleadoId);
			mav.addObject("estado",empleado.getEstado().getNombre());
			mav.addObject("empleado",empleado.getApellidos()+", "+empleado.getNombres());
	        return mav;
	 }
		
		 @RequestMapping(value = UrlConstant.EMPLEADO_VACACIONES_GRILLA+ "/{empleadoId}", method = RequestMethod.GET)
		 public @ResponseBody Map<String, Object> vacacionesGrilla(HttpServletRequest request,HttpServletResponse response, @PathVariable("empleadoId") Long empleadoId) throws IOException {
			 
			   logger.info("obtener informacion para llenado de grilla vacaciones");
			   // recuperar valores de paginado y filtrado
		        Integer rows = Integer.parseInt(request.getParameter("rows"));
		        Integer page = Integer.parseInt(request.getParameter("page"));
		        List<Vacaciones> vacaciones = empleadoService.getVacacionesPaginado(page, rows,empleadoId);
		     
		        int pageNumber = page; 
		        int totalVacaciones = empleadoService.getTotalVacacionesByEmpleado(empleadoId);
		        int pageTotal = Utils.getNumberOfPages(rows,totalVacaciones);
		        List<VacacionesDto> vacacionesDto = EmpleadoMapper.getListVacacionesDtoFromEmpleados(vacaciones);
		        JqGridData<VacacionesDto> gridData = new JqGridData<VacacionesDto>(pageTotal, pageNumber,totalVacaciones , vacacionesDto);
		        return gridData.getJsonObject();
	    }
		 
		@RequestMapping(value = UrlConstant.EMPLEADO_VACACION_FORM_URL + "/{vacacionId}", method = RequestMethod.GET)
		public ModelAndView vacacionesFormEdit(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("vacacionId") Long vacacionId) {

				logger.info("Vacaciones Form -> Editar");
			
				Vacaciones vacacion = empleadoService.getVacacionById(vacacionId);
		       	if(!empleadoSecurityHelper.elUsuarioTieneAcceso(request, vacacion.getEmpleado(), EmpleadoSecurityHelper.ACCION_VACACION)) {
		       		return Utils.getUnauthorizedActionModelAndView();
		       	}
				
		        VacacionesDto vacacionesDto = EmpleadoMapper.getVacacionDtoFromVacacion(vacacion);
		        
		        ModelAndView mav = new ModelAndView(VistasConstant.VACACION_VIEW);
		        mav.addObject("vacacionesDto", vacacionesDto);
		        return mav;
	    }	
		
		@RequestMapping(value = UrlConstant.EMPLEADO_VACACION_FORM_URL + "/{vacacionId}", method = RequestMethod.POST)
		public ModelAndView vacacionesEdit(Locale locale, Model model, Principal principal, @Valid VacacionesDto vacacionDto, BindingResult result,@PathVariable("vacacionId") Long vacacionId) throws ParseException  {
			logger.info("vacacionFormSubmit.");
			
			ModelAndView mav = null;
			vacacionesValidator.validate(vacacionDto, result);
			if(result.hasErrors()){
				    mav = new ModelAndView(VistasConstant.VACACION_VIEW);
			        mav.addObject("vacacionesDto", vacacionDto);
			        return mav;
			}
			Vacaciones vacacion = EmpleadoMapper.getVacacionFromVacacionDto(vacacionDto);			
			empleadoService.saveVacaciones(vacacion,vacacionDto.getEmpleadoId());
			return new ModelAndView("redirect:"+UrlConstant.EMPLEADO_VACACION+"/"+vacacionDto.getEmpleadoId());
		}	
			
	@RequestMapping(value = UrlConstant.VACACIONES_REMOVE_URL, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteVacaciones(Locale locale, Model model, Principal principal, Long vacacionesId, HttpServletRequest request) {
		logger.info("Eliminar vacaciones.");

		Map<String, Object> data = new HashMap<String, Object>();
		if (vacacionesId != null && vacacionesId > 0) {
			Vacaciones v = empleadoService.getVacacionById(vacacionesId);
			if(!empleadoSecurityHelper.elUsuarioTieneAcceso(request, v.getEmpleado(), EmpleadoSecurityHelper.ACCION_VACACION)) {
				return Utils.getUnauthorizedActionMap();
	       	}
			Empleado empl = v.getEmpleado();
			List<Vacaciones> vacaciones = empleadoService.getVacacionesByEmpleado(empl.getId());
			List<Vacaciones> vacAux = new ArrayList<Vacaciones>();
			romeVacacionesFromEmpleado(vacacionesId, empl, vacaciones, vacAux);
			Vacaciones vacacion = new Vacaciones();
			vacacion.setId(vacacionesId);
			empleadoService.deleteVacaciones(vacacion);
			data.put("success", true);
			data.put("message", "Vacaciones eliminada correctamente.");
		} else {
			data.put("sucess", false);
			data.put("message", "Error al intentar eliminar la vacacion.");
		}

		return data;
	}

	private void romeVacacionesFromEmpleado(Long vacacionesId, Empleado empl,
			List<Vacaciones> vacaciones, List<Vacaciones> vacAux) {
		for(Vacaciones vac : vacaciones){
			if(vac.getId().equals(vacacionesId)){
				vacAux.add(vac);
			}
		}
		vacaciones.removeAll(vacAux);
		empl.setVacaciones(vacaciones);
		empleadoService.saveEmpleado(empl);
	}
				
}
