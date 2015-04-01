package com.aehtiopicus.cens.controller.cens.mvc;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.aehtiopicus.cens.configuration.VistasConstant;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.PerfilRol;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.service.cens.MiembroCensService;
import com.aehtiopicus.cens.service.cens.RolCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;
import com.aehtiopicus.cens.utils.PageNotFoundException;

@ControllerAdvice
public class AbstractController {


	private static final Logger logger = Logger.getLogger(AbstractController.class);
	
	@Autowired
	private RolCensService rolCensService;
	
	@Autowired
	private UsuarioCensService usuarioCensService;
	
	@Autowired
	private MiembroCensService miembroCensService;

	@ExceptionHandler({Exception.class})
	public ModelAndView handleFormException(Exception ex) {
		logger.error(ex.getMessage());
		ex.printStackTrace();
		ModelAndView mv = new ModelAndView(VistasConstant.ERROR_VIEW);
		mv.addObject("error",ex.getMessage());
		return mv;
	}
	
	@ExceptionHandler({PageNotFoundException.class})
	public ModelAndView handleFormException(PageNotFoundException ex) {		
		
		ModelAndView mv = new ModelAndView(VistasConstant.NOT_FOUND);
		mv.addObject("error",ex.getMessage());
		return mv;
	}
		
	public PerfilRol getUserInfo(){
		logger.info("Obteniendo los datos de perfil y rol de usuario");
		Usuarios u = usuarioCensService.findUsuarioByUsername(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		List<PerfilRol> prList = rolCensService.getPerfilIdBasedOnUsuario(u.getUsername());
		PerfilRol higher=null;
		if(CollectionUtils.isNotEmpty(prList)){			
			for(PerfilRol pr : prList){
				if(higher==null){
					higher = pr;
				}else if(pr.getPerfilType().getPrioridad()<higher.getPerfilType().getPrioridad()){
					higher = pr;
				}
			}
		}
		//devolviendo rol con mayor jerarquia
		return higher;
	}
	
	public Long getMiembroInfo() throws Exception{
		logger.info("Obteniendo los datos de perfil y rol de usuario");
		MiembroCens mc = miembroCensService.getMiembroCensByUsername(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(mc==null){
			throw new Exception("Error general de sesi&oacute;n");
		}
		return mc.getId();
	}
	
	public Long getUsuarioId() throws Exception{
		logger.info("Obteniendo los datos de perfil y rol de usuario");
		Usuarios u = usuarioCensService.findUsuarioByUsername(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(u==null){
			throw new Exception("Error general de sesi&oacute;n");
		}
		return u.getId();
	}
	
}

