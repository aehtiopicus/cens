package com.aehtiopicus.cens.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Beneficio;
import com.aehtiopicus.cens.domain.BeneficioCliente;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Perfil;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.dto.BeneficioClienteDto;
import com.aehtiopicus.cens.dto.BeneficioDto;
import com.aehtiopicus.cens.dto.ClienteDto;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.enumeration.FrecuenciaBeneficioEnum;
import com.aehtiopicus.cens.enumeration.PerfilEnum;
import com.aehtiopicus.cens.enumeration.TipoBeneficioEnum;
import com.aehtiopicus.cens.helper.ClienteSecurityHelper;
import com.aehtiopicus.cens.mapper.ClienteMapper;
import com.aehtiopicus.cens.service.ClienteService;
import com.aehtiopicus.cens.service.PerfilService;
import com.aehtiopicus.cens.service.UsuarioService;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.validator.BeneficioClienteValidator;
import com.aehtiopicus.cens.validator.BeneficioValidator;
import com.aehtiopicus.cens.validator.ClienteValidator;

/**
 * Handles requests for the application ... page.
 */
@Controller
public class ClienteController extends AbstractController{

	private static final Logger logger = Logger.getLogger(ClienteController.class);
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected UsuarioService usuarioService;
	
	@Autowired
	protected PerfilService perfilService;
	
	@Autowired
	protected ClienteValidator clienteValidator;
	
	@Autowired
	protected BeneficioClienteValidator beneficioCLienteValidator;
	
	@Autowired
	protected ClienteSecurityHelper clienteSecurityHelper;
	
	@Autowired
	protected BeneficioValidator beneficioValidator;
	
	public void setClienteSecurityHelper(ClienteSecurityHelper clienteSecurityHelper) {
		this.clienteSecurityHelper = clienteSecurityHelper;
	}

	public void setBeneficioCLienteValidator(
			BeneficioClienteValidator beneficioCLienteValidator) {
		this.beneficioCLienteValidator = beneficioCLienteValidator;
	}

	public void setClienteValidator(ClienteValidator clienteValidator) {
		this.clienteValidator = clienteValidator;
	}

    public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
    
    public void setBeneficioValidator(BeneficioValidator beneficioValidator) {
		this.beneficioValidator = beneficioValidator;
	}

    
	@RequestMapping(value = UrlConstant.CLIENTE_URL, method = RequestMethod.GET)
    public ModelAndView clienteFormNew(Locale locale, Model model, Principal principal) {

		logger.info("Cliente Form -> Nuevo");

		ModelAndView mav = new ModelAndView(VistasConstant.CLIENTE_VIEW);
		mav.addObject("clienteDto", new ClienteDto());
		mav.addObject("gerentesDto", getGerentesOpDto());
		mav.addObject("jefesDto", getJefesOpDto());

		return mav;
    }
    
	@RequestMapping(value = UrlConstant.CLIENTE_URL + "/{clienteId}", method = RequestMethod.GET)
    public ModelAndView clienteFormEdit(HttpServletRequest request , Locale locale, Model model, Principal principal, @PathVariable("clienteId") Long clienteId) {

		logger.info("Cliente Form -> Editar");
		
		Cliente cliente = clienteService.getClienteById(clienteId);

		if(!clienteSecurityHelper.elUsuarioTieneAcceso(request, cliente)) {
			return Utils.getUnauthorizedActionModelAndView();
		}
		
        ClienteDto clienteDto = ClienteMapper.getClienteDtoFromCliente(cliente);
        
        ModelAndView mav = new ModelAndView(VistasConstant.CLIENTE_VIEW);
        mav.addObject("clienteDto", clienteDto);
        mav.addObject("gerentesDto", getGerentesOpDto());
        mav.addObject("jefesDto", getJefesOpDto());

        return mav;
    }
    
    
    @RequestMapping(value = {UrlConstant.CLIENTE_URL, UrlConstant.CLIENTE_URL + "/{clienteId}"}, method = RequestMethod.POST)
    public ModelAndView clienteFormSubmit(Locale locale, Model model, Principal principal, @Valid ClienteDto clienteDto, BindingResult result) {
        logger.info("Guardando Cliente.");
        ModelAndView mav = null;
        
        clienteValidator.validate(clienteDto, result);
        
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.CLIENTE_VIEW);
	        mav.addObject("clienteDto", clienteDto);
	        mav.addObject("gerentesDto", getGerentesOpDto());
	        mav.addObject("jefesDto", getJefesOpDto());
	        
