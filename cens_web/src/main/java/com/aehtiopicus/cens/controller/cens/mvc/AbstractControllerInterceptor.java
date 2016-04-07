package com.aehtiopicus.cens.controller.cens.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aehtiopicus.cens.domain.entities.PerfilRol;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.service.cens.RolCensService;
import com.aehtiopicus.cens.service.cens.UsuarioCensService;

@Component
public class AbstractControllerInterceptor extends HandlerInterceptorAdapter{

	private static final Logger log = LoggerFactory.getLogger(AbstractControllerInterceptor.class);

	@Autowired
	private UsuarioCensService usuarioCensService;
	
	@Autowired
	private RolCensService rolCensService;
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
		if(modelAndView != null){
			User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Usuarios u = usuarioCensService.findUsuarioByUsername(principal.getUsername());
			List<PerfilRol> prList = rolCensService.getPerfilIdBasedOnUsuario(u.getUsername());
			if(CollectionUtils.isNotEmpty(prList)){
				for(PerfilRol pr : prList){
					log.info(pr.getPerfilType().name().toLowerCase()+"Id  "+pr.getPerfilId());
					modelAndView.addObject(pr.getPerfilType().name().toLowerCase()+"Id",pr.getPerfilId());
				}
			}
			
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	
	
	

	
}
