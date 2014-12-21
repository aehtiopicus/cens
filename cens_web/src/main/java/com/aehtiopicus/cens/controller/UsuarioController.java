package com.aehtiopicus.cens.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.aehtiopicus.cens.configuration.UrlConstant;
import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.Perfil;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.domain.UsuarioPerfil;
import com.aehtiopicus.cens.dto.ChangePasswordDto;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.dto.JqGridData;
import com.aehtiopicus.cens.dto.MiPerfilDto;
import com.aehtiopicus.cens.dto.ResetPasswordDto;
import com.aehtiopicus.cens.dto.UsuarioDto;
import com.aehtiopicus.cens.enumeration.PerfilEnum;
import com.aehtiopicus.cens.mapper.UsuarioMapper;
import com.aehtiopicus.cens.service.UsuarioService;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.validator.ChangePasswordValidator;
import com.aehtiopicus.cens.validator.ResetPasswordValidator;
import com.aehtiopicus.cens.validator.UsuarioValidator;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UsuarioController extends AbstractController{

	 private static final Logger logger = Logger.getLogger(UsuarioController.class);

    @Autowired
    protected UsuarioService usuarioService;

    @Autowired
    protected UsuarioValidator usuarioValidator;
    
    @Autowired
    protected ChangePasswordValidator changePasswordValidator;
 
    @Autowired
    protected ResetPasswordValidator resetPasswordValidator;
    
    public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
    
	public void setUsuarioValidator(UsuarioValidator usuarioValidator) {
		this.usuarioValidator = usuarioValidator;
	}

	public void setChangePasswordValidator(ChangePasswordValidator changePasswordValidator) {
		this.changePasswordValidator = changePasswordValidator;
	}

	public void setResetPasswordValidator(ResetPasswordValidator resetPasswordValidator) {
		this.resetPasswordValidator = resetPasswordValidator;
	}

	@RequestMapping(value = UrlConstant.USUARIO_URL, method = RequestMethod.GET)
    public ModelAndView usuarioFormNew(Locale locale, Model model, Principal principal) {

		logger.info("Usuario Form -> Nuevo");
        
        ModelAndView mav = new ModelAndView(VistasConstant.USUARIO_VIEW);
        mav.addObject("usuarioDto", new UsuarioDto());
        mav.addObject("perfilesDto", getPerfilesDto());
        
        return mav;
    }

	@RequestMapping(value = UrlConstant.USUARIO_URL + "/{usuarioId}", method = RequestMethod.GET)
    public ModelAndView usuarioFormEdit(Locale locale, Model model, Principal principal, @PathVariable("usuarioId") Long usuarioId) {

		logger.info("Usuario Form -> Editar");		
		UsuarioPerfil usuarioPerfil = usuarioService.getUsuarioPerfilByUsuarioId(usuarioId);		
        UsuarioDto usuarioDto = UsuarioMapper.getUsuarioDtoFromUsuarioPerfil(usuarioPerfil);
        
        ModelAndView mav = new ModelAndView(VistasConstant.USUARIO_VIEW);
        mav.addObject("usuarioDto", usuarioDto);
        mav.addObject("perfilesDto", getPerfilesDto());
        
        return mav;
    }	
	
	
	@RequestMapping(value = {UrlConstant.USUARIO_URL, UrlConstant.USUARIO_URL + "/{usuarioId}"}, method = RequestMethod.POST)
    public ModelAndView usuarioFormSubmit(Locale locale, Model model, Principal principal, @Valid UsuarioDto usuarioDto, BindingResult result) {
        logger.info("Guardando Usuario.");
        ModelAndView mav = null;
        
        usuarioValidator.validate(usuarioDto, result);
        
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.USUARIO_VIEW);
	        mav.addObject("usuarioDto", usuarioDto);
	        mav.addObject("perfilesDto", getPerfilesDto());	        
	        return mav;
		}
		
		//Obtengo objetos del dominio a partir del dto que llega desde la pantalla, para lo cual usamos el mapper
		Perfil perfil = UsuarioMapper.getPerfilFromUsuarioDto(usuarioDto);
		Usuario usuario;
		if(usuarioDto.getUsuarioId() != null) {
			usuario = usuarioService.getById(usuarioDto.getUsuarioId());
			usuario = UsuarioMapper.updateUsuarioFromUsuarioDto(usuario, usuarioDto);
		}else {
			usuario = UsuarioMapper.getUsuarioFromUsuarioDto(usuarioDto);
		}
		
		
		usuarioService.saveUsuario(usuario, perfil);
        
        mav = new ModelAndView("redirect:" + UrlConstant.USUARIOS_URL);
        
        return mav;
    }
	
	
	private List<ComboDto> getPerfilesDto(){
        //obtengo lista de perfiles
		List<Perfil> perfiles = usuarioService.getPerfiles();
		//obtengo los dtos del mapper
        return UsuarioMapper.getCombosDtoFromPerfiles(perfiles);
	}	
	
	
	@RequestMapping(value = UrlConstant.USUARIOS_URL, method = RequestMethod.GET)
    public ModelAndView usuariosGrilla(Locale locale, Model model, Principal principal) {
        logger.info("Usuario Form.");
        
        ModelAndView mav = new ModelAndView(VistasConstant.USUARIOS_VIEW);
        mav.addObject("perfilesDto", getPerfilesDto());
        return mav;
    }
	
	 @RequestMapping(value = UrlConstant.USUARIOS_GRILLA_URL, method = RequestMethod.GET)
	 public @ResponseBody Map<String, Object> usuariosGrilla(HttpServletRequest request,HttpServletResponse response) throws IOException {
		 
		   logger.info("obtener informacion para llenado de grilla usuarios");
		   
	       // recuperar valores de paginado
	        Integer rows = Integer.parseInt(request.getParameter("rows"));
	        Integer page = Integer.parseInt(request.getParameter("page"));
	        logger.info("recuperando "+rows+" usuarios de la pagina "+page);
	        String perfil = request.getParameter("perfil");
	        String apellido = request.getParameter("apellido");
	        List<UsuarioPerfil> usuariosPerfil = usuarioService.search(perfil,apellido, page, rows);
	        
	        int pageNumber = page; 
	        int totalUsers = usuarioService.getTotalUsersFilterByProfile(perfil,apellido);
	        int pageTotal = Utils.getNumberOfPages(rows,totalUsers);
	        List<UsuarioDto> usuariosDto = UsuarioMapper.getUsuariosDtoFromUsuariosPerfil(usuariosPerfil);
	        JqGridData<UsuarioDto> gridData = new JqGridData<UsuarioDto>(pageTotal, pageNumber,totalUsers , usuariosDto);
	        return gridData.getJsonObject();

	    }
	
	
	
	/**
	 * Metodo invocado mediante ajax. recive el id del usuario que se desea eliminar.
	 * Si puede eliminar el usuario devuelve success en true y un mensaje.
	 * Si no puede eliminar el usuario devuelve sucess en false y el mensaje de error.
	 */
	@RequestMapping(value = UrlConstant.USUARIO_DELETE_URL, method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deleteUser(Locale locale, Model model, Principal principal, Long usuarioId, HttpServletRequest request) {
        logger.info("Eliminar usuario.");
        Map<String, Object> data  = new HashMap<String, Object>();
        
        if(!request.isUserInRole(PerfilEnum.ADMINISTRADOR.getNombre())){
        	data.put("sucess", false);
        	data.put("message","Permisos insuficientes.");
        	return data;
        }
        String nameUser = request.getRemoteUser();
        Usuario us = usuarioService.getUsuarioByUsername(nameUser);
        if(us != null && us.getId() == usuarioId){
        	data.put("sucess", false);
        	data.put("message","No puede eliminar el usuario.");
        	return data;
        }
        
        if(usuarioId != null && usuarioId > 0){
        	Usuario usuario = new Usuario();
        	usuario.setId(usuarioId);
        	
        	usuarioService.deleteUsuario(usuario);
        	data.put("success", true);
        	data.put("message", "Usuario eliminado correctamente.");
        }else{
        	data.put("sucess", false);
        	data.put("message","Error al intentar eliminar el usuario.");
        }
        
        return data;
    }	
		
	
	@RequestMapping(value = UrlConstant.MI_PERFIL_URL, method = RequestMethod.GET)
    public ModelAndView miPerfilForm(Locale locale, Model model, Principal principal) {
		logger.info("Mi Perfil Form");
		
        Usuario usuario = getUsuarioAutenticado();
        
        MiPerfilDto miPerfilDto = UsuarioMapper.getMiPerfilDtoFromUsuario(usuario);
        
        ModelAndView mav = new ModelAndView(VistasConstant.MI_PERFIL_VIEW);
        mav.addObject("miPerfilDto", miPerfilDto);
   
        return mav;
    }	
	
	@RequestMapping(value = UrlConstant.MI_PERFIL_URL, method = RequestMethod.POST)
    public ModelAndView miPerfilFormSubmit(Locale locale, Model model, Principal principal, @Valid MiPerfilDto miPerfilDto, BindingResult result) {
        logger.info("Actualizando perfil.");
        ModelAndView mav = null;
        
        Usuario usuario = getUsuarioAutenticado();	
        
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			miPerfilDto.setUsername(usuario.getUsername());
			mav = new ModelAndView(VistasConstant.MI_PERFIL_VIEW);
	        mav.addObject("miPerfilDto", miPerfilDto);
	        
	        return mav;
		}
		
		
		usuario = UsuarioMapper.updateUsuarioFromMiPerfilDto(miPerfilDto, usuario);
		
		usuarioService.saveUsuario(usuario);
        
        mav = new ModelAndView("redirect:" + UrlConstant.MAIN_URL);
        
        return mav;
    }	

	@RequestMapping(value = UrlConstant.CAMBIAR_PASSWORD_URL, method = RequestMethod.GET)
    public ModelAndView changePasswordForm(Locale locale, Model model, Principal principal) {
		logger.info("Cambiar password Form");
		
        Usuario usuario = getUsuarioAutenticado();
        
        ChangePasswordDto changePasswordDto = UsuarioMapper.getChangePasswordDtoFromUsuario(usuario);
        
        ModelAndView mav = new ModelAndView(VistasConstant.CAMBIAR_PASSWORD_VIEW);
        mav.addObject("changePasswordDto", changePasswordDto);
   
        return mav;
    }		
	
	@RequestMapping(value = UrlConstant.CAMBIAR_PASSWORD_URL, method = RequestMethod.POST)
    public ModelAndView changePasswordFormSubmit(Locale locale, Model model, Principal principal, @Valid ChangePasswordDto changePasswordDto, BindingResult result) {
        logger.info("Actualizando perfil.");
        ModelAndView mav = null;
        
        Usuario usuario = getUsuarioAutenticado();	
        changePasswordDto.setUsername(usuario.getUsername());
        
        changePasswordValidator.validate(changePasswordDto, result);
        
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			mav = new ModelAndView(VistasConstant.CAMBIAR_PASSWORD_VIEW);
	        mav.addObject("changePasswordDto", changePasswordDto);
	        
	        return mav;
		}
		
		
		usuario = UsuarioMapper.updateUsuarioFromChangePasswordDto(changePasswordDto, usuario);
		
		usuarioService.saveUsuario(usuario);
        
        mav = new ModelAndView("redirect:" + UrlConstant.MAIN_URL);
        
        return mav;
    }		
	
	private Usuario getUsuarioAutenticado(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        String username = auth.getName();
        Usuario usuario = usuarioService.getUsuarioByUsername(username);
        
        return usuario;
	}
	
	@RequestMapping(value = UrlConstant.RESETEAR_PASSWORD_URL + "/{usuarioId}", method = RequestMethod.GET)
    public ModelAndView resetPasswordForm(Locale locale, Model model, Principal principal, @PathVariable("usuarioId") Long usuarioId) {

		logger.info("Reset password");		
		Usuario usuario = usuarioService.getById(usuarioId);		
        ResetPasswordDto dto = UsuarioMapper.getResetPasswordDtoFromUsuario(usuario);
        
        ModelAndView mav = new ModelAndView(VistasConstant.RESETEAR_PASSWORD_VIEW);
        mav.addObject("resetPasswordDto", dto);
        
        return mav;
    }
	
	@RequestMapping(value = UrlConstant.RESETEAR_PASSWORD_URL + "/{usuarioId}", method = RequestMethod.POST)
    public ModelAndView resetPasswordFormSubmit(Locale locale, Model model, Principal principal, @Valid ResetPasswordDto resetPasswordDto, BindingResult result) {

		logger.info("Reset password submit");		
		ModelAndView mav = null;

		Usuario usuario = usuarioService.getById(resetPasswordDto.getUsuarioId());		
		
		resetPasswordValidator.validate(resetPasswordDto, result);
		
        //SI hay errores,  se muestran al usuario
		if(result.hasErrors()){
			resetPasswordDto.setUsername(usuario.getUsername());
			mav = new ModelAndView(VistasConstant.RESETEAR_PASSWORD_VIEW);
	        mav.addObject("resetPasswordDto", resetPasswordDto);
	        
	        return mav;
		}
		
        usuario = UsuarioMapper.updateUsuarioFromResetPassword(usuario, resetPasswordDto);
        usuarioService.saveUsuario(usuario);

        mav = new ModelAndView("redirect:" + UrlConstant.USUARIOS_URL);
        
        return mav;
    }
 }