	        return mav;
		}
	
		//Obtengo objetos del dominio a partir del dto que llega desde la pantalla, para lo cual usamos el mapper
		Cliente cliente = ClienteMapper.getClienteFromClienteDto(clienteDto);
		clienteService.saveCliente(cliente);
        mav = new ModelAndView("redirect:" + UrlConstant.CLIENTES_URL);
        return mav;
    }
    
    
    private List<ComboDto> getGerentesOpDto(){  
		Perfil perfilGteOp = perfilService.getPerfileByPerfil(PerfilEnum.GTE_OPERACION.getNombre());
		Perfil superUser = perfilService.getPerfileByPerfil(PerfilEnum.ADMINISTRADOR.getNombre());
		
		//obtengo lista de perfiles   
		List<Usuario> gerentesOperaciones = usuarioService.getUsuariosByPerfil(perfilGteOp);
		gerentesOperaciones.addAll(usuarioService.getUsuariosByPerfil(superUser));
        
		List<ComboDto> combosDto = ClienteMapper.getCombosDtoFromUsuarios(gerentesOperaciones);
        Collections.sort(combosDto);
        return combosDto;
	}
    
    private List<ComboDto> getJefesOpDto(){
		Perfil perfil = perfilService.getPerfileByPerfil(PerfilEnum.JEFE_OPERACION.getNombre());
		List<Usuario> usuarios = usuarioService.getUsuariosByPerfil(perfil);
		List<ComboDto> combosDto = ClienteMapper.getCombosDtoFromUsuarios(usuarios);
        Collections.sort(combosDto);
        return combosDto;
    }
    
    private List<ComboDto> getEstadosCliente(){
    	return ClienteMapper.getComboEstadoCliente();
	}
    
    @RequestMapping(value = UrlConstant.CLIENTES_URL, method = RequestMethod.GET)
    public ModelAndView clientesGrilla(Locale locale, Model model, Principal principal) {
        logger.info("Cliente List Form.");
        ModelAndView mav = new ModelAndView(VistasConstant.CLIENTES_VIEW);
        mav.addObject("estadosDto", getEstadosCliente());
        return mav;
    }
	
	 @RequestMapping(value = UrlConstant.CLIENTES_GRILLA_URL, method = RequestMethod.GET)
	 public @ResponseBody Map<String, Object> clientesGrilla(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		   logger.info("obtener informacion para llenado de grilla clientes");
		   
	        //recuperar valores de paginado
	        Integer rows = Integer.parseInt(request.getParameter("rows"));
	        Integer page = Integer.parseInt(request.getParameter("page"));
	        
	        logger.info("recuperando "+rows+" clientes de la pagina "+page);
	        String estado = request.getParameter("estado");
	        String nombre = request.getParameter("nombre");
	        logger.info("Estado cliente: "+ estado);
	        List<Cliente> clientes = clienteService.search(estado,nombre,page, rows);
	        int pageNumber = page; 
	        int totalClientes = clienteService.getTotalClientes(estado,nombre);
	        int pageTotal = clienteService.getNumberOfPages(rows,totalClientes);
	        
	        List<ClienteDto> clientesDto = ClienteMapper.getClientesDtoFromClientes(clientes);
	        clientesDto = clienteSecurityHelper.addSecurityToActionsGrillaClientes(request, clientesDto);
	        
	        JqGridData<ClienteDto> gridData = new JqGridData<ClienteDto>(pageTotal, pageNumber,totalClientes , clientesDto);
	        return gridData.getJsonObject();
	       
	    }
	 
	@RequestMapping(value = UrlConstant.BENEFICIOS_URL, method = RequestMethod.GET)
	public ModelAndView beneficios(Locale locale, Model model, Principal principal) {
		logger.info("Beneficios List Form.");
		ModelAndView mav = new ModelAndView(VistasConstant.BENEFICIOS_VIEW);

		return mav;
	}
	 
	@RequestMapping(value = UrlConstant.BENEFICIOS_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> beneficiosGrilla(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("obtener informacion para llenado de grilla beneficios");

		Integer rows = Integer.parseInt(request.getParameter("rows"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        
		List<Beneficio> beneficios = clienteService.searchBeneficios(page, rows);
		
		int total = clienteService.getTotalBeneficios(page, rows);
		int pageTotal = Utils.getNumberOfPages(rows,total);
		int pageNumber = page;

		List<BeneficioDto> beneficiosDto = ClienteMapper.getBeneficiosDtoFromBeneficios(beneficios);
		JqGridData<BeneficioDto> gridData = new JqGridData<BeneficioDto>(pageTotal, pageNumber, total, beneficiosDto);
		return gridData.getJsonObject();

	}	
	
	@RequestMapping(value = UrlConstant.BENEFICIO_URL, method = RequestMethod.GET)
	public ModelAndView beneficioForm(Locale locale, Model model, Principal principal) {
		logger.info("Beneficio Form.");

		ModelAndView mav = new ModelAndView(VistasConstant.BENEFICIO_VIEW);
		mav.addObject("beneficioDto", new BeneficioDto());

		return mav;
	}	
	
	@RequestMapping(value = UrlConstant.BENEFICIO_URL + "/{beneficioId}", method = RequestMethod.GET)
	public ModelAndView beneficioFormEdit(Locale locale, Model model, Principal principal, @PathVariable("beneficioId") Long beneficioId) {
		logger.info("Beneficio Form Edit.");

		Beneficio beneficio = clienteService.getBeneficioById(beneficioId);
		
		BeneficioDto beneficioDto = ClienteMapper.getBeneficioDtoFromBeneficio(beneficio);

		ModelAndView mav = new ModelAndView(VistasConstant.BENEFICIO_VIEW);
		mav.addObject("beneficioDto", beneficioDto);
		
		return mav;
	}	
	
	@RequestMapping(value = {UrlConstant.BENEFICIO_URL, UrlConstant.BENEFICIO_URL + "/{beneficioId}"}, method = RequestMethod.POST)
	public ModelAndView beneficioFormSubmit(Locale locale, Model model, Principal principal, @Valid BeneficioDto beneficioDto, BindingResult result) {
		logger.info("Beneficio Form Submit.");

		ModelAndView mav;
		
		beneficioValidator.validate(beneficioDto, result);
		
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.BENEFICIO_VIEW);
			mav.addObject("beneficioDto", beneficioDto);

	        return mav;
		}
		
		Beneficio beneficio = ClienteMapper.getBeneficioFromBeneficioDto(beneficioDto);
		beneficio = clienteService.saveBeneficio(beneficio);
		
		mav = new ModelAndView("redirect:" + UrlConstant.BENEFICIOS_URL);

		return mav;
	}	
	
	@RequestMapping(value = UrlConstant.BENEFICIO_DELETE_URL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deleteUser(@RequestParam("beneficioId") Long beneficioId, HttpServletRequest request) {
        logger.info("Eliminar beneficio.");
        Map<String, Object> data  = new HashMap<String, Object>();
        
        boolean success = true;
        String message;
        
        if(beneficioId != null) {
        	try {
            	success = clienteService.deleteBeneficio(beneficioId);
            	message = "El beneficio fue removido.";
        	}catch(Exception e) {
        		success = false;
        		message = "El beneficio no se puede eliminar. Se encuentra asociado al menos a un cliente.";
        	}
        }else {
        	success = false;
        	message = "No se pudo eliminar el Beneficio.";
        }

        data.put("success", success);
        data.put("message",message);
        
        return data;
    }
	

	@RequestMapping(value = UrlConstant.CLIENTE_BENEFICIOS_URL + "/{clienteId}", method = RequestMethod.GET)
	public ModelAndView beneficiosCliente(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("clienteId") Long clienteId) {
		logger.info("Beneficios Cliente List Form.");
		
		Cliente cliente = clienteService.getClienteById(clienteId);
		
		if(!clienteSecurityHelper.elUsuarioTieneAcceso(request, cliente)) {
			return Utils.getUnauthorizedActionModelAndView();
		}
		
		ClienteDto clienteDto = ClienteMapper.getClienteDtoFromCliente(cliente);
		
		ModelAndView mav = new ModelAndView(VistasConstant.CLIENTE_BENEFICIOS_VIEW);
		mav.addObject("clienteDto", clienteDto);
		
		return mav;
	}

	@RequestMapping(value = UrlConstant.CLIENTE_BENEFICIOS_GRILLA_URL, method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> beneficiosClienteGrilla(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("Obtener informacion para llenado de grilla beneficios del cliente");
		Long clienteId = Long.parseLong(request.getParameter("clienteId"));
		logger.info("clienteId: " + clienteId);

		Cliente cliente = clienteService.getClienteById(clienteId);
		
		if(!clienteSecurityHelper.elUsuarioTieneAcceso(request, cliente)) {
			return Utils.getUnauthorizedActionMap();
		}
		
		List<BeneficioCliente> beneficiosCliente = clienteService.getClienteById(clienteId).getBeneficios();
		// Fija para no paginar
		int total = beneficiosCliente.size();
		int pageTotal = 1;
		int pageNumber = 1;

		List<BeneficioClienteDto> beneficiosClienteDto = ClienteMapper.getBeneficiosClienteDtoFromBeneficiosCliente(beneficiosCliente);
		Collections.sort(beneficiosClienteDto);
		
		JqGridData<BeneficioClienteDto> gridData = new JqGridData<BeneficioClienteDto>(pageTotal, pageNumber, total, beneficiosClienteDto);
		return gridData.getJsonObject();

	}
	
	
	@RequestMapping(value = UrlConstant.CLIENTE_BENEFICIO_URL + "/{clienteId}", method = RequestMethod.GET)
	public ModelAndView clienteBeneficioForm(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("clienteId") Long clienteId) {
		logger.info("Asignar beneficio Form.");

		Cliente cliente = clienteService.getClienteById(clienteId);
		if(!clienteSecurityHelper.elUsuarioTieneAcceso(request, cliente)) {
			return Utils.getUnauthorizedActionModelAndView();
		}
		
		BeneficioClienteDto benenficioClienteDto = new BeneficioClienteDto();
		benenficioClienteDto.setClienteId(clienteId);
		ModelAndView mav = new ModelAndView(VistasConstant.CLIENTE_BENEFICIO_VIEW);
		mav.addObject("beneficioClienteDto", benenficioClienteDto);
		addModelToBeneficioForm(mav);
		
		return mav;
	}	

	private void addModelToBeneficioForm(ModelAndView mav) {
		mav.addObject("tiposDto", ClienteMapper.getCombosDtoFromTiposBeneficio(TipoBeneficioEnum.values()));
		mav.addObject("beneficiosDto", ClienteMapper.getCombosDtoFromBeneficios(clienteService.getBeneficios()));
	}
	
	
	@RequestMapping(value = UrlConstant.CLIENTE_BENEFICIO_URL + "/{clienteId}/{beneficioClienteId}", method = RequestMethod.GET)
	public ModelAndView clienteBeneficioEditForm(HttpServletRequest request, Locale locale, Model model, Principal principal, @PathVariable("clienteId") Long clienteId, @PathVariable("beneficioClienteId") Long beneficioClienteId) {
		logger.info("Asignar beneficio Edit Form.");

		Cliente cliente = clienteService.getClienteById(clienteId);
		if(!clienteSecurityHelper.elUsuarioTieneAcceso(request, cliente)) {
			return Utils.getUnauthorizedActionModelAndView();
		}
		
		BeneficioCliente beneficioCliente = clienteService.getBeneficioClienteById(beneficioClienteId);
		
		BeneficioClienteDto beneficioClienteDto = ClienteMapper.getBeneficioClienteDtoFromBeneficioCliente(beneficioCliente);
		ModelAndView mav = new ModelAndView(VistasConstant.CLIENTE_BENEFICIO_VIEW);
		mav.addObject("beneficioClienteDto", beneficioClienteDto);
		addModelToBeneficioForm(mav);

		return mav;
	}	
	
	
	@RequestMapping(value = {UrlConstant.CLIENTE_BENEFICIO_URL + "/{clienteId}", UrlConstant.CLIENTE_BENEFICIO_URL + "/{clienteId}/{beneficioClienteId}"}, method = RequestMethod.POST)
	public ModelAndView clienteBeneficioFormSubmit(Locale locale, Model model, Principal principal, @Valid BeneficioClienteDto beneficioClienteDto, BindingResult result) {
		logger.info("Asignar Beneficio Form Submit.");

		ModelAndView mav;
		
		beneficioCLienteValidator.validate(beneficioClienteDto, result);
		
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.CLIENTE_BENEFICIO_VIEW);
			mav.addObject("beneficioClienteDto", beneficioClienteDto);
			addModelToBeneficioForm(mav);

	        return mav;
		}
		
		BeneficioCliente beneficioCliente = ClienteMapper.getBeneficioClienteFromBeneficioClienteDto(beneficioClienteDto);
		beneficioCliente = clienteService.saveBeneficioCliente(beneficioCliente);
		
		mav = new ModelAndView("redirect:" + UrlConstant.CLIENTE_BENEFICIOS_URL + "/" + beneficioClienteDto.getClienteId());

		return mav;
	}	
	
	@RequestMapping(value = UrlConstant.CLIENTE_BENEFICIO_CHANGE_ESTADO_URL, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> beneficioChangeState(HttpServletRequest request, HttpServletResponse response, Long beneficioClienteId) throws IOException {

		logger.info("Cambiando estado del beneficio");
	
        Map<String, Object> data  = new HashMap<String, Object>();
        
        boolean success = true;
        String message;

		
		if(beneficioClienteId == null) {
			success = false;
			message = "No se pudo actualizar el beneficio";
		}

    	try {
        	BeneficioCliente beneficioCliente = clienteService.cambiarEstadoBeneficioCliente(beneficioClienteId);
        	success = true;
        	if(beneficioCliente.getVigente()) {
            	message = "El beneficio se encuentra habilitado, estará disponible en los próximos informes mensuales que se generen.";        		
        	}else {
            	message = "El beneficio se encuentra deshabilitado, no estará disponible en los próximos informes mensuales que se generen.";        		
        	}
    	}catch(Exception e) {
    		success = false;
    		message = e.getMessage();
    	}
    	
        data.put("success", success);
        data.put("message",message);
        
        return data;
	}	
}
